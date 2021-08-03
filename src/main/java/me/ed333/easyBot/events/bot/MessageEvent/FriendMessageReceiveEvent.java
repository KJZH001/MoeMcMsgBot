package me.ed333.easyBot.events.bot.MessageEvent;

import com.alibaba.fastjson.JSON;
import me.ed333.easyBot.events.bot.SendMsg;
import me.ed333.easyBot.utils.jsonParse.JSONOnFriendMessage;

public class FriendMessageReceiveEvent extends SendMsg {

    public static JSONOnFriendMessage jsonParse;
    public static JSONOnFriendMessage.senderData senderData;

    public FriendMessageReceiveEvent(String json) {
        jsonParse = JSON.parseObject(json, JSONOnFriendMessage.class);
    }

    // 获取好友QQ号码
    public long getId() { return senderData.id; }

    // 获取好友昵称
    public String getNickname() { return senderData.nickname;}

    // 获取好友的备注
    public String getRemark() { return senderData.remark; }
}
