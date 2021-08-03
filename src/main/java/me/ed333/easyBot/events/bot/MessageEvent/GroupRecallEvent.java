package me.ed333.easyBot.events.bot.MessageEvent;

import com.alibaba.fastjson.JSON;
import me.ed333.easyBot.utils.jsonParse.JSONOnGroupRecall;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * 群消息撤回事件
 */
public class GroupRecallEvent extends Event {
    public static JSONOnGroupRecall jsonParse;
    public static JSONOnGroupRecall.groupData groupData;
    public static JSONOnGroupRecall.operatorData operatorData;
    private static final HandlerList handlers = new HandlerList();

    public GroupRecallEvent(String json) {
        jsonParse = JSON.parseObject(json, JSONOnGroupRecall.class);
        groupData = JSON.parseObject(jsonParse.group.toString(), JSONOnGroupRecall.groupData.class);
        operatorData = JSON.parseObject(jsonParse.operator.toString(), JSONOnGroupRecall.operatorData.class);

    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public Long getAuthor_Id() { return jsonParse.authorId; }

    public int getMsgId() { return jsonParse.messageId; }

    public long getGroupId() { return groupData.id; }

    public String getGroupName() { return groupData.name; }

    public long getOperatorId() { return operatorData.id; }

    public String getMemberName() { return operatorData.memberName; }

    public String getSpecialTitle() { return operatorData.specialTitle; }

    public long getJoinTimestamp() { return operatorData.joinTimestamp; }

    public long getLastSpeakTimestamp() { return operatorData.lastSpeakTimestamp; }
}

