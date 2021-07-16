package me.ed333.easyBot;

import me.ed333.easyBot.utils.MessageChain;
import net.sf.json.JSONObject;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import static me.ed333.easyBot.utils.HttpRequest.doGet;
import static me.ed333.easyBot.utils.HttpRequest.doPost;
import static me.ed333.easyBot.utils.Messages.getMsg;
import static me.ed333.easyBot.utils.Messages.printDEBUG;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.SecureRandom;
import java.util.*;

public class BOT {
    private static final ConsoleCommandSender sender = Bukkit.getConsoleSender();
    private static Client client;

    public static String session;
    public static long botID = BotMain.cfg.getLong("botID");
    public static String url = BotMain.cfg.getString("host");
    public static int verifyTime = BotMain.cfg.getInt("time");
    public static long groupID = BotMain.cfg.getLong("groupID");
    public static String authKey = BotMain.cfg.getString("authKey");
    public static boolean catch_at = BotMain.cfg.getBoolean("catch.at");
    public static boolean catch_img = BotMain.cfg.getBoolean("catch.img");
    public static boolean enableBot = BotMain.cfg.getBoolean("enable_Bot");
    public static boolean catch_text = BotMain.cfg.getBoolean("catch.text");
    public static List<Player> enableBot_Players = new ArrayList<>();

    // k:player name   v: player verify code
    public static HashMap<String, Integer> codeMap = new HashMap<>();
    // k: player name  v: QQ
    public static HashMap<String, Long> verifyPlayers = new HashMap<>();

    /**
     * 连接 Bot 的 Socket 服务器
     * @param session session
     * @throws URISyntaxException 链接不存在或连接错误
     */
    public static void connect(String session) throws URISyntaxException {
        sender.sendMessage("§3BOT: §a链接服务器中...");
        URI uri = new URI("ws://" + url + "/all?sessionKey=" + session);
        client = new Client(uri);
        client.connect();
    }

    /**
     * 初始化 Bot
     */
    public static void initializeBot() throws Exception {
        JSONObject authResult = JSONObject.fromObject(auth());
        if (authResult.getInt("code") == 0) {
            sender.sendMessage("§3BOT: §a验证身份成功!");
            printDEBUG("result: " + authResult);
            session = authResult.getString("session");
            JSONObject verifyResult = JSONObject.fromObject(verify(session));
            if (verifyResult.getInt("code") == 0) {
                sender.sendMessage("§3BOT: §a绑定成功!");
                printDEBUG("result: " + verifyResult);
                connect(session);
            } else sender.sendMessage("§3BOT: §c绑定失败！返回结果: §7" + verifyResult);
        } else sender.sendMessage("§3BOT: §c验证失败！返回结果: " + authResult);
    }

    public static int genVerifyCode() {
        StringBuilder sb = new StringBuilder();
        int code;
        SecureRandom r = new SecureRandom();
        for (int i = 0; i < 7; i++) {
            code = r.nextInt(9);
            sb.append(code);
        }
        return Integer.parseInt(sb.toString());
    }

    /**
     * 断开连接
     */
    public static void close() throws Exception {
        client.close();
        sender.sendMessage("§3BOT: §a释放session...");
        JSONObject result = JSONObject.fromObject(release_Session(session));
        if (result.getInt("code") == 0) {
            sender.sendMessage("§3BOT: §a释放完成");
            printDEBUG("result: " + result);
        } else {
            sender.sendMessage("§3BOT: §c释放失败！服务器返回结果: " + result);
        }
    }

    /**
     * 验证身份
     * @return result
     * @throws Exception 请求异常时抛出
     */
    public static String auth() throws Exception {
        sender.sendMessage("§3BOT: §a注册bot...");
        return doPost("http://" + url + "/auth", new JSONObject().element("authKey", authKey));
    }

    /**
     * 校验 Session 并将 Session 绑定到BotQQ
     * @param SessionKey session
     * @return result String
     * @throws Exception 请求异常时抛出
     */
    public static String verify(String SessionKey) throws Exception {
        sender.sendMessage("§3BOT: §a绑定BOT...");
        return doPost("http://" + url + "/verify", new JSONObject().element("sessionKey", SessionKey).element("qq", botID));
    }

    /**
     * 释放 bot 绑定的session <br/>
     * 当bot断开连接时须释放session
     * @param sessionKey sessionKey
     * @return result
     * @throws Exception 请求异常时抛出
     */
    protected static String release_Session(String sessionKey) throws Exception {
        JSONObject request = new JSONObject().element("sessionKey", sessionKey)
                .element("qq", botID);
        return doPost("http://" + url + "/release", request);
    }

//                                                            //
//  depart =========================================== depart //
//                                                            //

    /**
     * 发送好友消息
     * @param target 好友ID
     * @param quote 启用引用
     * @param code 如果引用启用， msg 的 SourceID
     * @param msgChain 要发送的消息
     * @return result
     * @throws Exception 请求异常时抛出
     */
    public static String sendFriendMessage(long target, boolean quote, int code, MessageChain msgChain) throws Exception {
        JSONObject request = new JSONObject().element("sessionKey", session)
                .element("target", target)
                .element("messageChain", msgChain.toString());
        if (quote) request.element("quote", code);
        return doPost("http://" + url + "/sendFriendMessage", request);
    }

    /**
     * 通过群给某人发送临时消息
     * @param qq 临时消息对象的QQ
     * @param groupID 临时消息的群号
     * @param quote 启用引用
     * @param code 如果启用引用, 引用的Message id
     * @param msgChain 消息链
     * @return result
     */
    public static String sendTempMessage(long qq, long groupID, boolean quote, int code, MessageChain msgChain) throws Exception {
        JSONObject request = new JSONObject();
        request.element("sessionKey", session)
                .element("qq", qq)
                .element("group", groupID)
                .element("messageChain",msgChain.toString());
        if (quote) request.element("quote", code);
        printDEBUG( qq + " | " + groupID + " | " + msgChain + " | " + session);
        printDEBUG(request.toString());
        return doPost("http://" + url + "/sendTempMessage", request);
    }

    /**
     * 发送群聊消息
     * @param groupID 群号
     * @param quote 是否引用消息
     * @param code 如果启用引用, 引用的 Message id, 不引设用为0
     * @param msgChain 消息链
     * @return result
     */
    public static String sendGroupMessage(long groupID, boolean quote, int code, MessageChain msgChain) throws Exception {
        JSONObject request = new JSONObject().element("sessionKey", session)
                .element("target", groupID)
                .element("messageChain", msgChain.toString());
        if (quote) request.element("quote", code);
        return doPost("http://" + url + "/sendGroupMessage", request);
    }

    /**
     * 获取群员的信息
     * @param memberID 群员的QQ
     * @return result
     */
    public static String getMemberInfo(long memberID) {
        return doGet("http://" + url + "/memberInfo", "sessionKey=" + session + "&target=" + groupID + "&memberId=" + memberID);
    }


    /**
     * 撤回消息
     * @param SourceID 消息的ID
     * @return result
     * @throws Exception 请求异常时抛出
     */
    public static String recall(int SourceID) throws Exception {
        JSONObject request = new JSONObject().element("sessionKey", session)
                .element("target", SourceID);
        return doPost("http://" + url + "/recall", request);
    }

    /**
     * 禁言群员
     * @param memberID 群员ID
     * @param time 时间 单位: 秒
     * @return result
     */
    public static String mute(long groupID, long memberID, int time) throws Exception {
        JSONObject request = new JSONObject().element("sessionKey", session)
                .element("target", groupID)
                .element("memberID", memberID)
                .element("time", time);
        return doPost("http://" + url + "/mute", request);
    }

    /**
     * 解除禁言
     */
    public static String unmute(long groupID, long memberID) throws Exception {
        JSONObject request = new JSONObject().element("sessionKey", session)
                .element("target", groupID)
                .element("memberID", memberID);
        return doPost("http://" + url + "/unmute", request);
    }

    /**
     * 踢出群员
     * @param reason 原因
     * @return result
     */
    public static String kick(long groupID, long memberID, String reason) throws Exception {
        JSONObject request = new JSONObject().element("sessionKey", session)
                .element("target", groupID)
                .element("memberID", memberID)
                .element("msg", reason);
        return doPost("http://" + url + "/kick", request);
    }

//                                                            //
//  depart =========================================== depart //
//                                                            //

    /*
    这两个方法没有什么区别
    只不过是通过未绑定的QQ获取时
    一个返回了 lang.yml 中的 unBound_QQ.text 内容
    一个返回了 null
     */
    public static String get_gameName_byQQ(long qq) {
        return qq_isBound(qq) ? getMsg("unBound_QQ.text", null) : BotMain.boundData.getString("QQ_Bound." + qq);
    }

    public static String get_gameName_null(long qq) {
        return qq_isBound(qq) ? null : BotMain.boundData.getString("QQ_Bound." + qq);
    }

    /*
    QQ 是否被绑定了
     */
    public static boolean qq_isBound(long qq) {
        Set<String> bound_qq = BotMain.boundData.getConfigurationSection("QQ_Bound").getKeys(false);
        return !bound_qq.contains(String.valueOf(qq));
    }

    /*
    名字是否被绑定了
     */
    public static boolean name_isBound(String name) {
        Set<String> bound_name = BotMain.boundData.getConfigurationSection("Name_Bound").getKeys(false);
        sender.sendMessage(bound_name.toString());
        return bound_name.contains(name);
    }

    static class codeAutomaticallyExpires extends BukkitRunnable {
        private final String playerName;
        public codeAutomaticallyExpires(String name) {
            this.playerName = name;
        }
        @Override
        public void run() {
            codeMap.remove(playerName);
            verifyPlayers.remove(playerName);
            printDEBUG(playerName + " 的验证码失效！");
            printDEBUG(codeMap.toString());
            printDEBUG(verifyPlayers.toString());
        }
    }
}
