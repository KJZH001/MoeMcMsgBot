package me.ed333.easybot.plugin.with_mirai_api_http;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.ed333.easybot.api.messages.DEBUG;
import me.ed333.easybot.plugin.with_mirai_api_http.event.Handler;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.jetbrains.annotations.NotNull;

import java.net.URI;

public class SocketClient extends WebSocketClient {
    private final ConsoleCommandSender sender = Bukkit.getConsoleSender();
    public static boolean isConnected = false;
    public SocketClient(URI serverUri) { super(serverUri); }

    @Override
    public void onOpen(@NotNull ServerHandshake handshake) {
        sender.sendMessage("§3BOT: §a连接成功！ handshake data: " + handshake.getHttpStatusMessage() + " | " + handshake.getHttpStatus());
    }

    @Override
    public void onMessage(String message) {
        DEBUG.debugInfo(message);

        JsonObject json = new JsonParser().parse(message).getAsJsonObject();

        if (isConnected) {
            new Handler(json.get("data").getAsJsonObject());
        }

        isConnected = this.isOpen();
    }

    @Override
    public void onClose(int i, String reason, boolean b) {
        isConnected = false;
        sender.sendMessage("§3BOT: §e从 SocketServer 断开连接! 原因: " + reason);
    }

    @Override
    public void onError(@NotNull Exception ex) {
        sender.sendMessage("§3BOT: §c出错了！原因: " + ex.getLocalizedMessage());
        ex.printStackTrace();
    }
}
