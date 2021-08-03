package me.ed333.easyBot.utils.jsonParse;

import lombok.Data;

import java.util.List;

/**
 * 当收到好友消息类型的 json 时，自动 <br/>
 * 将 json 中的 messageChain 解析为 list, 对 msgChain 的解析见 {@link msgChainData} <br/>
 * 将 json 中的 sender 解析为 Object, 对 sender 的解析见 {@link senderData}
 */
@Data
public class JSONOnFriendMessage {
    public List<String> messageChain;
    public Object sender;

    @Data
    public static class msgChainData {
        // 消息类型
        public String type;
        // 消息id
        public int id;
        // 消息发送时间，以秒为单位
        public Long time;

        // 文本内容， 如果不包含此对象则为 null
        public String text;

        // 表情 id，如果不包含此对象则为 null
        public int faceId;
        // 表情名称，如果不包含此对象则为 null
        public String name;

        // 图片 id，如果不包含此对象则为 null
        public String imageId;
        // 图片的 url，如果不包含此对象则为 null
        public String url;

        /**
         * 对转发消息的解析 <br/>
         * {@link ForwardMsgData}
         */
        public List<String> nodeList;
    }



    /**
     * 对转发消息的解析
     * 需要提供<br/>
     * {@link JSONOnGroupMessage.msgChainData#nodeList} 中的一个对象才能解析 <br/>
     * 对象中不包含的内容为 null
     */
    @Data
    public static class ForwardMsgData {
        // 该条转发消息发送者的QQ号码
        public long senderId;
        // 该条转发消息的发送时间
        public long time;
        /*
        该条转发消息发送者的名称，若转发的为群消息则为群名片内容
        若好友消息为昵称
         */
        public String senderName;
        /**
         * 该条转发消息的 messageChain <br/>
         * 其对应的解析采用 {@link JSONOnGroupMessage.msgChainData}
         */
        public List<String> messageChain;
    }

    // 对 sender 的解析
    @Data
    public static class senderData {
        // 好友的 id
        public long id;
        // 好友的昵称
        public String nickname;
        // 好友的备注
        public String remark;
    }
}
