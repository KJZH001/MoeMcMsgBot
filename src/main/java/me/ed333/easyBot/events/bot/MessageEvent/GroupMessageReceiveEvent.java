package me.ed333.easyBot.events.bot.MessageEvent;

import com.alibaba.fastjson.JSON;
import me.ed333.easyBot.events.bot.SendMsg;
import me.ed333.easyBot.utils.Messages;
import me.ed333.easyBot.utils.jsonParse.JSONOnGroupMessage;
import me.ed333.easyBot.utils.jsonParse.MsgGet;
import net.md_5.bungee.api.chat.TextComponent;

/**
 * 收到了群消息事件
 */
public class GroupMessageReceiveEvent extends SendMsg {
    public static JSONOnGroupMessage jsonParse;
    public static JSONOnGroupMessage.senderData senderData;
    public static JSONOnGroupMessage.senderData.senderGroupData senderGroupData;
    public GroupMessageReceiveEvent(String json) {
        jsonParse = JSON.parseObject(json, JSONOnGroupMessage.class);
        senderData = JSON.parseObject(jsonParse.sender.toString(), JSONOnGroupMessage.senderData.class);
        senderGroupData = JSON.parseObject(senderData.group.toString(), JSONOnGroupMessage.senderData.senderGroupData.class);
    }

    // 获取发送者的 QQ
    public long getSenderId() { return senderData.id; }

    // 获取发送者的群名片
    public String getMemberName() { return senderData.memberName; }

    // 获取发送者的群头衔
    public String getSpecialTitle() { return senderData.specialTitle; }

    // 获取发送者的群权限，分别是 OWNER、ADMINISTRATOR、MEMBER
    public String getPermission() { return senderData.permission; }

    // 获取发送者入群时间
    public long getJoinTimestamp() { return senderData.joinTimestamp; }

    // 获取发送者最近一次说话的时间
    public long getLastSpeakTimestamp() { return senderData.lastSpeakTimestamp; }

    // 获取发送者所在的群
    public long getGroupId() { return senderGroupData.id; }

    // 获取发送者所在的群名称
    public String getGroupName() { return senderGroupData.name; }

    /**
     * 获取纯文本消息内容，不包含图片、表情等消息
     * @return 纯文本消息
     */

    public String getRawText() { return MsgGet.getRawMsg(MsgGet.msgType.Group, jsonParse.messageChain); }

    public TextComponent getMulti() {
        TextComponent txt = new TextComponent();
        txt.addExtra(Messages.getMsg("text", null));
        txt.addExtra(MsgGet.getMultiMsg(MsgGet.msgType.Group, jsonParse.messageChain, true, false));
        return txt;
    }
}
