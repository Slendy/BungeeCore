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
public class DdosCommand extends PlayerCommand {
    //fun inside joke :^)

    public DdosCommand(){
        super("ddos");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length == 0) {
            sender.sendMessage("§cUsage: /ddos <player>");
        }
        if(args.length == 1){
            ProxiedPlayer player = ProxyServer.getInstance().getPlayer(args[0]);
            if(player == null){
                sender.sendMessage("§cThe entity UUID provided is in an invalid format");
                return;
            }
            sender.sendMessage("Attack initiated. Watch them lag ;)");
        }

    }
}


