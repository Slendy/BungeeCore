package com.slendy.bungeecore;

import com.slendy.bungeecore.Commands.*;
import com.slendy.bungeecore.Commands.Server;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import net.md_5.bungee.event.EventHandler;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.HashMap;


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
public class core extends Plugin implements Listener {

    private static core plugin;

    public static String capitalizeFirstLetter(String original)
    {
        if ((original == null) || (original.length() == 0)) {
            return original;
        }
        return original.substring(0, 1).toUpperCase() + original.substring(1);
    }

    public static HashMap<String, String> lastmessage = new HashMap<String, String>();

    @EventHandler
    public void onQuit(PlayerDisconnectEvent e){
        String name = e.getPlayer().getName();
        if(lastmessage.containsKey(name)){
            lastmessage.remove(name);
        }
    }

    @EventHandler(priority = 124)
    public void onPing(ProxyPingEvent e){
        ServerPing r = e.getResponse();
        ServerPing.Players p = r.getPlayers();
        p = new ServerPing.Players(p.getOnline() + 1, p.getOnline(), p.getSample());
        ServerPing ping = new ServerPing(r.getVersion(), p, r.getDescription(), r.getFaviconObject());
        e.setResponse(ping);
    }




    private static core instance;

    public Configuration config;

    @Override
    public void onEnable() {
        plugin = this;

        getProxy().getPluginManager().registerCommand(this, new CommandAlert());
        getProxy().getPluginManager().registerCommand(this, new CommandFind());
        getProxy().getPluginManager().registerCommand(this, new CommandSend());
        getProxy().getPluginManager().registerCommand(this, new status());
        getProxy().getPluginManager().registerCommand(this, new SCommand());
        getProxy().getPluginManager().registerCommand(this, new StaffCommand());
        getProxy().getPluginManager().registerCommand(this, new setrank());
        getProxy().getPluginManager().registerCommand(this, new Server());
        getProxy().getPluginManager().registerCommand(this, new CommandCore());
        getProxy().getPluginManager().registerCommand(this, new CommandReport());
        getProxy().getPluginManager().registerCommand(this, new CommandTrouble());
        getProxy().getPluginManager().registerCommand(this, new CommandMessage());
        getProxy().getPluginManager().registerCommand(this, new CommandReply());
        getProxy().getPluginManager().registerCommand(this, new CommandDdos());
        getProxy().getPluginManager().registerCommand(this, new ping());
//        getProxy().getPluginManager().registerCommand(this, new exec());
        getProxy().getPluginManager().registerCommand(this, new request());

        getProxy().getPluginManager().registerListener(this, this);
        Helper.setPlugin(this);
//        getLogger().info("Enabled");
        instance = this;
        }

    @Override
    public void onDisable(){

//        getLogger().info("Disabled");
        plugin = null;
    }

    public static core getPlugin(){
        return plugin;
    }





}


