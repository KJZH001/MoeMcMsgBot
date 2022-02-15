package me.ed333.easybot.plugin.with_mirai_api_http.contactors;


import com.google.gson.JsonObject;
import me.ed333.easybot.api.contacts.IGroupMember;

public class GroupMember implements IGroupMember {
    private final long id;
    private final String memberName;
    private final String specialTitle;
    private final String permission;
    private final long joinTimeStamp;
    private final long lastSpeakTimestamp;
    private final long muteTimeRemaining;

    public GroupMember(JsonObject json) {
        this.id = json.get("id").getAsLong();
        this.memberName = json.get("memberName").getAsString();
        this.specialTitle = json.get("specialTitle").getAsString();
        this.permission = json.get("permission").getAsString();
        this.joinTimeStamp = json.get("joinTimestamp").getAsLong();
        this.lastSpeakTimestamp = json.get("lastSpeakTimestamp").getAsLong();
        this.muteTimeRemaining = json.get("muteTimeRemaining").getAsLong();
    }

    @Override
    public long ID() {
        return id;
    }

    @Override
    public String MemberName() {
        return memberName;
    }

    @Override
    public String SpecialTitle() {
        return specialTitle;
    }

    @Override
    public String Permission() {
        return permission;
    }

    @Override
    public long JoinTimestamp() {
        return joinTimeStamp;
    }

    @Override
    public long LastSpeakTimestamp() {
        return lastSpeakTimestamp;
    }

    @Override
    public long muteTimeRemaining() {
        return muteTimeRemaining;
    }
}
