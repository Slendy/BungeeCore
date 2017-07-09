package com.slendy.bungeecore;

import java.io.File;
import java.net.InetSocketAddress;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

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



public class Helper {

    public static boolean addServer(String name, String host, int port)
    {
        if (ProxyServer.getInstance().getServers().containsKey(name))
        {
            return false;
        }

        ServerInfo serverInfo = ProxyServer.getInstance().constructServerInfo(name, new InetSocketAddress(host, port), "", false);
        ProxyServer.getInstance().getServers().put(name, serverInfo);
        try
        {
            Configuration bungeeConfig = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File("config.yml"));
            bungeeConfig.set("servers." + name + ".address", host + ":" + port);
            bungeeConfig.set("servers." + name + ".restricted", Boolean.valueOf(false));
            bungeeConfig.set("servers." + name + ".motd", "");
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(bungeeConfig, new File("config.yml"));
        }
        catch (Exception e) {
            e.printStackTrace();
            ProxyServer.getInstance().getLogger().severe("Couldn't open Bungee config! Send the mess above to Slendy.");
            return false;
        }

        return true;
    }

    public static boolean removeServer(String name) {
        if (!ProxyServer.getInstance().getServers().containsKey(name)) {
            return false;
        }

        for (ProxiedPlayer plyr : ((ServerInfo) ProxyServer.getInstance().getServers().get(name)).getPlayers()) {
            plyr.disconnect(new ComponentBuilder(("§3Core §7>>")).append(" Server was cleared, please rejoin.").color(ChatColor.GRAY).create());
        }

        ProxyServer.getInstance().getServers().remove(name);
        try {
            Configuration bungeeConfig = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File("config.yml"));
            bungeeConfig.set("servers." + name, null);
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(bungeeConfig, new File("config.yml"));
        } catch (Exception e) {
            e.printStackTrace();
            ProxyServer.getInstance().getLogger().severe("Couldn't open Bungee config! Send the mess above to Slendy");
            return false;
        }

        return true;
    }


}

