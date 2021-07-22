package me.ed333.easyBot.events.bot.MessageEvent;

import me.ed333.easyBot.BOT;
import me.ed333.easyBot.utils.JsonParse;
import me.ed333.easyBot.utils.MessageChain;
import me.ed333.easyBot.utils.Messages;
import net.sf.json.JSONObject;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import static me.ed333.easyBot.utils.Messages.DEBUG.info;
/**
 * 好友消息撤回事件
 */
public class FriendRecallEvent extends Event {
    private final JSONObject json;
    private final JsonParse p = new JsonParse();
    private static final HandlerList handlers = new HandlerList();

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
     * 通过bot默认开启的群发送群聊消息
     * @param quote 是否引用消息
     * @param code 引用消息的id，不引用设为0
     * @param chain 发送的消息链
     * @return 返回发送结果，包含messageID，成功后返回内容: {"code": 0, "msg": "success", "messageId": 0}
     * @throws Exception 发送失败后抛出
     */
    public String sendGroupMessage(boolean quote, int code, MessageChain chain) throws Exception {
        String result = BOT.sendGroupMessage(p.getGroupID(json), quote, code, chain);
        info("event result: " + result);
        return result;
    }

    /**
     * 指定一个群发送群聊消息
     * @param groupID 群号
     * @param quote 是否引用消息
     * @param code 引用消息的id， 不引用为0
     * @param chain 消息链
     * @return 返回发送结果，包含messageID，成功后返回内容: {"code": 0, "msg": "success", "messageId": 0}
     * @throws Exception 发送失败后抛出
     */
    public String sendGroupMessage(long groupID, boolean quote, int code, MessageChain chain) throws Exception {
        String result = BOT.sendGroupMessage(groupID, quote, code, chain);
        info("event result: " + result);
        return result;
    }

    /**
     * 发送好友消息
     * @param targetID 好友的QQ号
     * @param quote 是否引用消息
     * @param code 引用消息的id，不引用为0
     * @param chain 消息链
     * @return 返回发送结果，包含messageID，成功后返回内容: {"code": 0, "msg": "success", "messageId": 0}
     * @throws Exception 发送失败后抛出
     */
    public String sendFriendMessage(long targetID, boolean quote, int code, MessageChain chain) throws Exception {
        String result = BOT.sendFriendMessage(targetID, quote, code, chain);
        info("event result: " + result);
        return result;
    }

    /**
     * 通过bot默认开启的群发送临时消息
     * @param qq 将要发送临时消息的对象
     * @param quote 是否引用消息
     * @param code 引用的消息id，不引用为0
     * @param chain 消息链
     * @return 返回发送结果，包含messageID，成功后返回内容: {"code": 0, "msg": "success", "messageId": 0}
     * @throws Exception
     */
    public String sendTempMessage(long qq, boolean quote, int code, MessageChain chain) throws Exception {
        String result = BOT.sendTempMessage(qq, BOT.groupID, quote, code, chain);
        info("event result: " + result);
        return result;
    }

    /**
     * 指定一个群发送临时消息
     * @param qq 将要发送临时消息的对象
     * @param groupID 群号
     * @param quote 是否引用消息
     * @param code 引用的消息id，不引用为0
     * @param chain 消息链
     * @return 返回发送结果，包含messageID，成功后返回内容: {"code": 0, "msg": "success", "messageId": 0}
     * @throws Exception
     */
    public String sendTempMessage(long qq, long groupID, boolean quote, int code, MessageChain chain) throws Exception {
        String result = BOT.sendTempMessage(qq, groupID, quote, code, chain);
        info("event Result: " + result);
        return result;
    }
}
