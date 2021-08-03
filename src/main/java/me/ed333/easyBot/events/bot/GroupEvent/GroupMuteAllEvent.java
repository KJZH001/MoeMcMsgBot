package me.ed333.easyBot.events.bot.GroupEvent;

import com.alibaba.fastjson.JSON;
import me.ed333.easyBot.events.bot.SendMsg;
import me.ed333.easyBot.utils.jsonParse.JSONOnGroupMuteAll;

/**
 * 全员禁言事件
 */
public class GroupMuteAllEvent extends SendMsg {
    public static JSONOnGroupMuteAll jsonParse;
    public static JSONOnGroupMuteAll.GroupData groupData;
    public static JSONOnGroupMuteAll.OperatorData operatorData;

    public GroupMuteAllEvent(String json) {
        jsonParse = JSON.parseObject(json, JSONOnGroupMuteAll.class);
        groupData = JSON.parseObject(jsonParse.group.toString(), JSONOnGroupMuteAll.GroupData.class);
        operatorData = JSON.parseObject(jsonParse.operator.toString(), JSONOnGroupMuteAll.OperatorData.class);
    }

    public boolean getOriginStatus() { return jsonParse.origin; }

    public boolean getCurrentStatus() { return jsonParse.current; }

    public long getGroupId() { return groupData.id; }

    public String getGroupName() { return groupData.name; }

    public long getOpId() { return operatorData.id; }

    public String getOpName() { return operatorData.memberName; }

    public String getOpSpecialTitle() { return  operatorData.SpecialTitle; }

    public String getOpPerm() { return operatorData.permission; }

    public long getOpJoinTime() { return operatorData.joinTimestamp; }

    public long getOpLastSpeakTime() {return operatorData.lastSpeakTimestamp; }
}
