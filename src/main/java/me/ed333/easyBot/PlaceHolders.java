package me.ed333.easyBot;

import com.alibaba.fastjson.JSON;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.ed333.easyBot.events.bot.MessageEvent.GroupMessageReceiveEvent;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;


public class PlaceHolders extends PlaceholderExpansion {
    public static long at_target;
    public static String imgId;
    public static String imgUrl;

    public static String faceName;
    public static int faceId;

    public static int messageId;
    public static long messageTime;

    public static String forward_name;

    @Override
    public String onPlaceholderRequest(Player player, String params) {
        switch (params) {
            case "sender_qq":
                return String.valueOf(GroupMessageReceiveEvent.senderData.id);
            case "sender_name":
                return GroupMessageReceiveEvent.senderData.memberName;
            case "sender_gameName":
                return BOT.get_gameName_byQQ(GroupMessageReceiveEvent.senderData.id);
            case "image_id":
                return imgId;
            case "image_url":
                return imgUrl;
            case "at_targetID":
                return String.valueOf(at_target);
            case "at_targetName":
                if (BOT.apiVer < 2.0) return JSON.parseObject(BOT.getMemberInfo(at_target)).getString("name");
                else return JSON.parseObject(BOT.getMemberInfo(at_target)).getString("memberName");
            case "at_target_gameName":
                return BOT.get_gameName_byQQ(at_target);
            case "sendTime":
                return String.valueOf(messageTime);
            case "sendTime_formatted":
                return stampToDate(String.valueOf(messageTime));
            case "faceName":
                return faceName;
            case "faceID":
                return String.valueOf(faceId);
            case "group":
                return String.valueOf(BOT.groupID);
            case "botID":
                return String.valueOf(BOT.botID);
            case "msgId":
                return String.valueOf(messageId);
            case "forward_name":
                return forward_name;
        }
        return null;
    }

    @Override
    public boolean canRegister() { return true; }

    @Override
    public @NotNull String getIdentifier() {
        return "txt";
    }

    @Override
    public @NotNull String getAuthor() { return "ed3"; }

    @Override
    public @NotNull String getVersion() {
        return "000";
    }

    private static String stampToDate(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(BOT.timeFormat);
        long lt = new Long(s + "000");
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }
}
