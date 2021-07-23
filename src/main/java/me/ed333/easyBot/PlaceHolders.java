package me.ed333.easyBot;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.ed333.easyBot.utils.JsonParse;
import me.ed333.easyBot.utils.Messages;
import net.sf.json.JSONObject;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.Date;


public class PlaceHolders extends PlaceholderExpansion {
    public static JSONObject recvMsg_json;
    public static JSONObject msg_Plain;
    public static JSONObject msg_At;
    public static JSONObject msg_Img;
    public static JSONObject msg_Face;
    public static JSONObject msg_atAll;
    public static JSONObject msg_Source;
    private final JsonParse parse = new JsonParse();

    @Override
    public String onPlaceholderRequest(Player player, String params) {
        switch (params) {
            case "sender_qq":
                return parse.getSenderId(recvMsg_json).toString();
            case "sender_name":
                return parse.getSender_groupName(recvMsg_json);
            case "sender_gameName":
                return BOT.get_gameName_byQQ(parse.getSenderId(recvMsg_json));
            case "image_id":
                return parse.getImg_id(msg_Img);
            case "image_url":
                return parse.getImg_url(msg_Img);
            case "at_targetID":
                return parse.getAt_targetID(msg_At).toString();
            case "at_targetName":
                if (BOT.apiVer < 2.0) return JSONObject.fromObject(BOT.getMemberInfo(parse.getAt_targetID(msg_At))).getString("name");
                else return JSONObject.fromObject(BOT.getMemberInfo(parse.getAt_targetID(msg_At))).getString("memberName");
            case "at_target_gameName":
                return BOT.get_gameName_byQQ(parse.getAt_targetID(msg_At));
            case "sendTime":
                return String.valueOf(parse.getSend_timeStamp(recvMsg_json.getJSONArray("messageChain").getJSONObject(0)));
            case "sendTime_formatted":
                return stampToDate(String.valueOf(parse.getSend_timeStamp(recvMsg_json.getJSONArray("messageChain").getJSONObject(0))));
            case "faceName":
                return parse.getFaceName(msg_Face);
            case "faceID":
                return String.valueOf(parse.getFaceId(msg_Face));
            case "group":
                return String.valueOf(BOT.groupID);
            case "botID":
                return String.valueOf(BOT.botID);
        }
        return null;
    }

    @Override
    public boolean canRegister() { return true; }

    @Override
    public String getIdentifier() {
        return "txt";
    }

    @Override
    public String getAuthor() { return "ed3"; }

    @Override
    public String getVersion() {
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
