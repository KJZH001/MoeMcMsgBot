package me.ed333.easybot.plugin.with_mirai_api_http.contactors;


import com.google.gson.JsonObject;
import me.ed333.easybot.api.BotAPI;
import me.ed333.easybot.api.contacts.IGroup;
import me.ed333.easybot.api.contacts.IGroupMember;
import me.ed333.easybot.api.messages.DEBUG;
import me.ed333.easybot.api.messages.MessageSection;
import me.ed333.easybot.api.utils.IBotMiraiHttpUtils;
import me.ed333.easybot.plugin.with_mirai_api_http.Configs;
import me.ed333.easybot.plugin.with_mirai_api_http.utils.HttpRequestUtils;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

public class Group implements IGroup {
    private final IBotMiraiHttpUtils utils = BotAPI.getIBotMiraiHttpUtils();
    private final long id;
    private final String name;
    private final HashMap<Long, IGroupMember> members = new HashMap<>();

    public Group(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public void sendMessage(@NotNull MessageSection section) {
        String result = HttpRequestUtils.doPost("http://" + Configs.HOST.getString() + "/sendGroupMessage", section.getAsJsonObject());
        DEBUG.debugInfo("Action: Send group msg, api-http: " + utils.getApiVer() + ", request ->" + section.getAsJsonObject() + ", result ->" + result);
    }

    @Override
    public void sendTempMessage(Long qq, @NotNull MessageSection section) {
        String result = HttpRequestUtils.doPost("http://" + Configs.HOST.getString() + "/sendTempMessage", section.getAsJsonObject());
        DEBUG.debugInfo("Action: Send temp msg, api-http: " + utils.getApiVer() + ", request ->" + section.getAsJsonObject() + ", result ->" + result);
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public long id() {
        return id;
    }


    @Override
    public void recall(int sourceID) {
        JsonObject request = new JsonObject();
        request.addProperty("sessionKey", utils.getSession());
        request.addProperty("target", sourceID);

        String result = HttpRequestUtils.doPost("http://" + Configs.HOST.getString() + "/recall", request);
        DEBUG.debugInfo("Action: recall msg, api-http: " + utils.getApiVer() + ", request ->" + request + ", result ->" + result);
    }

    @Override
    public void mute(@NotNull IGroupMember member, int time) {
        JsonObject request = new JsonObject();
        request.addProperty("sessionKey", utils.getSession());
        request.addProperty("target", id);
        request.addProperty("memberId", member.ID());
        request.addProperty("time", time);

        String result = HttpRequestUtils.doPost("http://" + Configs.HOST.getString() + "/mute", request);
        DEBUG.debugInfo("Action: mute group member, api-http: " + utils.getApiVer() + ", request ->" + request + ", result ->" + result);
    }

    @Override
    public void unmute(@NotNull IGroupMember member) {
        JsonObject request = new JsonObject();
        request.addProperty("sessionKey", utils.getSession());
        request.addProperty("target", id);
        request.addProperty("memberId", member.ID());

        String result = HttpRequestUtils.doPost("http://" + Configs.HOST.getString() + "/unmute", request);
        DEBUG.debugInfo("Action: unmute group member, api-http: " + utils.getApiVer() + ", request ->" + request + ", result ->" + result);
    }

    @Override
    public void muteAll() {
        JsonObject request = new JsonObject();
        request.addProperty("sessionKey", utils.getSession());
        request.addProperty("target", id);
        String result = HttpRequestUtils.doPost("http://" + Configs.HOST.getString() + "/muteAll", request);
        DEBUG.debugInfo("Action: unmute group member, api-http: " + utils.getApiVer() + ", request ->" + request + ", result ->" + result);
    }

    @Override
    public void kick(@NotNull IGroupMember member, String reason) {
        JsonObject request = new JsonObject();
        request.addProperty("sessionKey", utils.getSession());
        request.addProperty("target", id);
        request.addProperty("memberId", member.ID());
        request.addProperty("msg", reason);

        String result = HttpRequestUtils.doPost("http://" + Configs.HOST.getString() + "/kick", request);
        DEBUG.debugInfo("Action: kick member, api-http: " + utils.getApiVer() + ", request ->" + request + ", result ->" + result);
    }

    @Override
    public IGroupMember getGroupMember(Long qq) {
        return members.get(qq);
    }

    @Override
    public IGroupMember getGroupMember(String gameName) {
        return null;
    }

    @Override
    public IGroupMember getGroupMember(UUID uid) {
        return null;
    }

    @Override
    public HashMap<Long, IGroupMember> getGMembsByQQ(Set<Long> qqs) {
        HashMap<Long, IGroupMember> members = new HashMap<>();
        for (Long qq : qqs) {
            members.put(qq, this.members.get(qq));
        }
        return members;
    }

    @Override
    public HashMap<Long, IGroupMember> getGMembsByGameName(Set<String> gameNames) {
        return null;
    }

    @Override
    public HashMap<Long, IGroupMember> getGMembsByUUID(Set<UUID> uuids) {
        return null;
    }

    @Override
    public HashMap<Long, IGroupMember> getAll() {
        return members;
    }

    public void addMember(Long qq, GroupMember member) {
        members.put(qq, member);
    }
}
