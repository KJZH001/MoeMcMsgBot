package me.ed333.easyBot.events.bot.MessageEvent;

import com.alibaba.fastjson.JSON;
import me.ed333.easyBot.events.bot.SendMsg;
import me.ed333.easyBot.utils.jsonParse.JSONOnFriendRecall;
import org.bukkit.event.HandlerList;

/**
 * 好友消息撤回事件
 */
public class FriendRecallEvent extends SendMsg {
    public static JSONOnFriendRecall jsonParse;
    private static final HandlerList handlers = new HandlerList();

    public FriendRecallEvent(String json) {
        jsonParse = JSON.parseObject(json, JSONOnFriendRecall.class);
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    /**
     * 原消息发送者QQ
     * @return QQ number
     */
    public Long getAuthorId() {
        return jsonParse.authorId;
    }

    /**
     * 原消息Id
     * @return Message Id
     */
    public Integer getMsgId() {
        return jsonParse.messageId;
    }
}
