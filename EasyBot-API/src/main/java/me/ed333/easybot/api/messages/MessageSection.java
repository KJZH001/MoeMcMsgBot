package me.ed333.easybot.api.messages;

import com.google.gson.JsonObject;
import me.ed333.easybot.api.BotAPI;
import org.jetbrains.annotations.NotNull;

/**
 * 消息段
 */
public class MessageSection {
    private int quote = 0;
    private final JsonObject sectionJson = new JsonObject();

    /**
     * @param target 选定的群号(群号码/好友号码)
     * @param qq 临时消息选定的qq号码, 不是临时消息填 0L
     */
    public MessageSection(Long target, Long qq) {
        sectionJson.addProperty("target", target);
        sectionJson.addProperty("group", target);
        sectionJson.addProperty("qq", qq);
        sectionJson.addProperty("sessionKey", BotAPI.getIBotMiraiHttpUtils().getSession());
    }

    public JsonObject getAsJsonObject() { return sectionJson; }

    /**
     * 为消息段添加一个引用的消息。
     * @param code 消息的id
     * @return {@link MessageSection}
     */
    public MessageSection setQuote(int code) {
        if (quote == 0) {
            quote = code;
            sectionJson.addProperty("quote", code);
        } else {
            throw new Error("不可添加多个引用");
        }
        return this;
    }

    public MessageSection addMessageChain(@NotNull MessageChain chain) {
        sectionJson.add("messageChain", chain.getAsJsonArray());
        return this;
    }

    /**
     * 获取消息段中的消息链
     * @return {@link MessageChain}
     */
    public MessageChain getMessageChain() {
        return new MessageChain().parseToMessageChain(sectionJson.get("messageChain").getAsJsonArray());
    }
}
