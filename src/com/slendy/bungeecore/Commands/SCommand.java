package com.slendy.bungeecore.Commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.ArrayList;
import java.util.List;

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
public class SCommand extends Command {

    public SCommand(){
        super("s");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!(sender instanceof ProxiedPlayer)){
            sender.sendMessage("§3Staff Chat §7>> You must be a player.");
            return;
        }
        ProxiedPlayer player = (ProxiedPlayer) sender;
        String say = "";
        for(String str : args){
            say = say + str + " ";
        }
        if(player.hasPermission("core.staff") || player.hasPermission("core.*")) {
            if (args.length == 0) {
                player.sendMessage("§3Staff Chat §7>> Incorrect usage. /s [Message]");
            } else {
                for (ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {
                    if (p.hasPermission("core.staff") || p.hasPermission("core.*")){
                        p.sendMessage("§7[§3" + player.getServer().getInfo().getName() + "§7] §a" + player.getName() + "§7: §f" + say);
                    }
                }
            }
        } else {
            player.sendMessage("§3Permissions §7>> You don't have the required permissions to do that.");

        }
    }
}


