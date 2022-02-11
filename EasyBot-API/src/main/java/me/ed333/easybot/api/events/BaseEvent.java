package me.ed333.easybot.api.events;

import me.ed333.easybot.api.BotAPI;
import me.ed333.easybot.api.EConfigKeys;
import me.ed333.easybot.api.contacts.IGroup;
import me.ed333.easybot.api.messages.MessageSection;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.HashMap;
import java.util.List;

/**
 * 所有事件的超类
 */

public class BaseEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    public BaseEvent() { super(true); }

    public HandlerList getHandlers() { return handlers; }

    public static HandlerList getHandlerList() { return handlers; }

    /**
     * 向指定启用的群发送消息
     * @param groupId 指定的群号
     * @param section 消息段
     */
    public void sendGroupMessage(long groupId, MessageSection section) {
        BotAPI.getIbu().getGroup(groupId).sendMessage(section);
    }

    /**
     * 向所有启用的群发送消息
     * @param section 消息段
     */
    public void sendGroupMessage_toALL(MessageSection section) {
        HashMap<Long, IGroup> groups = BotAPI.getIbu().getAllGroups();
        List<Long> qqs = EConfigKeys.GROUPID.getLongList();
        for (Long qq : qqs) {
            IGroup group = groups.get(qq);
            group.sendMessage(section);
        }
    }

    /**
     * 指定一个启用的群的群成员向其发送临时消息
     * @param groupID 群号码
     * @param qq 群员的qq
     * @param section 消息段
     */
    public void sendTempMessage(Long groupID, Long qq, MessageSection section) {
        IGroup group = BotAPI.getIbu().getGroup(groupID);
        group.sendTempMessage(qq, section);
    }
}
