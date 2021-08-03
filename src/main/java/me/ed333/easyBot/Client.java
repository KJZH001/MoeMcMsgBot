package me.ed333.easyBot;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import me.ed333.easyBot.events.bot.BotEventHandle;
import me.ed333.easyBot.events.bot.GroupEventHandle;
import me.ed333.easyBot.events.bot.MessageEventHandle;
import me.ed333.easyBot.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;


import java.net.URI;

public class Client extends WebSocketClient {
    private final ConsoleCommandSender sender = Bukkit.getConsoleSender();
    public static boolean isConnected = false;
    public Client(URI serverUri) { super(serverUri); }

    @Override
    public void onOpen(ServerHandshake handshake) {
        isConnected = this.isOpen();
        sender.sendMessage("§3BOT: §a连接成功！ 状态: " + handshake.getHttpStatusMessage() + " | " + handshake.getHttpStatus());
    }

    @Override
    public void onMessage(String message) {
        JSONObject msg_json = JSON.parseObject(message);
        if (BOT.apiVer >= 2.0) msg_json = msg_json.getJSONObject("data");
        Messages.DEBUG.info("BOT_[DEBUG_INFO] onMessage: " + msg_json.toString());
        new BotEventHandle(msg_json.toJSONString());
        new GroupEventHandle(msg_json.toJSONString());
        new MessageEventHandle(msg_json.toJSONString());
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        isConnected = false;
        sender.sendMessage("§3BOT: §e从服务器断开连接! 可能是服务器关闭或者禁用了BOT！");
    }

    @Override
    public void onError(Exception ex) {
        sender.sendMessage("§3BOT: §c出错了！原因: " + ex.getLocalizedMessage());
        ex.printStackTrace();
    }
}
