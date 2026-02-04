package com.matthewperiut.accessoryapi.command;

import com.matthewperiut.accessoryapi.api.PlayerExtraHP;
//import com.matthewperiut.retrocommands.api.Command;
//import com.matthewperiut.retrocommands.util.SharedCommandSource;
//import java.util.ArrayList;
//import net.minecraft.entity.player.PlayerEntity;
//
//public class HeartsCommand implements Command {
//    @Override
//    public void command(SharedCommandSource commandSource, String[] parameters) {
//        PlayerEntity player = commandSource.getPlayer();
//        if (player == null)
//        {
//            commandSource.sendFeedback("This command can only be run by a player");
//            return;
//        }
//        if (parameters.length > 2)
//        {
//            switch (parameters[1]) {
//                case "add" -> {
//                    commandSource.sendFeedback("Added " + Integer.parseInt(parameters[2]) / 2.F + " Hearts");
//                    ((PlayerExtraHP) player).addExtraHP(Integer.parseInt(parameters[2]));
//                }
//                case "remove" -> {
//                    commandSource.sendFeedback("Removed " + Integer.parseInt(parameters[2]) / 2.F + " Hearts");
//                    ((PlayerExtraHP) player).addExtraHP(-Integer.parseInt(parameters[2]));
//                }
//                default -> manual(commandSource);
//            }
//        }
//        else if (parameters.length > 1 && parameters[1].equals("count"))
//        {
//            commandSource.sendFeedback("You have " + (10 + (((PlayerExtraHP) player).getExtraHP() / 2.F)) + " Hearts");
//        }
//        else
//        {
//            manual(commandSource);
//        }
//    }
//
//    @Override
//    public String name() {
//        return "hearts";
//    }
//
//    @Override
//    public void manual(SharedCommandSource commandSource) {
//        commandSource.sendFeedback("Usage: /hearts {add/remove} {halfHearts}");
//        commandSource.sendFeedback("       /hearts {count}");
//        commandSource.sendFeedback("Info: Changes the total number of hearts a player has");
//    }
//
//    @Override
//    public String[] suggestion(SharedCommandSource source, int parameterNum, String currentInput, String totalInput) {
//        if (parameterNum == 1)
//        {
//            String[] options = {"add", "count", "remove"};
//            ArrayList<String> output = new ArrayList<>();
//            for (String option : options)
//            {
//                if (option.startsWith(currentInput))
//                {
//                    output.add(option.substring(currentInput.length()));
//                }
//            }
//            return output.toArray(new String[0]);
//        }
//        return Command.super.suggestion(source, parameterNum, currentInput, totalInput);
//    }
//}
