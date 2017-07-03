package com.slendy.bungeecore.Commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.Title;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

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
public class CommandAlert extends Command {

    public CommandAlert(){
        super("alert");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
            if(sender.hasPermission("core.alert") || sender.hasPermission("core.*")) {
                if (args.length == 0) {
                    sender.sendMessage("§3Announcement §7>> Incorrect usage. /alert <message>");
                } else {
                    StringBuilder builder = new StringBuilder();
                    String[] arrayOfString;
                    int j = (arrayOfString = args).length;
                    for (int i = 0; i < j; i++) {
                        String s = arrayOfString[i];
                        builder.append(ChatColor.translateAlternateColorCodes('&', s));
                        builder.append(" ");
                    }
                    String starter = "§3Announcement §7>> §b";
                    String message = builder.substring(0, builder.length() - 1);
                    Title title = ProxyServer.getInstance().createTitle();
                    TextComponent titleComp = new TextComponent("§3§lAnnouncement");

                    for (ProxiedPlayer p1 : ProxyServer.getInstance().getPlayers()) {
                        p1.sendMessage(starter + ChatColor.translateAlternateColorCodes('&', message));
                    }
                    TextComponent titleSubComp = new TextComponent("§b" + message);

                    title.title(titleComp);
                    title.subTitle(titleSubComp);
                    title.fadeIn(20);
                    title.stay(100);
                    title.fadeOut(40);
                    for (ProxiedPlayer proxplayer : ProxyServer.getInstance().getPlayers()) {
                        proxplayer.sendTitle(title);
                    }
                }
            }

    }
}


