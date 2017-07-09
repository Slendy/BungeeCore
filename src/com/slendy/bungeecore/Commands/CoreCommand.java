package com.slendy.bungeecore.Commands;


import java.util.Map;

import com.slendy.bungeecore.Helper;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.config.ServerInfo;
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

public class CoreCommand extends Command {
    public CoreCommand() {
        super("core");
    }

    public void usage(ProxiedPlayer player) {
        if (player.hasPermission("core.servers") || player.hasPermission("core.*")) {
            player.sendMessage("§3Core§7 >> /core info <server>");
            player.sendMessage("§3Core§7 >> /core server add <name> <host:port> - Adds a server.");
            player.sendMessage("§3Core§7 >> /core server remove <name> - Removes a server.");
            player.sendMessage("§3Core§7 >> /core server clear <name> [fallback] - Clears players from a server [and sends players to the fallback server].");
            player.sendMessage("");

        }
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if ((sender instanceof ProxiedPlayer)) {
            ProxiedPlayer player = (ProxiedPlayer) sender;
            if (player.hasPermission("core.*") || player.hasPermission("core.core")) {

                if (args.length == 0) {
                    usage(player);
                } else {
                    if (args.length == 1) {
                        if (args[0].equalsIgnoreCase("server")) {
                            player.sendMessage("§3Core§7 >> /core server add <name> <host:port> - Adds a server.");
                            player.sendMessage("§3Core§7 >> /core server remove <name> - Removes a server.");
                            player.sendMessage("§3Core§7 >> /core server clear <name> [fallback] - Clears players from a server [and sends players to the fallback server].");
                            player.sendMessage("");
                            return;
                        }
                        if (args[0].equalsIgnoreCase("info")) {
                            player.sendMessage("§3Core§7 >> /core info <server>");
                            player.sendMessage("");
                            return;

                        }

                    }
                    if (args.length == 2) {
                        if (args[0].equalsIgnoreCase("server")) {
                            if (args[1].equalsIgnoreCase("add")) {
                                player.sendMessage("§3Core§7 >> /core server add <name> <host:port> - Adds a server.");
                                player.sendMessage("");
                                return;
                            }
                            if (args[1].equalsIgnoreCase("remove")) {
                                player.sendMessage("§3Core§7 >> /core server remove <name> - Removes a server.");
                                player.sendMessage("");
                                return;
                            }
                            if (args[1].equalsIgnoreCase("clear")) {
                                player.sendMessage("§3Core§7 >> /core server clear <name> [fallback] - Clears players from a server [and sends players to the fallback server].");
                                player.sendMessage("");
                                return;

                            }
                        }
                    }
                    if (args[0].equalsIgnoreCase("server")) {
                        if (player.hasPermission("core.servers") || player.hasPermission("core.*")) {

                            if ((args[1].equalsIgnoreCase("add")) && (args.length == 4)) {
                                String host = args[3].split(":")[0];
                                int port = 25565;
                                try {
                                    String portTmp = args[3].split(":")[1];

                                    if (!portTmp.equalsIgnoreCase("")) {
                                        port = Integer.parseInt(portTmp);
                                    }
                                } catch (Exception e) {
                                    player.sendMessage("§3Core§7 >> Invalid port specified.");

                                    return;
                                }

                                if (Helper.addServer(args[2], host, port)) {
                                    player.sendMessage("§3Core§7 >> Server added successfully.");
                                } else {
                                    player.sendMessage("Core> Failed to add server: does it already exist?");
                                }

                            } else if ((args[1].equalsIgnoreCase("remove")) && (args.length == 3)) {
                                if (Helper.removeServer(args[2])) {
                                    player.sendMessage("§3Core§7 >> Successfully removed server.");
                                } else {
                                    player.sendMessage("§3Core§7 >> Failed to remove server: does it exist?");
                                }

                            } else if ((args[1].equalsIgnoreCase("clear")) && (args.length == 4)) {
                                ServerInfo fallback = ProxyServer.getInstance().getServerInfo(args[3]);

                                if (fallback == null) {
                                    player.sendMessage("§3Core§7 >> Fallback server not found.");

                                    return;
                                }

                                for (ProxiedPlayer plyr : ((ServerInfo) ProxyServer.getInstance().getServers().get(args[2])).getPlayers()) {
                                    plyr.sendMessage("§3Core§7 >> Your server has been cleared and you have been sent to a fallback server.");

                                    plyr.connect(fallback);
                                }
                                player.sendMessage("§3Core§7 >> Successfully cleared server.");
                            } else if ((args[1].equalsIgnoreCase("clear")) && (args.length == 3)) {
                                for (ProxiedPlayer plyr : ((ServerInfo) ProxyServer.getInstance().getServers().get(args[2])).getPlayers()) {
                                    player.sendMessage("§3Core§7 >> Server was cleared, please rejoin.");
                                }
                                player.sendMessage("§3Cire> Successfully cleared server.");
                            } else {
                                usage(player);
                            }
                            return;
                        }
                        player.sendMessage("§3Core> §7You don't have the required permissions for this command.");
                        return;
                    }
                    if (args[0].equalsIgnoreCase("info") && args.length == 2) {
                        if (player.hasPermission("core.*")) {
                            ServerInfo target = ProxyServer.getInstance().getServerInfo(args[1]);
                            if (target != null) {
                                player.sendMessage("§3Core§7 >> Server: §a" + target.getName() + " §7IP: §a" + target.getAddress() + "§7.");
                            } else {
                                player.sendMessage("§3Core§7 >> The server §a" + args[1] + " §7does not exist.");
                            }
                        }
                        return;
                    }

                    usage(player);
                }

                return;
            } else {
                player.sendMessage("§3Core> §7You don't have permission to use this command.");
            }
        }
        sender.sendMessage("§3Core§7 >> This command is unavailable in the console, sorry");
    }
}