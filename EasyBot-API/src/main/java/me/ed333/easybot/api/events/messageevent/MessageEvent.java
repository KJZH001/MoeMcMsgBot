package me.ed333.easybot.api.events.messageevent;


import com.google.gson.JsonObject;
import me.ed333.easybot.api.BotAPI;
import me.ed333.easybot.api.contacts.IGroupMember;
import me.ed333.easybot.api.events.BaseEvent;
import me.ed333.easybot.api.messages.MessageChain;

/**
 * 所有 MessageEvent 的超类，该类的超类为 BaseEvent
 */

public class MessageEvent extends BaseEvent {
    private static JsonObject json;

    public MessageEvent(JsonObject json) {
        MessageEvent.json = json;
    }

    /**
     * 获取消息链
     * @return MessageChain
     */
    public MessageChain getMessageChain() {
        return new MessageChain().parseToMessageChain(json.get("messageChain").getAsJsonArray());
    }

    /**
     * 获取消息发送者的属性
     * @return IGroupMember
     */
    public IGroupMember getSender() {
        return BotAPI.getIbu().getGroup(
                json.get("sender").getAsJsonObject().get("group").getAsJsonObject().get("id").getAsLong()
        ).getGroupMember(
                json.get("sender").getAsJsonObject().get("id").getAsLong()
        );
    }
}
