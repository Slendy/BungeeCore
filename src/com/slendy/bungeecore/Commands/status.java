package com.slendy.bungeecore.Commands;

import net.md_5.bungee.api.Callback;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.plugin.Command;

import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

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
public class status extends Command {


    public status() {
        super("status");
    }

    @Override
    public void execute(final CommandSender sender, String[] args) {
        if(!sender.hasPermission("core.*"))
            return;
        sender.sendMessage("");
        sender.sendMessage("There are " + ProxyServer.getInstance().getServers().size() + " total proxy servers.");
        final AtomicInteger offline = new AtomicInteger(0);
        final AtomicInteger online = new AtomicInteger(0);
        final AtomicInteger total = new AtomicInteger(0);
        for(Map.Entry<String, ServerInfo> entry : ProxyServer.getInstance().getServers().entrySet()){
            final String name = entry.getKey();
            final ServerInfo sinfo = entry.getValue();
            sinfo.ping(new Callback<ServerPing>() {
                public void done(ServerPing result, Throwable error) {
                    if(error == null){
                        sender.sendMessage("§l" + name + " §ris online with " + sinfo.getPlayers().size() + (sinfo.getPlayers().size() == 1 ? " Player" : " Players") + " (" + sinfo.getAddress().getAddress().getHostAddress() + ":" + sinfo.getAddress().getPort() + ")");
                        online.getAndAdd(1);
                        total.getAndAdd(1);

//                        sender.sendMessage("§8[§a" + name + "§8]§7:" + " §aOnline §7(§a" + sinfo.getPlayers().size() + "§7" + (sinfo.getPlayers().size() == 1 ? " Player" : " Players") + ").");
                    } else {
                        sender.sendMessage("§l" + name + " §ris offline (" + sinfo.getAddress().getAddress().getHostAddress() + ":" + sinfo.getAddress().getPort() + ")");
                        offline.getAndAdd(1);
                        total.getAndAdd(1);
//                        sender.sendMessage("§8[§a" + name + "§8]§7:" + " §cOffline§7.");
                        //dab
                    }
                    if(total.get() == ProxyServer.getInstance().getServers().size()){
                        sender.sendMessage("§l§nSUMMARY§r: " + online.get() + (online.get() == 1 ? " Server is " : " Servers are ") + "online, " + offline.get() + (offline.get() == 1 ? " Server is " : " Servers are ") + "offline.");
//                        sender.sendMessage(total.get() + " total, " + online.get() + " online, " + offline.get() + " offline.");
                    }
                }


            });
        }
//        sender.sendMessage("SUMMARY: " + online);
//        if(total.get() == ProxyServer.getInstance().getServers().size()) {

//        }

    }
}


