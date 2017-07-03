package com.slendy.bungeecore.Commands;

import com.slendy.bungeecore.core;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ProxyReloadEvent;
//import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.command.PlayerCommand;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

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
public class setrank extends PlayerCommand {

    private core plugin = core.getPlugin();

    public setrank(){
        super("setrank");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender.hasPermission("core.setrank") || sender.hasPermission("core.*")){
            if(args.length != 2){
                sender.sendMessage("§3Rank §7>> /setrank <player> <rank> - Sets a players rank through BungeeCord. (Rank is case sensitive)");
            } else {
                ProxiedPlayer p = ProxyServer.getInstance().getPlayer(args[0]);
                String rank = args[1];
                if(p == null){
                    sender.sendMessage("§3Rank §7>> Player not found");
                    return;
                }
                p.removeGroups((String[])p.getGroups().toArray(new String[p.getGroups().size()]));
                p.addGroups(new String[] {rank});
                File file = new File(plugin.getDataFolder().getParentFile().getParent(), "config.yml");
                try{
                    Configuration config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
                    for(String remove : p.getGroups()){
                        config.getStringList("groups." + p.getName()).remove(remove);
                    }
                    p.disconnect("§3Rank §7>> Your BungeeCord rank has been set to §a" + core.capitalizeFirstLetter(rank.toLowerCase()) + " §7by §a" + sender.getName() + "§7. \n §7Please relogin.");
                    sender.sendMessage("§3Rank §7>> You set §a" + p.getName() + "'s §7rank to §a" + core.capitalizeFirstLetter(rank.toLowerCase()) + "§7.");
                    config.getStringList("groups." + p.getName()).add(rank);
                    config.set("groups." + p.getName(), Arrays.asList(new String[] {rank}));
                    ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, file);
                    BungeeCord.getInstance().config.load();
                    BungeeCord.getInstance().stopListeners();
                    BungeeCord.getInstance().startListeners();
                    BungeeCord.getInstance().getPluginManager().callEvent(new ProxyReloadEvent(sender));


                }catch(IOException e){

                }


            }

        } else {
            sender.sendMessage("§3Permissions §7>> You don't have the required permission for that command.");
        }

    }
}


