package com.github.idimabr.rapharegionevents.utils;

import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class WGUtils {

    public static boolean InRegion(Player player, String name) {
        for (ProtectedRegion region : WGBukkit.getRegionManager(player.getWorld()).getApplicableRegions(player.getLocation())) {
            if(region.getId().equalsIgnoreCase(name)) return true;
        }
        return false;
    }

    public static boolean InRegion(Location location, String name) {
        for (ProtectedRegion region : WGBukkit.getRegionManager(location.getWorld()).getApplicableRegions(location)) {
            if(region.getId().equalsIgnoreCase(name)) return true;
        }
        return false;
    }
}
