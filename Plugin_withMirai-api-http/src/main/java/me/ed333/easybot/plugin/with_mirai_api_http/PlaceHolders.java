package me.ed333.easybot.plugin.with_mirai_api_http;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.ed333.easybot.api.BotAPI;
import me.ed333.easybot.api.EConfigKeys;
import me.ed333.easybot.api.Vars;
import me.ed333.easybot.api.utils.IBotMiraiHttpUtils;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PlaceHolders extends PlaceholderExpansion {
    private final IBotMiraiHttpUtils ibmh = BotAPI.getIBotMiraiHttpUtils();

    @Override
    public String onPlaceholderRequest(Player player, @NotNull String params) {
        switch (params) {
            case "senderQQ":
                return String.valueOf(Vars.senderId);
            case "senderGroupID":
                return String.valueOf(Vars.groupID);
            case "senderGroupName":
                return BotAPI.getIbu().getGroup(Vars.groupID).name();
            case "senderNick":
                return Vars.senderNick;
            case "senderGameName":
                return ibmh.getGameName(Vars.senderId);
            case "imageID":
                return Vars.imgId;
            case "imageURL":
                return Vars.imgUrl;
            case "atTargetID":
                return String.valueOf(Vars.atTarget);
            case "atTargetNick":
                return ibmh.getSenderGroup().getGroupMember(Vars.atTarget).MemberName();
            case "atTargetGameName":
                return ibmh.getGameName(Vars.atTarget);
            case "sendTime":
                return String.valueOf(Vars.time);
            case "sendTimeF":
                return stampToDate(Vars.time);
            case "faceName":
                return Vars.faceName;
            case "faceID":
                return String.valueOf(Vars.faceId);
            case "botID":
                return EConfigKeys.BOTID.getString();
            case "msgID":
                return String.valueOf(Vars.msgID);
        }
        if (params.startsWith("group_")) {
            String[] spilt = params.split("group_");
            if (spilt.length > 1 && spilt[1].matches("^[0-9]\\d*$")) {
                return String.valueOf(EConfigKeys.GROUPID.getLongList().get(Integer.parseInt(spilt[1])));
            }
        }
        return null;
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "txt";
    }

    @Override
    public @NotNull String getAuthor() {
        return "ed333";
    }

    @Override
    public @NotNull String getVersion() {
        return "Plugin with mirai http";
    }

    private static String stampToDate(Long stamp){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Configs.TIME_FORMAT.getString());
        long lt = new Long(stamp + "000");
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }
}
