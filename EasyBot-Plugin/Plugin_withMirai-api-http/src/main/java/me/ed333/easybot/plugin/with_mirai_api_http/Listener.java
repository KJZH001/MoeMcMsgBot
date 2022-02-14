package me.ed333.easybot.plugin.with_mirai_api_http;

import com.google.gson.JsonParser;
import me.ed333.easybot.api.BotAPI;
import me.ed333.easybot.api.Vars;
import me.ed333.easybot.api.events.messageevent.GroupMessage;
import me.ed333.easybot.api.messages.MessageChain;
import me.ed333.easybot.api.messages.MessageSection;
import me.ed333.easybot.api.utils.IBotMiraiHttpUtils;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;


public class Listener implements org.bukkit.event.Listener {
    private final IBotMiraiHttpUtils ibmh = BotAPI.getIBotMiraiHttpUtils();

    @EventHandler
    void onGroupMessage(@NotNull GroupMessage event) {
        if (ibmh.getAllGroups().containsValue(event.getGroup())) {
            Vars.senderId = event.getSender().ID();
            Vars.senderNick = event.getSender().MemberName();
            Vars.groupID = event.getGroup().id();

            TextComponent msg = event.getMessageChain().toTextComponent();
            TextComponent txt = new TextComponent();
            txt.addExtra(ibmh.getGameName_ToComponent(event.getSender().ID()));
            txt.addExtra(msg);

            ibmh.getEnabledPlayers().forEach(player -> player.spigot().sendMessage(txt));
            Bukkit.getConsoleSender().spigot().sendMessage(txt);
        }
    }

    @EventHandler
    void onPlayerJoin(@NotNull PlayerJoinEvent event) {
        Player p = event.getPlayer();
        YamlConfiguration data = BotAPI.getiConfigManager().getData();

        String cfgPath = "Player." + p.getUniqueId();

        if (!ibmh.getEnabledPlayers().contains(p)) {
            if (data.getBoolean(cfgPath + ".enableBot")) {
                ibmh.addEnableBotPlayer(p);
            } else if (!ibmh.name_isBound(p.getName())) {
                data.set(cfgPath + ".enableBot", true);
                ibmh.addEnableBotPlayer(p);
            }
        }

        data.set(cfgPath + ".name", p.getName());
        try {
            data.save(BotAPI.getiConfigManager().getDataFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @EventHandler
    void onPlayerChat(@NotNull AsyncPlayerChatEvent event) {
        String eventMessage = event.getMessage();
        MessageChain chain = new MessageChain();
        chain.addPlain(BotAPI.getILanguageUtils().getLangText("groupFormat", event.getPlayer()).replace("§7§l[§a提示§7§l] ", ""));
        chain.addPlain(eventMessage.replace(String.format("#reply_%s#", Vars.msgID), ""));

        if (eventMessage.startsWith(String.format("#reply_%s#", Vars.msgID))) {
            event.setMessage(eventMessage.replace(String.format("#reply_%s#", Vars.msgID), ""));
            String reply_msgData = ibmh.messageFromId(Vars.msgID);
            long replyGroupId = new JsonParser().parse(reply_msgData).getAsJsonObject().get("sender").getAsJsonObject().get("group").getAsJsonObject().get("id").getAsLong();
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (SocketClient.isConnected) {
                        BotAPI.getIbu().getGroup(replyGroupId).sendMessage(new MessageSection(replyGroupId, 0L).setQuote(Vars.msgID).addMessageChain(chain));
                    }
                }
            }.runTaskLater(Main.getPlugin(Main.class), Configs.SEND_DELAY.getLong());
            return;
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                if (SocketClient.isConnected) {
                    BotAPI.getIbu().getAllGroups().forEach((id, group) -> group.sendMessage(new MessageSection(id, 0L).addMessageChain(chain)));
                }
            }
        }.runTaskLater(Main.getPlugin(Main.class), Configs.SEND_DELAY.getLong());
    }

    @EventHandler
    void onPlayerLeave(@NotNull PlayerQuitEvent event) {
        ibmh.removeEnableBotPlayer(event.getPlayer());
    }
}
