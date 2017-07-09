package com.slendy.bungeecore.Commands;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.ArrayList;
import java.util.Collection;


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
public class ServerCommand extends Command {

    public ServerCommand() {
        super("server");
    }

    public static <T> String listToString(Collection<T> inputList, boolean comma, String color, String commacolor)
    {
        String out = "";
        for (T cur : inputList) {
            out = out + color + cur.toString() + (comma ? commacolor + ", " : " ");
        }
        if (out.length() > 0) {
            out = out.substring(0, out.length() - (comma ? 2 : 1));
        }
        return out;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if((sender instanceof ProxiedPlayer)){

            ProxiedPlayer player = (ProxiedPlayer)sender;
            if(args.length == 0){
                Collection<String> names = BungeeCord.getInstance().getServers().keySet();
                player.sendMessage("§3Server §7>> You are currently connected to: §a" + player.getServer().getInfo().getName());
                player.sendMessage("§3Server §7>> Available Servers: " + listToString(names, true, "§a", "§7") + "§7.");
            }

            if(args.length == 1){
                ServerInfo target = ProxyServer.getInstance().getServerInfo(args[0]);
                if(target != null){
                    if(!player.getServer().getInfo().getName().equals(target.getName())) {
                        if(target.canAccess(player)) {
                            player.sendMessage("§3Server §7>> Sending you to §a" + target.getName() + " §7from §a" + player.getServer().getInfo().getName() + "§7.");
                            player.connect(target);
                        } else {
                            player.sendMessage("§3Serer §7>>> §cYou don't have permission to join §a" + target.getName() + "");
                        }

                    } else {
                        player.sendMessage("§3Server §7>> You are already on that server.");
                    }


                } else {
                    player.sendMessage("§3Server §7>> The server §a" + args[0] + " §7does not exist.");
                }
            }

        } else {
            sender.sendMessage("§3Server §7>> You must be a player to use this command!");
        }

    }
}
//
//
