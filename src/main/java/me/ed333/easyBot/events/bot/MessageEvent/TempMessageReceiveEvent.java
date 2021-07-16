package me.ed333.easyBot.events.bot.MessageEvent;

import me.ed333.easyBot.BOT;
import me.ed333.easyBot.utils.MessageChain;
import net.sf.json.JSONObject;

/**
 * 临时消息事件
 */
public class TempMessageReceiveEvent extends MessageEvent{
    public TempMessageReceiveEvent(JSONObject json) {
        super(json);
    }

    /**
     * 发送临时消息
     * @param quote 启用对消息的引用
     * @param code 引用消息的id， 不引用为 0
     * @param chain 消息链
     * @return send result
     * @throws Exception 发送异常时抛出
     */
    public String sendMessage(boolean quote, int code, MessageChain chain) throws Exception {
        return (BOT.sendTempMessage(getSenderId(), getGroupId(), quote, code, chain));
    }
}
