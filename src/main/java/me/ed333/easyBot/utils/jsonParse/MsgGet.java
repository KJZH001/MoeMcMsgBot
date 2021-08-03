package me.ed333.easyBot.utils.jsonParse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import me.ed333.easyBot.BOT;
import me.ed333.easyBot.BotMain;
import me.ed333.easyBot.PlaceHolders;
import me.ed333.easyBot.utils.BookCreator;
import me.ed333.easyBot.utils.Messages;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

import static me.ed333.easyBot.utils.Messages.getMsg;
import static me.ed333.easyBot.utils.Messages.hoverEvent_txt_replace;

import java.util.List;

public class MsgGet {

    public enum msgType { Temp, Group, /*Friend*/ }

    /**
     * 获取纯文本消息内容，不包含图片、表情等消息
     * @param type 消息类型 <br/>
     *                分别为 Group | Friend | Temp <br/>
     *                群聊类型 | 好友类型 | 临时类型 <br/>
     * @return 纯文本消息
     */
    public static String getRawMsg(msgType type, List<String> msgChain) {
        StringBuilder sb = new StringBuilder();
        if (type.equals(msgType.Group) || type.equals(msgType.Temp)) {
            for (String str : msgChain) {
                JSONOnGroupMessage.msgChainData msgChainData = JSON.parseObject(str, JSONOnGroupMessage.msgChainData.class);
                if (msgChainData.type.equals("Plain")) sb.append(msgChainData.text);
            }
            return sb.toString();
        }
        return null;
    }

    /**
     * 获取复合消息内容，含图片、表情等消息
     * @param type 消息类型 <br/>
     *                分别为 Group | Friend | Temp <br/>
     *                群聊类型 | 好友类型 | 临时类型 <br/>
     * @param replace 替换掉换行符
     * @param isForward 是否是转发消息类型，转发消息类型对于消息链内以下类型中的PAPI变量不会去解析: At, 因为解析不到<br/>
     *                  避免过多的日志输出
     * @return 复合消息内容
     */
    public static TextComponent getMultiMsg(msgType type, List<String> msgChain, boolean replace, boolean isForward) {
        TextComponent text = new TextComponent();

        if (type.equals(msgType.Group) || type.equals(msgType.Temp)) {
            for (String str : msgChain) {
                JSONOnGroupMessage.msgChainData msgChainData = JSON.parseObject(str, JSONOnGroupMessage.msgChainData.class);

                if (msgChainData.type.equals("Source") && BotMain.hasPAPI) {
                    JSONObject json = JSON.parseObject(str);
                    PlaceHolders.messageId = json.getInteger("id");
                    PlaceHolders.messageTime = json.getLong("time");

                } else if (msgChainData.type.equals("Plain") && BOT.isCatch_text) {
                    TextComponent plainText = new TextComponent(replace ? msgChainData.text.replace("\n", " ") : msgChainData.text);
                    plainText.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                            new ComponentBuilder(
                                    hoverEvent_txt_replace(getMsg("Plain.hoverEvent", null))
                            ).create()));
                    text.addExtra(plainText);

                } else if (msgChainData.type.equals("Image") && BOT.isCatch_img) {
                    if (BotMain.hasPAPI) {
                        JSONObject json = JSON.parseObject(str);
                        PlaceHolders.imgUrl = json.getString("url");
                        PlaceHolders.imgId = json.getString("imageId");
                    }

                    TextComponent imageText = new TextComponent(getMsg("Image.text", null));
                    imageText.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, msgChainData.url));
                    imageText.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                            new ComponentBuilder(
                                    hoverEvent_txt_replace(getMsg("Image.hoverEvent", null))
                            ).create()));
                    text.addExtra(imageText);

                } else if (msgChainData.type.equals("At") && BOT.isCatch_at) {
                    if (isForward) continue;
                    if (BotMain.hasPAPI) PlaceHolders.at_target = JSON.parseObject(str).getLong("target");
                    TextComponent at_txt = new TextComponent(getMsg("At.text",null));
                    at_txt.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                            new ComponentBuilder(
                                    hoverEvent_txt_replace(getMsg("At.hoverEvent",null))
                            ).create()));
                    text.addExtra(at_txt);

                } else if (msgChainData.type.equals("AtAll") && BOT.isCatch_atAll) {
                    TextComponent atAll = new TextComponent(getMsg("atAll.text", null));
                    atAll.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                            new ComponentBuilder(
                                    hoverEvent_txt_replace(getMsg("atAll.hoverEvent", null))
                            ).create()));
                    text.addExtra(atAll);

                } else if (msgChainData.type.equals("Face") && BOT.isCatch_face) {
                    if (BotMain.hasPAPI){
                        JSONObject json = JSON.parseObject(str);
                        PlaceHolders.faceName = json.getString("name");
                        PlaceHolders.faceId = json.getInteger("faceId");
                    }
                    TextComponent face = new TextComponent(getMsg("Face.text", null));
                    face.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                            new ComponentBuilder(
                                    hoverEvent_txt_replace(getMsg("Face.hoverEvent", null))
                            ).create()));
                    text.addExtra(face);

                } else if (msgChainData.type.equals("Forward") && BOT.isCatch_forward) {
                    TextComponent forward = new TextComponent(getMsg("Forward.text", null));
                    forward.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                            new ComponentBuilder(
                                    hoverEvent_txt_replace(getMsg("Forward.hoverEvent", null))
                            ).create()));
                    forward.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/bot open"));
                    text.addExtra(forward);
                    // 建立一本书
                    BookCreator bc = new BookCreator(PlaceHolders.messageId);
                    bc.addContent(msgChainData.nodeList);
                    bc.create();
                    Messages.DEBUG.info("BOT_[DEBUG_INFO] Create Book: Book_" + PlaceHolders.messageId);
                }
            }
            return text;
        }
        return null;
    }
}
