package com.slendy.bungeecore.Commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * ************************************************************************
 * Copyright Slendy (c) 2016. All Right Reserved.
 * Any code contained within this document, and any associated APIs with similar branding
 * are the sole property of Slendy. Distribution, reproduction, taking snippets, or
 * claiming any contents as your own will break the terms of the license, and void any
 * agreements with you, the third party.
 * Thanks
 * ************************************************************************
 */
public class request extends Command {


    public request() {
        super("request");
    }

    public static enum TimeUnit
    {
        FIT,  DAYS,  HOURS,  MINUTES,  SECONDS,  MILLISECONDS;
    }

    public static double trim(int degree, double d)
    {
        String format = "#.#";
        for (int i = 1; i < degree; i++) {
            format = format + "#";
        }
        DecimalFormat twoDForm = new DecimalFormat(format);
        return Double.valueOf(twoDForm.format(d)).doubleValue();
    }

    public static String convertString(long time, int trim, TimeUnit type)
    {
        if (time == -1L) {
            return "Permanent";
        }
        if (type == TimeUnit.FIT) {
            if (time < 60000L) {
                type = TimeUnit.SECONDS;
            } else if (time < 3600000L) {
                type = TimeUnit.MINUTES;
            } else if (time < 86400000L) {
                type = TimeUnit.HOURS;
            } else {
                type = TimeUnit.DAYS;
            }
        }
        if (type == TimeUnit.DAYS) {
            return trim(trim, time / 86400000.0D) + " Days";
        }
        if (type == TimeUnit.HOURS) {
            return trim(trim, time / 3600000.0D) + " Hours";
        }
        if (type == TimeUnit.MINUTES) {
            return trim(trim, time / 60000.0D) + " Minutes";
        }
        if (type == TimeUnit.SECONDS) {
            return trim(trim, time / 1000.0D) + " Seconds";
        }
        return trim(trim, time) + " Milliseconds";
    }

    private HashMap<ProxiedPlayer, Long> times = new HashMap<ProxiedPlayer, Long>();

    public static boolean elapsed(long from, long required)
    {
        return System.currentTimeMillis() - from > required;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!(sender instanceof ProxiedPlayer)){
            sender.sendMessage("You must be a player");
            return;
        }

        ProxiedPlayer senderr = (ProxiedPlayer) sender;
        if(times.get(sender) != null && !elapsed(times.get(senderr), 5 * 60 * 1000)){
//            senderr.sendMessage("§3Request §7>> You must wait " + convertString(times.get(senderr) - System.currentTimeMillis(), 1, TimeUnit.FIT) );
//            ((ProxiedPlayer) sender).sendMessage("§3Request §7>> This command is currently on cooldown.");
            senderr.sendMessage("§3Request §7>> You must wait §a" + convertString(5 * 60 * 1000 - (System.currentTimeMillis() - times.get(senderr)), 1, TimeUnit.FIT) + "§7 until you can use this command again.");
            return;
        }

        ArrayList<ProxiedPlayer> staff = new ArrayList<ProxiedPlayer>();
        for(ProxiedPlayer player : ProxyServer.getInstance().getPlayers()){
            if(player.hasPermission("core.*") || player.hasPermission("core.request")){
                staff.add(player);
            }
        }
        for(ProxiedPlayer player : staff){
            player.sendMessage("§3Request §7>> §a" + sender.getName() + " §7needs help on server: §a" + senderr.getServer().getInfo().getName());
        }
        if(times.containsKey(senderr))
            times.remove(senderr);

        times.put(senderr, System.currentTimeMillis());
    }
}


