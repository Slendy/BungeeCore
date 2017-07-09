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
public class TroubleCommand extends PlayerCommand {

    public TroubleCommand(){ super("trouble"); }

    public void usage(ProxiedPlayer player){ player.sendMessage("§3Report §7>> /trouble <player> <reason> - Sends the player a message about their report (without name)."); }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!(sender instanceof ProxiedPlayer)) return;
        ProxiedPlayer player = (ProxiedPlayer) sender;
        if(player.hasPermission("core.staff") || player.hasPermission("core.*")){
            if(args.length < 2){
                usage(player);
                return;
            }

            String message = "";
            for(int i = 1; i < args.length; i++){
                if(i == args.length - 1)
                    message = message + args[i];
                else {
                    message = message + args[i] + " ";
                }
            }

            ProxiedPlayer reporter = ProxyServer.getInstance().getPlayer(args[0]);
            if(reporter == null){
                player.sendMessage("§3Report §7>> That player is not online");
                return;
            }
            reporter.sendMessage("§3Report §7>> " + message);
            player.sendMessage("§3Trouble §7>> Message sent to §a" + reporter + "§7.");
            player.sendMessage("§3Preview §7>> " + message);

        }

    }
}


