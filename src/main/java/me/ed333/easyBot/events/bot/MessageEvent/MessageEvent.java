package me.ed333.easyBot.events.bot.MessageEvent;

import me.ed333.easyBot.BOT;
import me.ed333.easyBot.utils.JsonParse;
import me.ed333.easyBot.utils.MessageChain;
import net.md_5.bungee.api.chat.TextComponent;
import net.sf.json.JSONObject;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import static me.ed333.easyBot.utils.Messages.DEBUG.info;
/**
 * <p>请勿直接使用本事件， 为了方便维护才写的。。</p>
 * <p>基础消息事件，被继承的类, 不会被触发</p>
 * <p>包含了部分基础方法</p>
 */
class MessageEvent extends Event {

    private final JSONObject json;
    private final JsonParse p = new JsonParse();
    private static final HandlerList handlers = new HandlerList();
    private final JsonParse jsonParse = new JsonParse();

    public MessageEvent(JSONObject json) {
        super(true);
        this.json = json;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {return handlers;}

    /**
     * 获取纯文本消息
     * @return Message
     */
    public String getMessage() {
        return jsonParse.getText(json);
    }

    /**
     * 获取复合消息
     * @return Multi text
     */
    public TextComponent getMulti() {
        return jsonParse.getMulti(json);
    }

    /**
     * 获取发送者Id
     * @return Sender id
     */
    public Long getSenderId() {
        return jsonParse.getSenderId(json);
    }

    /**
     * 获取发送者的群名片
     * @return Sender group name
     */
    public String getSender_groupName() {
        return jsonParse.getSender_groupName(json);
    }

    /**
     * 获取群号
     * @return Group id
     */
    public Long getGroupId() {
        return jsonParse.getGroupID(json);
    }

    /**
     * 获取发送者绑定的游戏 Id
     * <p>未绑定的返回 null</p>
     * @return Sender game name
     */
    public String getSender_GameName() {
        return BOT.get_gameName_null(getSenderId());
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
