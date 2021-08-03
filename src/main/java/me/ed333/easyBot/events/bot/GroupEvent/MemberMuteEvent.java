package me.ed333.easyBot.events.bot.GroupEvent;

import com.alibaba.fastjson.JSON;
import me.ed333.easyBot.events.bot.SendMsg;
import me.ed333.easyBot.utils.jsonParse.JSONOnMemberEvent;

/**
 *
 */
public class MemberMuteEvent extends SendMsg {
    public static JSONOnMemberEvent jsonParse;
    public static JSONOnMemberEvent.dData memberData;
    public static JSONOnMemberEvent.dData operatorData;
    public static JSONOnMemberEvent.dData.GroupData groupData;
    public MemberMuteEvent(String json) {
        jsonParse = JSON.parseObject(json, JSONOnMemberEvent.class);
        memberData = JSON.parseObject(jsonParse.member.toString(), JSONOnMemberEvent.dData.class);
        operatorData = JSON.parseObject(jsonParse.operator.toString(), JSONOnMemberEvent.dData.class);
        groupData = JSON.parseObject(operatorData.group.toString(), JSONOnMemberEvent.dData.GroupData.class);
    }

    public long getGroupId() { return groupData.id; }

    public String getGroupName() { return groupData.name; }

    /**禁言时间， 单位：秒*/
    public int getDurationSeconds() { return jsonParse.durationSeconds; }

/*
------------------------获取被禁言群员的有关信息
*/

    /**被禁言群员的id*/
    public long getMemberId() { return memberData.id;}

    /**被禁言群员的群名片*/
    public String getMemberName() { return memberData.memberName; }

    /**被禁言群员的群头衔*/
    public String getMemberSpecialTitle() { return memberData.SpecialTitle; }

    /**被禁言群员的权限，分别是 OWNER、ADMINISTRATOR、MEMBER*/
    public String getMemberPerm() { return memberData.permission; }

    /**被禁言群员入群时间*/
    public long getMemberJoinTime() { return memberData.joinTimestamp; }

    /**被禁言群员上一次说话的时间*/
    public long getMemberLastSpeakTime() { return memberData.lastSpeakTimestamp; }

/*
------------------------获取执行此操作的人有关信息
*/

    public long getOperatorId() { return operatorData.id;}

    public String getOperatorName() { return operatorData.memberName; }

    public String getOperatorSpecialTitle() { return operatorData.SpecialTitle; }

    public String getOperatorPerm() { return operatorData.permission; }

    public long getOperatorJoinTime() { return operatorData.joinTimestamp; }

    public long getOperatorLastSpeakTime() { return operatorData.lastSpeakTimestamp; }
}