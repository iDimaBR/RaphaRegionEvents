package com.github.idimabr.rapharegionevents.listeners;

import com.github.idimabr.rapharegionevents.RaphaRegionEvents;
import com.github.idimabr.rapharegionevents.objects.TemporaryEvent;
import com.github.idimabr.rapharegionevents.utils.ActionBar;
import com.github.idimabr.rapharegionevents.utils.WGUtils;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Map;

public class PlayerListener implements Listener {

    private RaphaRegionEvents plugin;

    public PlayerListener(RaphaRegionEvents plugin) {
        this.plugin = plugin;
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onMove(PlayerMoveEvent e){
        Player player = e.getPlayer();
        if(player.hasPermission("rapharegionevents.bypass.regionblock")) return;

        for (Map.Entry<String, TemporaryEvent> entry : RaphaRegionEvents.getManager().getEvents().entrySet()) {
            String region = entry.getKey();
            TemporaryEvent regionEvent = entry.getValue();
            if(WGUtils.InRegion(player, region)){
                if(!regionEvent.isOpen()) {
                    Location location = regionEvent.getBackLocation();
                    if (location == null) {
                        player.setVelocity(player.getLocation().getDirection().multiply(-0.6).setY(0.1));
                        player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1, 1);
                    }else{
                        Location backLocation = regionEvent.getBackLocation();
                        player.teleport(backLocation.getWorld().getSpawnLocation());

                        player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 1);
                    }
                    new ActionBar("§cO evento não está aberto.", player);
                }else{
                    if(!regionEvent.getMembers().contains(player.getUniqueId()))
                        regionEvent.getMembers().add(player.getUniqueId());
                }
            }else{
                regionEvent.getMembers().remove(player.getUniqueId());
            }
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onBreak(BlockBreakEvent e){
        Player player = e.getPlayer();
        if(player.hasPermission("rapharegionevents.bypass.regionblock")) return;

        for (Map.Entry<String, TemporaryEvent> entry : RaphaRegionEvents.getManager().getEvents().entrySet()) {
            String region = entry.getKey();
            TemporaryEvent event = entry.getValue();
            if(WGUtils.InRegion(e.getBlock().getLocation(), region))
                if(!event.isOpen()) {
                    e.setCancelled(true);
                    new ActionBar("§cO evento não está aberto.", player);
                    player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1, 1);
                }
        }
    }
}
