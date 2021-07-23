package me.ed333.easyBot.utils;

import me.ed333.easyBot.BOT;
import me.ed333.easyBot.PlaceHolders;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import static me.ed333.easyBot.utils.Messages.getMsg;
import static me.ed333.easyBot.utils.Messages.hoverEvent_txt_replace;
public class JsonParse {
    /**
     * 获取消息发送的时间戳
     * @param source_json 位于 “messageChain” 中
     * @return time Stamp
     */
    public Long getSend_timeStamp(JSONObject source_json) {
        return source_json.getLong("time");
    }

    /**
     * 获取表情名字
     * @param face_json 位于 "messageChain" 中
     * @return face json
     */
    public String getFaceName(JSONObject face_json) {
        return face_json.getString("name");
    }

    /**
     * 获取表情ID
     * @param face_json 位于 "messageChain" 中
     * @return face id
     */
    public int getFaceId(JSONObject face_json) {
        return face_json.getInt("faceId");
    }

    /**
     * 通过接收到的 json 获取 msgChain
     * @param msg_json 接收到的JSON
     * @return msgChain
     */
    public JSONArray get_msgChainArray(JSONObject msg_json) {
        return msg_json.getJSONArray("messageChain");
    }

    /**
     * 通过接收到的 json 获取 "sender" 块 json
     * @param msg_Json 接收到的JSON
     * @return sender_json
     */
    public JSONObject getSender_Json(JSONObject msg_Json) {
        return msg_Json.getJSONObject("sender");
    }

    /**
     * 通过 sender_json 获取 "group" 块的 json
     * @param sender_json sender 块的 json
     * @return group_json
     */
    public JSONObject getSender_group_json(JSONObject sender_json) {
        return sender_json.getJSONObject("group");
    }

    /**
     * 通过接收到的 json 获取发送者的 qq 号
     * @param msg_json 接收到的JSON
     * @return sender_id
     */
    public Long getSenderId(JSONObject msg_json) {
        return getSender_Json(msg_json).getLong("id");
    }

    /**
     * 通过接收到的 json 获取发送者的群名片
     * @param msg_json 接收到的JSON
     * @return sender group name
     */
    public String getSender_groupName(JSONObject msg_json) {
        return getSender_Json(msg_json).getString("memberName");
    }

    /**
     * 获取发送者所在的群id 该方法同时还可以获取临时消息中发送者所在群id
     * @param msg_Json 接收到的JSON
     * @return groupID
     */
    public Long getGroupID(JSONObject msg_Json) {
        return getSender_group_json(getSender_Json(msg_Json)).getLong("id");
    }

    /**
     * 获得消息中图片地址
     * @param msg_single 图片类型的JSONObject
     * @return Image url
     */
    public String getImg_url(JSONObject msg_single) {
        return msg_single.getString("url");
    }

    /**
     * 获得消息中图片的 id
     * @param msg_Single 图片类型的JSONObject
     * @return Image id
     */
    public String getImg_id(JSONObject msg_Single) {
        return msg_Single.getString("imageId");
    }

    /**
     * 获取消息中被at的id
     * @param msg_at At类型的JSONObject
     * @return At target Id
     */
    public Long getAt_targetID(JSONObject msg_at) {
        return msg_at.getLong("target");
    }

    public String getSender_Msg(JSONObject msg_plain) {return msg_plain.getString("text");}

    /**
     * 获取所有的纯文本消息
     * <p>仅获取纯文本</p>
     * @param msg_json 接收到的JSON
     * @return Raw text
     */
    public String getText(JSONObject msg_json) {
        StringBuilder sb = new StringBuilder();
        JSONArray msgChain = get_msgChainArray(msg_json);
        for (Object o: msgChain) {
            JSONObject msg_Single = JSONObject.fromObject(o);
            String type = msg_Single.getString("type");

            if (type.equals("Plain")) sb.append(msg_Single.getString("text"));
        }
        return sb.toString();
    }
    /**
     * 获取复合消息
     * <p>目前可以获取</p>
     * <ul>
     *     <li>纯文本</li>
     *     <li>Image</li>
     *     <li>AT</li>
     * </ul>
     * @param msg_json 接收的json
     * @return result
     */
    public TextComponent getMulti(JSONObject msg_json) {
        TextComponent txt = new TextComponent();
        JSONArray msgChain = get_msgChainArray(msg_json);
        txt.addExtra(getMsg("text", null));
        for (Object o : msgChain) {
            JSONObject msgSingle = JSONObject.fromObject(o);
            String type = msgSingle.getString("type");

            if (type.equals("Source")) PlaceHolders.msg_Source = msgSingle;

            if (type.equals("Plain") && BOT.catch_text) {
                PlaceHolders.msg_Plain = msgSingle;
                TextComponent plain_text = new TextComponent(getSender_Msg(msgSingle).replace("\n", ""));
                plain_text.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                        new ComponentBuilder(
                                hoverEvent_txt_replace(getMsg("Plain.hoverEvent", null))
                        ).create()));
                txt.addExtra(plain_text);
            }

            if (type.equals("Image") && BOT.catch_img) {
                PlaceHolders.msg_Img = msgSingle;
                TextComponent image_txt = new TextComponent(getMsg("Image.text", null));
                image_txt.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, getImg_url(msgSingle)));
                image_txt.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                        new ComponentBuilder(
                                hoverEvent_txt_replace(getMsg("Image.hoverEvent", null))
                        ).create()));
                txt.addExtra(image_txt);
            }

            if (type.equals("At") && BOT.catch_at) {
                PlaceHolders.msg_At = msgSingle;
                TextComponent at_txt = new TextComponent(getMsg("At.text",null));
                at_txt.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                        new ComponentBuilder(
                                hoverEvent_txt_replace(getMsg("At.hoverEvent",null))
                        ).create()));
                txt.addExtra(at_txt);
            }

            if (type.equals("AtAll") && BOT.catch_atAll) {
                PlaceHolders.msg_atAll = msgSingle;
                TextComponent atAll = new TextComponent(getMsg("atAll.text", null));
                atAll.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                        new ComponentBuilder(
                                hoverEvent_txt_replace(getMsg("atAll.hoverEvent", null))
                        ).create()));
                txt.addExtra(atAll);
            }

            if (type.equals("Face") && BOT.catch_face) {
                PlaceHolders.msg_Face = msgSingle;
                TextComponent face = new TextComponent(getMsg("Face.text", null));
                face.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                        new ComponentBuilder(
                            hoverEvent_txt_replace(getMsg("Face.hoverEvent", null))
                        ).create()));
                txt.addExtra(face);
            }
        }
        return txt;
    }
}
