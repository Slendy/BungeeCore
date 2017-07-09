package com.slendy.bungeecore;

import com.slendy.bungeecore.Commands.*;
import com.slendy.bungeecore.Commands.ServerCommand;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.event.EventHandler;

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
public class Core extends Plugin implements Listener {

    private static Core plugin;

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

    @Override
    public void onEnable() {
        plugin = this;

        getProxy().getPluginManager().registerCommand(this, new AlertCommand());
        getProxy().getPluginManager().registerCommand(this, new FindCommand());
        getProxy().getPluginManager().registerCommand(this, new SendCommand());
        getProxy().getPluginManager().registerCommand(this, new StatusCommand());
        getProxy().getPluginManager().registerCommand(this, new SCommand());
        getProxy().getPluginManager().registerCommand(this, new StaffCommand());
        getProxy().getPluginManager().registerCommand(this, new SetRankCommand());
        getProxy().getPluginManager().registerCommand(this, new ServerCommand());
        getProxy().getPluginManager().registerCommand(this, new CoreCommand());
        getProxy().getPluginManager().registerCommand(this, new ReportCommand());
        getProxy().getPluginManager().registerCommand(this, new TroubleCommand());
        getProxy().getPluginManager().registerCommand(this, new MessageCommand());
        getProxy().getPluginManager().registerCommand(this, new ReplyCommand());
        getProxy().getPluginManager().registerCommand(this, new DdosCommand());
        getProxy().getPluginManager().registerCommand(this, new PingCommand());
        getProxy().getPluginManager().registerCommand(this, new RequestCommand());

        getProxy().getPluginManager().registerListener(this, this);
        }

    @Override
    public void onDisable(){
        plugin = null;
    }

    public static Core getPlugin(){
        return plugin;
    }





}


