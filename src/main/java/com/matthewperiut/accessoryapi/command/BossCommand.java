package com.matthewperiut.accessoryapi.command;

import com.matthewperiut.accessoryapi.api.BossLivingEntity;
import com.matthewperiut.spc.api.Command;
import com.matthewperiut.spc.util.SharedCommandSource;
import net.minecraft.entity.EntityBase;
import net.minecraft.entity.Living;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.util.maths.Box;

import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class BossCommand implements Command {
    @Override
    public void command(SharedCommandSource commandSource, String[] parameters) {
        PlayerBase player = commandSource.getPlayer();
        if (player == null)
        {
            commandSource.sendFeedback("This command can only be run by a player");
            return;
        }
        if (parameters.length < 2)
        {
            manual(commandSource);
            return;
        }

        String requestedBoss = parameters[1];

        Living bossEntity = null;

        for (Object o : player.level.players) {
            PlayerBase p = (PlayerBase) o;
            if (p.name.equals(requestedBoss)) {
                bossEntity = p;
            }
        }

        int bossId = -1;

        try {
            if (bossEntity == null) bossId = Integer.parseInt(requestedBoss);
        } catch (Exception e) {
            commandSource.sendFeedback("Invalid entity id");
            return;
        }

        for (Object o : player.level.entities) {
            if (o instanceof Living e)
                if (e.entityId == bossId)
                    bossEntity = e;
        }

        if (bossEntity instanceof BossLivingEntity boss)
        {
            commandSource.sendFeedback(boss.getName() + "is now a boss");
            boss.setBoss(true);
        }
    }

    @Override
    public String name() {
        return "boss";
    }

    @Override
    public void manual(SharedCommandSource commandSource) {
        commandSource.sendFeedback("Usage: /boss {entityId}");
        commandSource.sendFeedback("Info: Turns a Living Entity into a boss");
    }

    @Override
    public String[] suggestion(SharedCommandSource source, int parameterNum, String currentInput, String totalInput) {
        if (parameterNum == 1) {
            PlayerBase p = source.getPlayer();
            List<EntityBase> entities = p.level.getEntities(EntityBase.class, Box.create(p.x-20, p.y-20, p.z-20, p.x+20, p.y+20, p.z+20));

            // Use TreeMap to keep entries in order based on the distance
            TreeMap<Double, EntityBase> distanceMap = new TreeMap<>();

            for (EntityBase entity : entities) {
                double distance = p.distanceTo(entity);
                // Handle potential duplicates (unlikely but possible)
                while (distanceMap.containsKey(distance)) {
                    distance += 0.0001;  // Small offset to handle entities at almost same distance
                }
                distanceMap.put(distance, entity);
            }

            // Extract entity IDs from sorted entities and convert them to String
            // If entity is a PlayerBase, use getName() instead
            List<String> sortedEntityIDs = distanceMap.values().stream()
                    .map(entity -> {
                        if (entity instanceof PlayerBase) {
                            return ((PlayerBase) entity).name;
                        } else {
                            return Integer.toString(entity.entityId);
                        }
                    })
                    .collect(Collectors.toList());

            // Filter and modify the suggestions based on currentInput
            for (int i = sortedEntityIDs.size() - 1; i >= 0; i--) {  // Change loop condition to i >= 0
                if (!sortedEntityIDs.get(i).startsWith(currentInput)) {
                    sortedEntityIDs.remove(i);
                } else {
                    sortedEntityIDs.set(i, sortedEntityIDs.get(i).substring(currentInput.length()));
                }
            }

            return sortedEntityIDs.toArray(new String[0]);
        }
        return new String[0];
    }
}
