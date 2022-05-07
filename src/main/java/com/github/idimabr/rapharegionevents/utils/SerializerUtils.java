package com.github.idimabr.rapharegionevents.utils;

import com.google.gson.Gson;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import java.util.UUID;

public class SerializerUtils {

    private static Gson gson = new Gson();

    public static String convertLocation(Location location){
        String world = location.getWorld().getName();
        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();

        return world + ";" + x + ";" + y + ";" + z;
    }

    public static Location convertLocation(String string){
        if(!string.contains(";")) return null;
        String[] values = string.split(";");

        World world = Bukkit.getWorld(values[0]);
        int x = Integer.parseInt(values[1]);
        int y = Integer.parseInt(values[2]);
        int z = Integer.parseInt(values[3]);

        return new Location(world, x, y, z);
    }
}
