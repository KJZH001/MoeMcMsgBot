package me.ed333.easyBot.utils.jsonParse;

import lombok.Data;

import java.util.List;

/**
 * 当收到 群消息类型 或者 临时消息类型 的 json 时, 自动 <br/>
 * 将 json 中的 messageChain 解析为 list, 对 msgChain 的解析见 {@link msgChainData} <br/>
 * 将 json 中的 sender 解析为 Object, 对 sender 的解析见 {@link senderData} <br/>
 */
@Data
public class JSONOnGroupMessage {
    public List<String> messageChain;
    public Object sender;

    /**
     * 需要提供 messageChain 中的一个对象才能解析 <br/>
     * 对象中不包含的内容为 null
     */
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
         * AT 的QQ，如果不包含此对象则为 null
         * 当 {@link msgChainData#type} 为 AtAll 时
         * 该值为 0
         */
        public long target;

        /**
         * 对转发消息的解析 <br/>
         * {@link ForwardMsgData}
         */
        public List<String> nodeList;

    }

    /**
     * 解析 sender 中的内容
     */
    @Data
    public static class senderData {
        // 发送者的 qq 号
        public long id;
        // 发送者的群名片
        public String memberName;
        // 发送者的头衔
        public String specialTitle;
        // 发送者权限，分别是 OWNER、ADMINISTRATOR、MEMBER
        public String permission;
        // 发送者的入群时间
        public long joinTimestamp;
        // 发送者最近一次说话的时间
        public long lastSpeakTimestamp;
        /**
         * 发送者所在群的信息, 对 group 对象的解析: <br/>
         * {@link senderGroupData}
         * */
        public Object group;

        @Data
        public static class senderGroupData {
            // 群号
            public long id;
            // 群名
            public String name;
        }
    }

    /**
     * 对转发消息的解析
     * 需要提供<br/>
     * {@link msgChainData#nodeList} 中的一个对象才能解析 <br/>
     * 对象中不包含的内容为 null
     */
    @Data
    public static class ForwardMsgData {
        // 该条转发消息发送者的QQ号码
        public long senderId;
        // 该条转发消息的发送时间
        public long time;
        // 该条转发消息发送者所在群的群名片
        public String senderName;
        /**
         * 该条转发消息的 messageChain
         * 其对应的解析采用 {@link msgChainData}
         */
        public List<String> messageChain;
    }
}
