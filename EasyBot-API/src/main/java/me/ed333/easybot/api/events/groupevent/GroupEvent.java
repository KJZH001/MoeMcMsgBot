package me.ed333.easybot.api.events.groupevent;


import com.google.gson.JsonObject;
import me.ed333.easybot.api.BotAPI;
import me.ed333.easybot.api.contacts.IGroup;
import me.ed333.easybot.api.contacts.IGroupMember;
import me.ed333.easybot.api.events.BaseEvent;
import org.jetbrains.annotations.NotNull;

/**
 * 所有 GroupEvent 的超类，该类的超类为 BaseEvent
 */
public class GroupEvent extends BaseEvent {
    private static JsonObject json;

    public GroupEvent(@NotNull JsonObject json) {
        GroupEvent.json = json.get("group").getAsJsonObject();
    }

    /**
     * 获取群的属性
     * @return IGroup
     */
    public IGroup getGroup() {
        return BotAPI.getIbu().getGroup(json.get("id").getAsLong());
    }


    /**
     * 获取执行此操作的 管理员/群主 的属性
     * @return IGroupMember
     */
    public IGroupMember getOperator() {
        return BotAPI.getIbu().getGroup(
                json.get("group").getAsJsonObject().get("id").getAsLong()
        ).getGroupMember(
                json.get("operator").getAsJsonObject().get("id").getAsLong()
        );
    }
}
