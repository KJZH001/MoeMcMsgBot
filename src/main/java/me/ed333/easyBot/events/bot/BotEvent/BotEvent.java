package me.ed333.easyBot.events.bot.BotEvent;

import com.alibaba.fastjson.JSON;
import me.ed333.easyBot.events.bot.SendMsg;
import me.ed333.easyBot.utils.jsonParse.JSONOnBotEvent;

/**
 * 该类被继承，请勿直接使用
 */
class BotEvent extends SendMsg {
    public static JSONOnBotEvent jsonParse;
    public BotEvent(String json) {
        jsonParse = JSON.parseObject(json, JSONOnBotEvent.class);
    }

    public long getBotId() { return jsonParse.qq; }
}
