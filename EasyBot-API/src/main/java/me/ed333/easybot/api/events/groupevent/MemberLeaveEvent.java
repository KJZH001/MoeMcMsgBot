package me.ed333.easybot.api.events.groupevent;


import com.google.gson.JsonObject;
import me.ed333.easybot.api.BotAPI;
import me.ed333.easybot.api.contacts.IGroupMember;
import me.ed333.easybot.api.events.BaseEvent;

public class MemberLeaveEvent extends BaseEvent {
    private static JsonObject json;

    public MemberLeaveEvent(JsonObject json) {
        MemberLeaveEvent.json = json.get("data").getAsJsonObject();
    }

    /**
     * 获取该群员的属性 <br/>
     * @return IGroupMember
     */
    public IGroupMember getMember() {
        return BotAPI.getIbu().getGroup(
                json.get("member").getAsJsonObject().get("group").getAsJsonObject().get("id").getAsLong()
        ).getGroupMember(
                json.get("member").getAsJsonObject().get("id").getAsLong()
        );
    }

}
