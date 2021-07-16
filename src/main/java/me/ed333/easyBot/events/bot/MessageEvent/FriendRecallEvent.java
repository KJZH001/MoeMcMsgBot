package me.ed333.easyBot.events.bot.MessageEvent;

import me.ed333.easyBot.BOT;
import me.ed333.easyBot.utils.JsonParse;
import me.ed333.easyBot.utils.MessageChain;
import me.ed333.easyBot.utils.Messages;
import net.sf.json.JSONObject;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * 好友消息撤回事件
 */
public class FriendRecallEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private final JSONObject json;

    public FriendRecallEvent(JSONObject json) {
        super(true);
        this.json = json;
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
    public Long getAuthor_Id() {
        return json.getLong("authorId");
    }

    /**
     * 原消息Id
     * @return Message Id
     */
    public Integer getMsgId() {
        return json.getInt("messageId");
    }

    /**
     * 原消息发送时间
     * @return Message time
     */
    public Integer getMsgTime() {
        return json.getInt("time");
    }

    /**
     * 发送群聊消息
     * @param quote 是否引用消息
     * @param code 引用消息的id，不引用设为0
     * @param chain 发送的消息链
     * @return 返回消息的id
     * @throws Exception 发送失败后抛出
     */
    public String sendGroupMessage(boolean quote, int code, MessageChain chain) throws Exception {
        String result = BOT.sendGroupMessage(new JsonParse().getGroupID(json), quote, code, chain);
        Messages.printDEBUG(result);
        return result;
    }
}
