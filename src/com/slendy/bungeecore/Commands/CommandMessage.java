package com.slendy.bungeecore.Commands;

import com.slendy.bungeecore.core;
import net.md_5.bungee.api.ChatColor;
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
public class CommandMessage extends PlayerCommand {

    public CommandMessage() { super("msg", "", "m", "message", "w", "whisper", "tell", "t"); }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ProxiedPlayer){
            if(args.length < 2){
                sender.sendMessage("§3Message§7 >> /msg <player> <msg> - Sends a private message to the specified player");
                return;
            }
            ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[0]);
            if(target == null){
                sender.sendMessage("§3Message§7 >> Player not found");
                return;
            }
            StringBuilder msg = new StringBuilder();
            for(int i = 1; i < args.length; i++){
                msg.append(args[i]).append(" ");
            }

            String msg1 = ChatColor.translateAlternateColorCodes('&', msg.toString().trim());
            if(target.getName().equalsIgnoreCase("Slendy")){
                sender.sendMessage("§dSlendy is often AFK or minimized, due to plugin development.");
                sender.sendMessage("§dPlease be patient if he does not reply instantly.");
            }
            target.sendMessage("§2§l" + sender.getName() + " > " + target.getName() + "§a§l " + msg1);
            sender.sendMessage("§2§l" + sender.getName() + " > " + target.getName() + "§a§l " + msg1);
            if(core.lastmessage.containsKey(sender.getName())){
                core.lastmessage.remove(sender.getName());
            }
            core.lastmessage.put(sender.getName(), target.getName());
            if(core.lastmessage.containsKey(target.getName())){
                core.lastmessage.remove(target.getName());
            }
            core.lastmessage.put(target.getName(), sender.getName());
        }

    }
}


