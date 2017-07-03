package com.slendy.bungeecore.Commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
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
public class CommandSend extends PlayerCommand {

    public CommandSend() {
        super("send");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender.hasPermission("core.send") || sender.hasPermission("core.*")) {
            if (args.length != 2) {
                sender.sendMessage("§3Send §7>> Incorrect usage. /send <server|player|all|current> <target>");
                return;

            } else {
                ServerInfo target = ProxyServer.getInstance().getServerInfo(args[1]);
                if (target == null) {
                    sender.sendMessage("§3Send §7>> The specified server does not exist.");
                    return;
                }
                if (args[0].equalsIgnoreCase("all")) {
                    for (ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {
                        if (!p.getServer().getInfo().getName().equals(target.getName())) {
                            p.connect(target);
                            p.sendMessage("§3Send §7>> You were summoned to §a" + target.getName() + "§7.");
                        }
                    }

                } else if (args[0].equalsIgnoreCase("current")) {
                    if (!(sender instanceof ProxiedPlayer)) {
                        sender.sendMessage("§3Send §7>> Only players can use this command.");
                        return;
                    }
                    ProxiedPlayer player = (ProxiedPlayer) sender;
                    for (ProxiedPlayer p : player.getServer().getInfo().getPlayers()) {
                        if (!p.getServer().getInfo().getName().equals(target.getName())) {
                            p.connect(target);
                            p.sendMessage("§3Send §7>> You were summoned to §a" + target.getName() + "§7.");
                        }

                    }
                } else {
                    ServerInfo serverTarget = ProxyServer.getInstance().getServerInfo(args[0]);
                    if (serverTarget != null) {
                        for (ProxiedPlayer p : serverTarget.getPlayers()) {
                            if (!p.getServer().getInfo().getName().equals(target.getName())) {
                                p.connect(target);
                                p.sendMessage("§3Send §7>> You were summoned to §a" + target.getName() + "§7.");
                            }
                        }
                    } else {
                        ProxiedPlayer player = ProxyServer.getInstance().getPlayer(args[0]);
                        if (player == null) {
                            sender.sendMessage("§3Send §7>> That player is not online");
                            return;
                        }
                        if (!player.getServer().getInfo().getName().equals(target.getName())) {
                            player.connect(target);
                            player.sendMessage("§3Send §7>> You were summoned to §a" + target.getName() + "§7.");
                        }
                    }
                }
            }
            sender.sendMessage("§3Send §7>> Successfully summoned players.");
        } else {
            sender.sendMessage("§3Permissions §7>> You don't have the required permissions for that command.");
        }
    }
}


