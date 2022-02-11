package me.ed333.easybot.api.events.groupevent;


import com.google.gson.JsonObject;
import me.ed333.easybot.api.BotAPI;
import me.ed333.easybot.api.contacts.IGroupMember;

public class MemberLeaveEventKick extends GroupEvent {
    private static JsonObject json;

    public MemberLeaveEventKick(JsonObject json) {
        super(json.get("data").getAsJsonObject());
        MemberLeaveEventKick.json = json.get("data").getAsJsonObject();
    }

    /**
     * 获取被执行此操作的群员的属性 <br/>
     * @return IGroupMember
     */
    public IGroupMember getMember() {
        return BotAPI.getIbu().getGroup(
                json.get("group").getAsJsonObject().get("id").getAsLong()
        ).getGroupMember(
                json.get("member").getAsJsonObject().get("id").getAsLong()
        );
    }
}
