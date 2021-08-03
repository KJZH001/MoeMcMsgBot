package me.ed333.easyBot.utils.jsonParse;

import lombok.Data;

/**
 * 对全员禁言的 json 解析
 */
@Data
public class JSONOnGroupMuteAll {
    // 原来的状态
    public boolean origin;
    // 现在的状态
    public boolean current;
    // 关于群的一些信息
    public Object group;
    // 关于执行该操作的人的信息
    public Object operator;

    @Data
    public static class GroupData {
        // 群号码
        public long id;
        // 群名称
        public String name;
    }

    @Data
    public static class OperatorData {
        // 操作者QQ号
        public long id;
        // 操作者群名片
        public String memberName;
        // 操作者头衔
        public String SpecialTitle;
        // 权限
        public String permission;
        // 入群时间
        public long joinTimestamp;
        // 上一次发言时间
        public long lastSpeakTimestamp;
    }
}
