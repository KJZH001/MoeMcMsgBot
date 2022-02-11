package me.ed333.easybot.api.messages;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.ed333.easybot.api.BotAPI;
import me.ed333.easybot.api.Vars;
import me.ed333.easybot.api.utils.ILanguageUtils;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

/**
 * 创建一个 MessageChain
 */
public class MessageChain {
    private JsonArray jsonArray = new JsonArray();
    private boolean hasQuote = false;
    private final ILanguageUtils ilu = BotAPI.getILanguageUtils();

    @Override
    public String toString() {
        return jsonArray.toString();
    }

    public JsonArray getAsJsonArray() { return jsonArray; }

    /**
     * 添加一个文本消息
     * @param text 文本内容
     * @return {@link MessageChain}
     */
    public MessageChain addPlain(String text) {
        JsonObject plainTxt = new JsonObject();
        plainTxt.addProperty("type", "Plain");
        plainTxt.addProperty("text", text);
        this.jsonArray.add(plainTxt);
        return this;
    }

    /**
     * <p>通过图片ID来添加一个图片消息</p>
     * <p>注意的是， 好友消息的图片格式是和群图片格式不一样的：</p>
     * <p>  好友图片ID格式：xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx</p>
     * <p>  群图片ID格式：{xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx}.mirai</p>
     * @param ImageID 图片的ID
     * @return {@link MessageChain}
     */
    public MessageChain addImage(String ImageID) {
        JsonObject imageTxt = new JsonObject();
        imageTxt.addProperty("type", "Image");
        imageTxt.addProperty("ImageId", ImageID);
        this.jsonArray.add(imageTxt);
        return this;
    }

    /**
     * <p>通过网络图片添加一个图片消息</p>
     * @param Url 图片的地址
     * @return {@link MessageChain}
     */
    public MessageChain addImageByUrl(String Url) {
        JsonObject imageTxt = new JsonObject();
        imageTxt.addProperty("type", "Image");
        imageTxt.addProperty("url", Url);
        this.jsonArray.add(imageTxt);
        return this;
    }

    /**
     * 添加一个 At
     * @param QQ 被At的QQ
     * @return {@link MessageChain}
     */
    public MessageChain addAt(long QQ) {
        JsonObject atTxt = new JsonObject();
        atTxt.addProperty("type", "At");
        atTxt.addProperty("target", QQ);
        this.jsonArray.add(atTxt);
        return this;
    }

    /**
     * 添加一个 @全体成员
     * @return {@link MessageChain}
     */
    public MessageChain addAtAll() {
        JsonObject atAll = new JsonObject();
        atAll.addProperty("type", "AtAll");
        this.jsonArray.add(atAll);
        return this;
    }

    /**
     * 通过表情 id 添加一个表情
     * @param id 表情id
     * @return {@link MessageChain}
     */
    public MessageChain addFace_byID(int id) {
        JsonObject face = new JsonObject();
        face.addProperty("faceId", id);
        this.jsonArray.add(face);
        return this;
    }

    /**
     * 通过表情的拼音添加一个表情
     * @param name 表情拼音
     * @return {@link MessageChain}
     */
    public MessageChain addFace_byName(String name) {
        JsonObject face = new JsonObject();
        face.addProperty("faceId", name);
        this.jsonArray.add(face);
        return this;
    }

    /**
     * 将 http 返回消息中的 "messageChain" 对象转换成 {@link MessageChain}
     * @param jsonArray "messageChain" 中的内容，是一个数组
     * @return {@link MessageChain}
     */
    public MessageChain parseToMessageChain(JsonArray jsonArray) {
        this.jsonArray = jsonArray;
        return this;
    }

    /**
     * 将该消息链转成用于发送到游戏中的 {@link TextComponent} 的形式，
     * @return {@link TextComponent}
     */
    public TextComponent toTextComponent() {
        TextComponent txt = new TextComponent();
        TextComponent t = new TextComponent();
        for (Object obj : jsonArray) {
            JsonObject jsonObject = new JsonParser().parse(obj.toString()).getAsJsonObject();
            String type = jsonObject.get("type").getAsString();

            if (type.equals("Source")) {
                Vars.msgID = jsonObject.get("id").getAsInt();
                Vars.time = jsonObject.get("time").getAsLong();
            }

            if (type.equals("Plain")) {
                t = new TextComponent(jsonObject.get("text").getAsString().replace("\n", " "));
                t.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(
                        ilu.hoverEvent_txt_replace(ilu.getLangText("Plain.hoverEvent"))
                ).create()));
            }

            if (type.equals("Image")) {
                Vars.imgUrl = jsonObject.get("url").getAsString();
                Vars.imgId = jsonObject.get("imageId").getAsString();

                t = new TextComponent(ilu.getLangText("Image.text"));
                t.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(
                        ilu.hoverEvent_txt_replace(ilu.getLangText("Image.hoverEvent"))
                ).create()));

                t.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, Vars.imgUrl));
            }

            if (type.equals("At")) {
                Vars.atTarget = jsonObject.get("target").getAsLong();
                t = new TextComponent(ilu.getLangText("At.text"));
                t.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(
                        ilu.hoverEvent_txt_replace(ilu.getLangText("At.hoverEvent"))
                ).create()));
            }

            if (type.equals("AtAll")) {
                t = new TextComponent(ilu.getLangText("atAll.text"));
                t.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(
                        ilu.hoverEvent_txt_replace(ilu.getLangText("atAll.hoverEvent"))
                ).create()));
            }

            if (type.equals("Face")) {
                Vars.faceId = jsonObject.get("faceId").getAsInt();
                Vars.faceName = jsonObject.get("name").getAsString();

                t = new TextComponent(ilu.getLangText("Face.text"));
                t.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(
                        ilu.hoverEvent_txt_replace(ilu.getLangText("Face.hoverEvent"))
                ).create()));
            }

            txt.addExtra(t);
        }
        return txt;
    }

    /**
     * 将消息换成纯文本形式的内容
     * @return String message
     */
    public String toPlainText() {
        return toTextComponent().toPlainText();
    }
}
