package me.ed333.easyBot.utils.jsonParse;

import lombok.Data;

/**
 * 群消息撤回类型的 json 解析
 */
@Data
public class JSONOnGroupRecall {
    // 被撤回消息的发送者 QQ 号码
    public long authorId;

    // 撤回消息的 id
    public int messageId;

    /**
     * 撤回消息所在群的信息<br/>
     * {@link groupData}
     */
    public Object group;

    /**
     * 有关执行该操作的 管理员/群主的信息 <br/>
     * {@link operatorData}
     */
    public Object operator;

    @Data
    public static class groupData {
        // 群号码
        public long id;
        // 群名称
        public String name;
    }

    @Data
    public static class operatorData {
        // QQ
        public long id;
        // 群名片
        public String memberName;
        // 头衔
        public String specialTitle;
        // 入群时间
        public long joinTimestamp;
        // 最近一次说话的时间
        public long lastSpeakTimestamp;
    }
}
