package me.ed333.easyBot.events;

import me.ed333.easyBot.BOT;
import me.ed333.easyBot.BotMain;
import me.ed333.easyBot.Client;
import me.ed333.easyBot.events.bot.MessageEvent.GroupMessageReceiveEvent;
import me.ed333.easyBot.utils.MessageChain;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import static me.ed333.easyBot.utils.Messages.getMsg;

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
    }

    @EventHandler
    public void onLeave (PlayerQuitEvent event) {
        BOT.enableBot_Players.remove(event.getPlayer());
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
            String msg = event.getMessage();
            MessageChain chain = new MessageChain();
            chain.addPlain(getMsg("groupFormat", event.getPlayer())).addPlain(msg);
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (Client.isConnected) {
                        try {
                            BOT.sendGroupMessage(BOT.groupID, false, 0, chain);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    cancel();
                }
            }.runTaskTimer(BotMain.getPlugin(BotMain.class), 0L, BOT.sendDelay);
    }

    @EventHandler
    public void  onGroupMessage(GroupMessageReceiveEvent event) {
        ConsoleCommandSender sender = Bukkit.getConsoleSender();
        if (event.getGroupId() == BOT.groupID) {
            String catchType = BotMain.cfg.getString("catch.type");
            for (Player p : BOT.enableBot_Players) {
                if (catchType.equals("text") && BotMain.cfg.getBoolean("catch.text")) {
                    sender.sendMessage(event.getRawText());
                }
                else if (catchType.equals("multi")) {
                    p.spigot().sendMessage(event.getMulti());
                }
            }
            if (catchType.equals("multi")) sender.spigot().sendMessage(event.getMulti());
            if (catchType.equals("text")) sender.sendMessage(event.getRawText());
        }
    }
}
