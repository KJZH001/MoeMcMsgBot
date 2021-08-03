package me.ed333.easyBot.events.bot.MessageEvent;

/**
 * 临时消息事件 <br/>
 * 解析方式与 {@link GroupMessageReceiveEvent} 相同
 */
public class TempMessageReceiveEvent extends GroupMessageReceiveEvent {
    public TempMessageReceiveEvent(String json) {
        super(json);
    }
}
