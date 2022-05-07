package com.github.idimabr.rapharegionevents.objects;

import com.github.idimabr.rapharegionevents.RaphaRegionEvents;
import com.google.common.collect.Lists;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class TemporaryEvent {

    private int day;
    private int hour;
    private int minute;

    private int dayFinal;
    private int hourFinal;
    private int minuteFinal;

    private boolean open;
    private String regionName;
    private List<UUID> members = Lists.newArrayList();
    private Location backLocation;
    private Location spawnLocation;

    public TemporaryEvent(int day, int hour, int minute, int dayFinal, int hourFinal, int minuteFinal, boolean open, String regionName, List<UUID> members, Location backLocation, Location spawnLocation) {
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.open = open;
        this.regionName = regionName;
        this.members = members;
        this.backLocation = backLocation;
        this.dayFinal = dayFinal;
        this.hourFinal = hourFinal;
        this.minuteFinal = minuteFinal;
        this.spawnLocation = spawnLocation;
    }

    public TemporaryEvent(int day, int hour, int minute, int dayFinal, int hourFinal, int minuteFinal, boolean open, String regionName, Location backLocation, Location spawnLocation) {
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.open = open;
        this.regionName = regionName;
        this.members = members;
        this.backLocation = backLocation;
        this.dayFinal = dayFinal;
        this.hourFinal = hourFinal;
        this.minuteFinal = minuteFinal;
        this.spawnLocation = spawnLocation;
    }

    public TemporaryEvent(int day, int hour, int minute, int dayFinal, int hourFinal, int minuteFinal, boolean open, String regionName, List<UUID> members, Location backLocation) {
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.open = open;
        this.regionName = regionName;
        this.members = members;
        this.backLocation = backLocation;
        this.dayFinal = dayFinal;
        this.hourFinal = hourFinal;
        this.minuteFinal = minuteFinal;
    }

    public TemporaryEvent(int day, int hour, int minute, int dayFinal, int hourFinal, int minuteFinal, boolean open, String regionName, Location backLocation) {
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.open = open;
        this.regionName = regionName;
        this.backLocation = backLocation;
        this.dayFinal = dayFinal;
        this.hourFinal = hourFinal;
        this.minuteFinal = minuteFinal;
    }

    public TemporaryEvent(int day, int hour, int minute, int dayFinal, int hourFinal, int minuteFinal, boolean open, String regionName) {
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.open = open;
        this.regionName = regionName;
        this.dayFinal = dayFinal;
        this.hourFinal = hourFinal;
        this.minuteFinal = minuteFinal;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public void setDayFinal(int dayFinal) {
        this.dayFinal = dayFinal;
    }

    public void setHourFinal(int hourFinal) {
        this.hourFinal = hourFinal;
    }

    public void setMinuteFinal(int minuteFinal) {
        this.minuteFinal = minuteFinal;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getRegionName() {
        return regionName;
    }

    public List<UUID> getMembers() {
        return members;
    }

    public void setMembers(List<UUID> members) {
        this.members = members;
    }

    public int getDayFinal() {
        return dayFinal;
    }

    public int getHourFinal() {
        return hourFinal;
    }

    public int getMinuteFinal() {
        return minuteFinal;
    }

    public Location getBackLocation() {
        return backLocation;
    }

    public void setBackLocation(Location backLocation) {
        this.backLocation = backLocation;
    }

    public Location getSpawnLocation() {
        return spawnLocation;
    }

    public void setSpawnLocation(Location spawnLocation) {
        this.spawnLocation = spawnLocation;
    }

    public void teleportPlayers(){
        Location location = spawnLocation;
        if(location == null) return;

        for (UUID member : members) {
            Player player = Bukkit.getPlayer(member);
            if(player == null) continue;
            player.teleport(location);
            player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 1);
            for (String s : RaphaRegionEvents.getPlugin().getConfig().getStringList("Regions." + regionName + ".Message.PlayerTeleport")) {
               player.sendMessage(s.replace("&","§"));
            }
        }
    }
}
