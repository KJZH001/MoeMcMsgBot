package me.ed333.easyBot.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 创建一个 MessageChain
 */
public class MessageChain {
    private final JSONArray json = new JSONArray();

    @Override
    public String toString() {
        return json.toString();
    }

    public Object[] toArray() { return json.toArray(); }

    /**
     * 添加一个文本消息
     * @param text 文本内容
     * @return MessageChain
     */
    public MessageChain addPlain(String text) {
        JSONObject plainTxt = new JSONObject();
        plainTxt.put("type", "Plain");
        plainTxt.put("text", text);
        this.json.add(plainTxt);
        return this;
    }

    /**
     * <p>通过图片ID来添加一个图片消息</p>
     * <p>注意的是， 好友消息的图片格式是和群图片格式不一样的：</p>
     * <p>  好友图片ID格式：xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx</p>
     * <p>  群图片ID格式：{xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx}.mirai</p>
     * @param ImageID 图片的ID
     * @return MessageChain
     */
    public MessageChain addImage(String ImageID) {
        JSONObject imageTxt = new JSONObject();
        imageTxt.put("type", "Image");
        imageTxt.put("ImageId", ImageID);
        this.json.add(imageTxt);
        return this;
    }

    /**
     * <p>通过网络图片添加一个图片消息</p>
     * @param Url 图片的地址
     * @return MessageChain
     */
    public MessageChain addImageByUrl(String Url) {
        JSONObject imageTxt = new JSONObject();
        imageTxt.put("type", "Image");
        imageTxt.put("url", Url);
        this.json.add(imageTxt);
        return this;
    }

    /**
     * 添加一个 At
     * @param QQ 被At的QQ
     * @return MessageChain
     */
    public MessageChain addAt(long QQ) {
        JSONObject atTxt = new JSONObject();
        atTxt.put("type", "At");
        atTxt.put("target", QQ);
        this.json.add(atTxt);
        return this;
    }

    /**
     * 添加一个 @全体成员
     * @return MessageChain
     */
    public MessageChain addAtAll() {
        JSONObject atAll = new JSONObject();
        atAll.put("type", "AtAll");
        this.json.add(atAll);
        return this;
    }

    /**
     * 通过表情 id 添加一个表情
     * @param id 表情id
     * @return message chain
     */
    public MessageChain addFace_byID(int id) {
        JSONObject face = new JSONObject();
        face.put("faceId", id);
        this.json.add(face);
        return this;
    }

    /**
     * 通过表情的拼音添加一个表情
     * @param name 表情拼音
     * @return message chain
     */
    public MessageChain addFace_byName(String name) {
        JSONObject face = new JSONObject();
        face.put("faceId", name);
        this.json.add(face);
        return this;
    }
}
