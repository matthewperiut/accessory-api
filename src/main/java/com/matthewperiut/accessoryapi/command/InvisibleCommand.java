package com.matthewperiut.accessoryapi.command;

import com.matthewperiut.accessoryapi.api.PlayerVisibility;
import com.matthewperiut.retrocommands.api.Command;
import com.matthewperiut.retrocommands.util.SharedCommandSource;
import net.minecraft.entity.player.PlayerBase;

public class InvisibleCommand implements Command {
    @Override
    public void command(SharedCommandSource commandSource, String[] parameters) {
        PlayerBase player = commandSource.getPlayer();
        if (player == null)
        {
            commandSource.sendFeedback("This command can only be run by a player");
            return;
        }

        ((PlayerVisibility) player).setInvisible(!((PlayerVisibility) player).isInvisible());
        commandSource.sendFeedback("You are now " + (((PlayerVisibility) player).isInvisible() ? "in" : "") + "visible");
    }

    @Override
    public String name() {
        return "invisible";
    }

    @Override
    public void manual(SharedCommandSource commandSource) {
        commandSource.sendFeedback("Usage: /invisible");
        commandSource.sendFeedback("Info: Toggles invisibility");
    }
}
