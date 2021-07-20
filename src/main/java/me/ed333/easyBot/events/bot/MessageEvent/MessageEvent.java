package me.ed333.easyBot.events.bot.MessageEvent;

import me.ed333.easyBot.BOT;
import me.ed333.easyBot.utils.JsonParse;
import me.ed333.easyBot.utils.MessageChain;
import me.ed333.easyBot.utils.Messages;
import net.md_5.bungee.api.chat.TextComponent;
import net.sf.json.JSONObject;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

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
    public @NotNull HandlerList getHandlers() {
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
     * 发送群聊消息
     * @param quote 是否引用消息
     * @param code 引用消息的id，不引用设为0
     * @param chain 发送的消息链
     * @return 返回消息的id
     * @throws Exception 发送失败后抛出
     */
    public String sendGroupMessage(boolean quote, int code, MessageChain chain) throws Exception {
        String result = BOT.sendGroupMessage(p.getGroupID(json), quote, code, chain);
        Messages.printDEBUG(result);
        return result;
    }
}
