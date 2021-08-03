package me.ed333.easyBot.events.bot.BotEvent;

/**
 * Bot被服务器断开或因网络问题而掉线
 */
public class BotOfflineEventDropped extends BotEvent{
    public BotOfflineEventDropped(String json) {
        super(json);
    }
}
