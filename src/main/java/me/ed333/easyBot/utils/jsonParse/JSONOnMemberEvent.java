package me.ed333.easyBot.utils.jsonParse;

import lombok.Data;

/**
 * 可以解析以下事件的 json: <br/>
 * {@link me.ed333.easyBot.events.bot.GroupEvent.MemberMuteEvent} <br/>
 * {@link me.ed333.easyBot.events.bot.GroupEvent.MemberUnmuteEvent} <br/>
 */
@Data
public class JSONOnMemberEvent {
    // 禁言的时间
    public int durationSeconds;
    // 被禁言群员的信息
    public Object member;
    // 执行该操作的群主/管理员信息
    public Object operator;

    /**
     * member 和 operator 都可以用这个解析
     */
    @Data
    public static class dData {
        public long id;
        public String memberName;
        public String SpecialTitle;
        public String permission;
        public long joinTimestamp;
        public long lastSpeakTimestamp;
        public Object group;

        @Data
        public static class GroupData {
            public long id;
            public String name;
        }
    }
}
