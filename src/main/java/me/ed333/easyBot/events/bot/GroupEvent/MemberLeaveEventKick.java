package me.ed333.easyBot.events.bot.GroupEvent;

/**
 * 群员被踢出事件
 * json 解析与 {@link MemberMuteEvent} 相同, <br/>
 * 但没有{@link MemberMuteEvent#getDurationSeconds()} 方法 <br/>
 */
public class MemberLeaveEventKick extends MemberMuteEvent {
    public MemberLeaveEventKick(String json) {
        super(json);
    }
}
