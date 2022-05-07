package com.github.idimabr.rapharegionevents.managers;

import com.github.idimabr.rapharegionevents.RaphaRegionEvents;
import com.github.idimabr.rapharegionevents.objects.TemporaryEvent;
import com.github.idimabr.rapharegionevents.utils.SerializerUtils;
import com.google.common.collect.Maps;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;

public class ManagerObject {

    private RaphaRegionEvents plugin;

    public ManagerObject(RaphaRegionEvents plugin) {
        this.plugin = plugin;
    }

    private HashMap<String, TemporaryEvent> events = Maps.newHashMap();

    public HashMap<String, TemporaryEvent> getEvents() {
        return events;
    }

    public void loadEvents(){
        ConfigurationSection sectionRegions = plugin.getConfig().getConfigurationSection("Regions");
        for (String region : sectionRegions.getKeys(false)) {
            ConfigurationSection section = sectionRegions.getConfigurationSection(region);

            int day = section.getInt("Start.Day");
            int hour = section.getInt("Start.Hour");
            int minute = section.getInt("Start.Minute");

            int dayFinal = section.getInt("Final.Day");
            int hourFinal = section.getInt("Final.Hour");
            int minuteFinal = section.getInt("Final.Minute");

            Location location = null;
            String locationValue = section.getString("Location");
            if(section.contains("Location") && locationValue.contains(";"))
                location = SerializerUtils.convertLocation(locationValue);

            Location location2 = null;
            String locationValue2 = section.getString("LocationSpawn");
            if(section.contains("LocationSpawn") && locationValue.contains(";"))
                location2 = SerializerUtils.convertLocation(locationValue2);

            events.put(region, new TemporaryEvent(day, hour, minute, dayFinal, hourFinal, minuteFinal, false, region, location, location2));
        }
    }
}
