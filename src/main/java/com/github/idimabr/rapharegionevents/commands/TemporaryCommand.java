package com.github.idimabr.rapharegionevents.commands;

import com.github.idimabr.rapharegionevents.RaphaRegionEvents;
import com.github.idimabr.rapharegionevents.objects.TemporaryEvent;
import com.github.idimabr.rapharegionevents.utils.ConfigUtil;
import com.github.idimabr.rapharegionevents.utils.SerializerUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class TemporaryCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {

        if(!sender.hasPermission("rapharegionevents.admin")){
            sender.sendMessage("§cSem permissão!");
            return false;
        }

        if(args.length == 0 || (args.length == 1 && args[0].equalsIgnoreCase("help"))){
            sender.sendMessage("§9Comandos:");
            sender.sendMessage("    /regionevents <abrir/fechar/definirlocal/definirspawn> <região>");
            sender.sendMessage("    /regionevents definir <região> <inicio/fim> <dia/hora/minuto> <valor>");
            return false;
        }

        if(args.length == 2){
            String region = args[1];

            if(!RaphaRegionEvents.getManager().getEvents().containsKey(region)){
                sender.sendMessage("§cRegião do evento não encontrada.");
                return false;
            }

            TemporaryEvent regionEvent = RaphaRegionEvents.getManager().getEvents().get(region);

            switch(args[0]){
                case "abrir":
                    regionEvent.setOpen(true);
                    RaphaRegionEvents.getPlugin().getConfig().set("Regions." + region + ".Opened", true);
                    RaphaRegionEvents.getPlugin().getConfig().saveConfig();
                    sender.sendMessage("§aRegião Aberta!");
                    sendMessageAll(RaphaRegionEvents.getPlugin().getConfig().getStringList("Regions." + region + ".Message.Opened"));
                    break;
                case "fechar":
                    regionEvent.setOpen(false);
                    RaphaRegionEvents.getPlugin().getConfig().set("Regions." + region + ".Opened", false);
                    RaphaRegionEvents.getPlugin().getConfig().saveConfig();
                    sender.sendMessage("§aRegião Fechada!");
                    regionEvent.teleportPlayers();
                    sendMessageAll(RaphaRegionEvents.getPlugin().getConfig().getStringList("Regions." + region + ".Message.Closed"));
                    break;
                case "definirlocal":
                    if(!(sender instanceof Player)){
                        sender.sendMessage("§cApenas jogadores podem utilizar esse comando.");
                        return false;
                    }

                    Player player = (Player) sender;

                    regionEvent.setBackLocation(player.getLocation());
                    RaphaRegionEvents.getPlugin().getConfig().set("Regions." + regionEvent.getRegionName() + ".Location", SerializerUtils.convertLocation(player.getLocation()));
                    RaphaRegionEvents.getPlugin().getConfig().saveConfig();
                    player.sendMessage("§aLocal de saida do evento da Região '§f" + regionEvent.getRegionName() + "' §adefinido.");
                    break;
                case "definirspawn":
                    if(!(sender instanceof Player)){
                        sender.sendMessage("§cApenas jogadores podem utilizar esse comando.");
                        return false;
                    }

                    Player playerL = (Player) sender;

                    regionEvent.setSpawnLocation(playerL.getLocation());
                    RaphaRegionEvents.getPlugin().getConfig().set("Regions." + regionEvent.getRegionName() + ".LocationSpawn", SerializerUtils.convertLocation(playerL.getLocation()));
                    RaphaRegionEvents.getPlugin().getConfig().saveConfig();
                    playerL.sendMessage("§aSpawn para retorno do evento da Região '§f" + regionEvent.getRegionName() + "' §adefinido.");
                    break;
                default:
                    sender.sendMessage("§cUtilize /regionevents <abrir/fechar/definirlocal/definirspawn> <região>");
                    break;
            }
        }else if(args.length == 5) {
            /// regionevents definir dia <value>

            String region = args[1];

            if(!RaphaRegionEvents.getManager().getEvents().containsKey(region)){
                sender.sendMessage("§cRegião do evento não encontrada.");
                return false;
            }

            TemporaryEvent regionEvent = RaphaRegionEvents.getManager().getEvents().get(region);

            String change = args[2];
            String key = args[3];

            ConfigUtil config = RaphaRegionEvents.getPlugin().getConfig();

            switch(args[0]) {
                case "definir":
                    if(!change.equals("inicio") || change.equals("final")){
                        sender.sendMessage("§cMudanças aceitas: inicio/final");
                        return false;
                    }

                    switch(key){
                        case "dia":
                            try{
                                int value = Integer.parseInt(args[4]);
                                if(change.equalsIgnoreCase("inicio")){
                                    regionEvent.setDay(value);
                                }else{
                                    regionEvent.setDayFinal(value);
                                }
                                sender.sendMessage("§aO valor '" + key + "' no '" + change + "' foi alterado para '" + value + "'.");
                                config.set("Regions." + regionEvent.getRegionName() + "." + (change.equals("inicio") ? "Start" : "Final") + ".Day", value);
                                config.saveConfig();
                            } catch (NumberFormatException e){
                                sender.sendMessage("§cNúmero incorreto!");
                                return false;
                            }
                            break;
                        case "hora":
                            try{
                                int value = Integer.parseInt(args[4]);
                                if(change.equalsIgnoreCase("inicio")){
                                    regionEvent.setHour(value);
                                }else{
                                    regionEvent.setHourFinal(value);
                                }
                                sender.sendMessage("§aO valor '" + key + "' no '" + change + "' foi alterado para '" + value + "'.");
                                config.set("Regions." + regionEvent.getRegionName() + "." + (change.equals("inicio") ? "Start" : "Final") + ".Hour", value);
                                config.saveConfig();
                            } catch (NumberFormatException e){
                                sender.sendMessage("§cNúmero incorreto!");
                                return false;
                            }
                            break;
                        case "minuto":
                            try{
                                int value = Integer.parseInt(args[4]);
                                if(change.equalsIgnoreCase("inicio")){
                                    regionEvent.setMinute(value);
                                }else{
                                    regionEvent.setMinuteFinal(value);
                                }
                                sender.sendMessage("§aO valor '" + key + "' no '" + change + "' foi alterado para '" + value + "'.");
                                config.set("Regions." + regionEvent.getRegionName() + "." + (change.equals("inicio") ? "Start" : "Final") + ".Minute", value);
                                config.saveConfig();
                            } catch (NumberFormatException e){
                                sender.sendMessage("§cNúmero incorreto!");
                                return false;
                            }
                            break;
                        default:
                            sender.sendMessage("§cUtilize /regionevents definir <região> <inicio/fim> <dia/hora/minuto> <valor>");
                            break;
                    }
                    break;
                default:
                    sender.sendMessage("§cUtilize /regionevents definir <região> <inicio/fim> <dia/hora/minuto> <valor>");
                    break;
            }
        }else{
            sender.sendMessage("§cUtilize /regionevents <abrir/fechar/definirlocal/definirspawn> <região>");
        }
        return false;
    }

    private void sendMessageAll(List<String> messages){
        for (Player player : Bukkit.getOnlinePlayers()) {
            for (String message : messages) {
                player.sendMessage(message.replace("&","§"));
            }
        }
    }
}
