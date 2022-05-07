package com.github.idimabr.rapharegionevents.runnables;

import com.github.idimabr.rapharegionevents.RaphaRegionEvents;
import com.github.idimabr.rapharegionevents.objects.TemporaryEvent;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import java.util.Calendar;
import java.util.Map;

public class ToggleRegionRunnable extends BukkitRunnable {

    private RaphaRegionEvents plugin;

    public ToggleRegionRunnable(RaphaRegionEvents plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        for (Map.Entry<String, TemporaryEvent> entry : RaphaRegionEvents.getManager().getEvents().entrySet()) {
            TemporaryEvent regionEvent = entry.getValue();
            String region = entry.getKey();

            Calendar calendar = Calendar.getInstance();

            int dayStart = regionEvent.getDay();
            int hourStart = regionEvent.getHour();
            int minuteStart = regionEvent.getMinute();

            int dayFinal = regionEvent.getDayFinal();
            int hourFinal = regionEvent.getHourFinal();
            int minuteFinal = regionEvent.getMinuteFinal();

            int day = calendar.get(Calendar.DAY_OF_WEEK);
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            if(day == dayStart && hour == hourStart && minute == minuteStart && !regionEvent.isOpen()) {
                regionEvent.setOpen(true);
                for (String s : RaphaRegionEvents.getPlugin().getConfig().getStringList("Regions." + region + ".Message.Opened")) {
                    Bukkit.getOnlinePlayers().forEach(player -> player.sendMessage(s.replace("&","§")));
                }
            }

            if(day == dayFinal && hour == hourFinal && minute == minuteFinal && regionEvent.isOpen()) {
                regionEvent.setOpen(false);
                regionEvent.teleportPlayers();
                for (String s : RaphaRegionEvents.getPlugin().getConfig().getStringList("Regions." + region + ".Message.Closed")) {
                    Bukkit.getOnlinePlayers().forEach(player -> player.sendMessage(s.replace("&","§")));
                }
            }
        }
    }
}
