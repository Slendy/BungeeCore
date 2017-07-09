package com.slendy.bungeecore.Commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.command.PlayerCommand;

import java.util.ArrayList;

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
public class ReportCommand extends PlayerCommand {

    public ReportCommand(){ super("report"); }

    public void usage(ProxiedPlayer player){ player.sendMessage("§3Report §7>> /report <player> <reason> - Reports a player."); }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!(sender instanceof ProxiedPlayer)) return;
        ProxiedPlayer p = (ProxiedPlayer) sender;
        String reason = "";
        if(args.length < 2){
            usage(p);
            return;
        }

        ProxiedPlayer troublesomePlayer = ProxyServer.getInstance().getPlayer(args[0]);
        if(troublesomePlayer == null){
            p.sendMessage("§3Report §7>> That player is not online");
            return;
        }

        for(int i = 1; i < args.length; i++){
            if(i == args.length - 1)
                reason = reason + args[i];
            else {
                reason = reason + args[i] + " ";
            }
        }
        ArrayList<ProxiedPlayer> staff = new ArrayList<ProxiedPlayer>();
        if(ProxyServer.getInstance().getPlayers().size() > 0) {
            for(ProxiedPlayer plyr : ProxyServer.getInstance().getPlayers()){
                if(plyr.hasPermission("core.staff") || plyr.hasPermission("core.*")){
                    staff.add(plyr);
                }
            }
        }
        if(staff.size() == 0){
            p.sendMessage("§3Report §7>> If there are any staff online they will review your report shortly.");
        } else {
            for(ProxiedPlayer pl : staff){
                pl.sendMessage("§3Report §7>> Report from §a" + p.getName() + " §7[§a" + p.getServer().getInfo().getName() + "§7] received.");
                pl.sendMessage("§3Report §7>> Troublesome player: §a" + troublesomePlayer.getName() + " §7[§a" + troublesomePlayer.getServer().getInfo().getName() + "§7]");
                pl.sendMessage("§3Report §7>> Report reason: §a" + reason);
            }
            staff.clear();
            p.sendMessage("§3Report §7>> Thanks for your report!");
        }


    }
}


