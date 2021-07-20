package me.ed333.easyBot.events;

import me.ed333.easyBot.BOT;
import me.ed333.easyBot.BotMain;
import me.ed333.easyBot.Client;
import me.ed333.easyBot.events.bot.MessageEvent.GroupMessageReceiveEvent;
import me.ed333.easyBot.utils.MessageChain;
import me.ed333.easyBot.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * 插件默认监听的一些事件
 */
public class ListeningEvent implements Listener {
    @EventHandler
    public void onJoin (PlayerJoinEvent event) {
        Player p = event.getPlayer();
        if (!BOT.enableBot_Players.contains(p)) {
            if (BotMain.playerConfig.getBoolean(p.getUniqueId() + ".enable_Bot")) BOT.enableBot_Players.add(p);
            else if (!BOT.name_isBound(p.getName())) {
                BotMain.playerConfig.set(p.getUniqueId() + ".enable_Bot", true);
                BOT.enableBot_Players.add(p);
            }
        }

        BotMain.playerConfig.set(p.getUniqueId() + ".lastName", p.getName());
        Messages.printDEBUG(BOT.enableBot_Players.toString());
    }

    @EventHandler
    public void onLeave (PlayerQuitEvent event) {
        BOT.enableBot_Players.remove(event.getPlayer());
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) throws Exception {
        String msg = event.getMessage();
        if (Client.isConnected) {
            BOT.sendGroupMessage(
                    BOT.groupID,
                    false,
                    0,
                    new MessageChain()
                            .addPlain(event.getPlayer().getName())
                            .addPlain(":")
                    .addPlain(msg)
            );
        }
    }

    @EventHandler
    public void  onGroupMessage(GroupMessageReceiveEvent event) {
        ConsoleCommandSender sender = Bukkit.getConsoleSender();
        if (event.getGroupId().equals(BOT.groupID)) {
            String catchType = BotMain.cfg.getString("catch.type");
            for (Player p : BOT.enableBot_Players) {
                if (catchType.equals("text") && BotMain.cfg.getBoolean("catch.text")) {
                    sender.sendMessage(event.getMessage());
                }
                else if (catchType.equals("multi")) {
                    p.spigot().sendMessage(event.getMulti());
                }
            }
            sender.spigot().sendMessage(event.getMulti());
        }
    }
}
