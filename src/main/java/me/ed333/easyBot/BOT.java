package me.ed333.easyBot;

import com.alibaba.fastjson.JSONObject;
import me.ed333.easyBot.utils.MessageChain;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import static me.ed333.easyBot.utils.HttpRequest.doGet;
import static me.ed333.easyBot.utils.HttpRequest.doPost;
import static me.ed333.easyBot.utils.Messages.getMsg;
import static me.ed333.easyBot.utils.Messages.DEBUG.info;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.SecureRandom;
import java.util.*;

public class BOT {
    private static final ConsoleCommandSender sender = Bukkit.getConsoleSender();
    private static Client client;

    public static Double apiVer;
    public static String session;
    public static int codeLength = BotMain.cfg.getInt("codeLength");
    public static long botID = BotMain.cfg.getLong("botID");
    public static String url = BotMain.cfg.getString("host");
    public static int verifyTime = BotMain.cfg.getInt("time");
    public static long groupID = BotMain.cfg.getLong("groupID");
    public static String Key = BotMain.cfg.getString("Key");
    public static String timeFormat = BotMain.cfg.getString("timeFormat");

    public static boolean DEBUG = BotMain.cfg.getBoolean("DEBUG");

    public static boolean enableBot = BotMain.cfg.getBoolean("enable_Bot");
    public static boolean isCatch_at = BotMain.cfg.getBoolean("catch.at");
    public static boolean isCatch_img = BotMain.cfg.getBoolean("catch.img");
    public static boolean isCatch_text = BotMain.cfg.getBoolean("catch.text");
    public static boolean isCatch_atAll = BotMain.cfg.getBoolean("catch.atAll");
    public static boolean isCatch_face = BotMain.cfg.getBoolean("catch.face");
    public static boolean isCatch_forward = BotMain.cfg.getBoolean("catch.forward");
    public static long sendDelay = BotMain.cfg.getLong("sendDelay");

    //2021-7-22 由 List 集合改为 Set 集合 防止了重复的可能
    public static Set<Player> enableBot_Players = new HashSet<>();

    // k:player name   v: player verify code
    public static HashMap<String, Integer> codeMap = new HashMap<>();
    // k: player name  v: QQ
    public static HashMap<String, Long> verifyPlayers = new HashMap<>();

    private static void apiVer() {
        JSONObject version = JSONObject.parseObject(doGet("http://" + url + "/about", null)).getJSONObject("data");
        String[] versionSpil = version.getString("version").split("\\.");
        apiVer = Double.parseDouble(versionSpil[0]);
    }

    /**
     * 连接 Bot 的 Socket 服务器
     * @param session session
     * @throws URISyntaxException 链接不存在或连接错误
     */
    public static void connect(String session) throws URISyntaxException {
        sender.sendMessage("§3BOT: §a链接服务器中...");
        URI uri_api_1 = new URI("ws://" + url + "/all?sessionKey=" + session);
        URI uri_api_2 = new URI("ws://" + url + "/all?verifyKey=" + Key + "&qq=" + botID);
        if (apiVer < 2.0) client = new Client(uri_api_1);
        else client = new Client(uri_api_2);
        info("BOT_[DEBUG_INFO] Action: Connect Socket, api-http: " + apiVer);
        client.connect();
    }

    /**
     * 初始化 Bot
     */
    public static void initializeBot() throws Exception {
        apiVer();
        JSONObject authResult = JSONObject.parseObject(auth());
        if (authResult.getInteger("code") == 0) {
            sender.sendMessage("§3BOT: §a验证身份成功!");
            session = authResult.getString("session");
            JSONObject verifyResult = JSONObject.parseObject(verify());
            if (verifyResult.getInteger("code") == 0) {
                sender.sendMessage("§3BOT: §a绑定成功!");
                connect(session);
            } else sender.sendMessage("§3BOT: §c绑定失败！返回结果: §7" + verifyResult);
        } else sender.sendMessage("§3BOT: §c验证失败！返回结果: " + authResult);
    }

    /**
     * 生成验证码
     * @return verify code
     */
    public static int genVerifyCode() {
        StringBuilder sb = new StringBuilder();
        int code;
        SecureRandom r = new SecureRandom();
        for (int i = 0; i < codeLength + 1; i++) {
            code = r.nextInt(9);
            sb.append(code);
        }
        return Integer.parseInt(sb.toString());
    }

    /**
     * 断开连接
     */
    public static void close() {
        client.close();
        sender.sendMessage("§3BOT: §a释放session...");
        JSONObject result = JSONObject.parseObject(release_Session(session));
        if (result.getInteger("code") == 0) {
            sender.sendMessage("§3BOT: §a释放完成");
            info("BOT_[DEBUG_INFO] Action: Close socket client, api-http: " + apiVer + ", result ->" + result);
        } else {
            sender.sendMessage("§3BOT: §c释放失败！服务器返回结果: " + result);
        }
    }

    /**
     * 验证身份
     * @return result
     */
    public static String auth() {
        sender.sendMessage("§3BOT: §a注册bot...");
        JSONObject request = new JSONObject();
        String result;
        String url_v1 = "http://" + url + "/auth";
        String url_v2 = "http://" + url + "/verify";
        if (apiVer < 2.0 ) {
            request.put("authKey", Key);
            result = doPost(url_v1, request);
        } else {
            request.put("verifyKey", Key);
            result = doPost(url_v2, request);
        }
        info("BOT_[DEBUG_INFO] Action: AuthBot, api-http: " + apiVer +", request ->" + request + ", result ->" + result);
        return result;
    }

    /**
     * 校验 Session 并将 Session 绑定到BotQQ
     * @return result String
     */
    public static String verify() {
        sender.sendMessage("§3BOT: §a绑定BOT...");
        String result;
        JSONObject request = new JSONObject();
        request.put("sessionKey", session);
        request.put("qq", botID);
        if (apiVer < 2.0) result = doPost("http://" + url + "/verify", request);
        else result = doPost("http://" + url + "/bind", request);
        info("BOT_[DEBUG_INFO] Action: Verify Session, api-http: " + apiVer + ", request ->" + request + ", result ->" + result);
        return result;
    }

    /**
     * 释放 bot 绑定的session <br/>
     * 当bot断开连接时须释放session
     * @param sessionKey sessionKey
     * @return result
     */
    protected static String release_Session(String sessionKey) {
        JSONObject request = new JSONObject();
        String result;
        request.put("sessionKey", sessionKey);
        request.put("qq", botID);
        result = doPost("http://" + url + "/release", request);
        info("BOT_[DEBUG_INFO] Action: Release session, api-http: " + apiVer + ", request ->" + request + ", result ->" + result);
        return result;
    }

//                                                            //
//  depart =========================================== depart //
//                                                            //

//    /**
//     * 发送好友消息
//     * @param target 好友ID
//     * @param quote 启用引用
//     * @param code 如果引用启用， msg 的 SourceID
//     * @param msgChain 要发送的消息
//     * @return result
//     */
//    public static String sendFriendMessage(long target, boolean quote, int code, MessageChain msgChain) {
//        JSONObject request = new JSONObject();
//        String result;
//        request.put("sessionKey", session);
//        request.put("target", target);
//        request.put("messageChain", msgChain.toArray());
//        if (quote) request.put("quote", code);
//        result = doPost("http://" + url + "/sendFriendMessage", request);
//        info("BOT_[DEBUG_INFO] Action: Send friend msg, api-http: " + apiVer + ", request ->" + request + ", result ->" + result);
//        return result;
//    }

    /**
     * 通过群给某人发送临时消息
     * @param qq 临时消息对象的QQ
     * @param groupID 临时消息的群号
     * @param quote 启用引用
     * @param code 如果启用引用, 引用的Message id
     * @param msgChain 消息链
     * @return result
     */
    public static String sendTempMessage(long qq, long groupID, boolean quote, int code, MessageChain msgChain) {
        JSONObject request = new JSONObject();
        String result;
        request.put("sessionKey", session);
        request.put("qq", qq);
        request.put("group", groupID);
        request.put("messageChain",msgChain.toArray());
        if (quote) request.put("quote", code);
        result = doPost("http://" + url + "/sendTempMessage", request);
        info("BOT_[DEBUG_INFO] Action: Send temp msg, api-http: " + apiVer + ", request ->" + request + ", result ->" + result);
        return result;
    }

    /**
     * 发送群聊消息
     * @param groupID 群号
     * @param quote 是否引用消息
     * @param code 如果启用引用, 引用的 Message id, 不引设用为0
     * @param msgChain 消息链
     * @return result
     */
    public static String sendGroupMessage(long groupID, boolean quote, int code, MessageChain msgChain) {
        JSONObject request = new JSONObject();
        String result;
        request.put("target", groupID);
        request.put("sessionKey", session);
        request.put("messageChain", msgChain.toArray());
        if (quote) request.put("quote", code);
        result = doPost("http://" + url + "/sendGroupMessage", request);
        info("BOT_[DEBUG_INFO] Action: Send group msg, api-http: " + apiVer + ", request ->" + request + ", result ->" + result);
        return result;
    }

    /**
     * 获取群员的信息
     * @param memberID 群员的QQ
     * @return member info
     */
    public static String getMemberInfo(long memberID) {
        String result = doGet("http://" + url + "/memberInfo", "sessionKey=" + session + "&target=" + groupID + "&memberId=" + memberID);
        info("BOT_[DEBUG_INFO] Action: Get member info, api-http: " + apiVer + ", result ->" + result);
        return result;
    }


    /**
     * 撤回消息
     * @param SourceID 消息的ID
     * @return result
     */
    public static String recall(int SourceID) {
        JSONObject request = new JSONObject();
        String result;
        request.put("sessionKey", session);
        request.put("target", SourceID);
        result = doPost("http://" + url + "/recall", request);
        info("BOT_[DEBUG_INFO] Action: Recall, api-http: " + apiVer + ", request ->" + request + ", result ->" + result);
        return result;
    }

    /**
     * 禁言群员
     * @param memberID 群员ID
     * @param time 时间 单位: 秒
     * @return result
     */
    public static String mute(long groupID, long memberID, int time) {
        JSONObject request = new JSONObject();
        String result;
        request.put("sessionKey", session);
        request.put("target", groupID);
        request.put("memberID", memberID);
        request.put("time", time);
        result = doPost("http://" + url + "/mute", request);
        info("BOT_[DEBUG_INFO] Action: Mute, api-http: " + apiVer + ", request ->" + request + ", result ->" + result);
        return result;
    }

    /**
     * 解除禁言
     */
    public static String unmute(long groupID, long memberID) {
        JSONObject request = new JSONObject();
        String result;
        request.put("sessionKey", session);
        request.put("target", groupID);
        request.put("memberID", memberID);
        result = doPost("http://" + url + "/unmute", request);
        info("BOT_[DEBUG_INFO] Action: Unmute, api-http: " + apiVer + ", request ->" + request + ", result ->" + result);
        return result;
    }

    /**
     * 踢出群员
     * @param reason 原因
     * @return result
     */
    public static String kick(long groupID, long memberID, String reason) {
        JSONObject request = new JSONObject();
        String result;
        request.put("sessionKey", session);
        request.put("target", groupID);
        request.put("memberID", memberID);
        request.put("msg", reason);
        result = doPost("http://" + url + "/kick", request);
        info("BOT_[DEBUG_INFO] Action: Kick, api-http: " + apiVer + ", request ->" + request + ", result ->" + result);
        return result;
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
        return qq_isBound(qq) ? BotMain.boundData.getString("QQ_Bound." + qq) : getMsg("unBound_QQ.text", null);
    }

    /*
    QQ 是否被绑定了
     */
    public static boolean qq_isBound(long qq) {
        Set<String> bound_qq = BotMain.boundData.getConfigurationSection("QQ_Bound").getKeys(false);
        return bound_qq.contains(String.valueOf(qq));
    }

    /*
    名字是否被绑定了
     */
    public static boolean name_isBound(String name) {
        Set<String> bound_name = BotMain.boundData.getConfigurationSection("Name_Bound").getKeys(false);
        sender.sendMessage(bound_name.toString());
        return bound_name.contains(name);
    }

    /**
     * 验证码自销
     */
    public static class codeAutomaticallyExpires extends BukkitRunnable {
        private final String playerName;
        public codeAutomaticallyExpires(String name) {
            this.playerName = name;
        }

        @Override
        public void run() {
            codeMap.remove(playerName);
            verifyPlayers.remove(playerName);
        }
    }
}
