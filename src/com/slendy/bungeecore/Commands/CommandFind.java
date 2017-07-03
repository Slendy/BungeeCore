package com.slendy.bungeecore.Commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.command.PlayerCommand;

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
public class CommandFind extends PlayerCommand {

    public CommandFind() { super("find"); }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender.hasPermission("core.find") || sender.hasPermission("core.*"))
        if(args.length != 1){
            sender.sendMessage("§3Find §7>> Incorrect usage. /find <username>");
        } else {
            ProxiedPlayer player = ProxyServer.getInstance().getPlayer(args[0]);
            if(player == null || player.getServer() == null){
                sender.sendMessage("§3Find §7>> Failed to locate [§a" + args[0] + "§7].");
            } else {
                sender.sendMessage("§3Find §7>> Located [§a" + player.getName() + "§7] at §a" + player.getServer().getInfo().getName());
            }

        }

    }
}


