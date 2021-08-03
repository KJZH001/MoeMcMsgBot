package me.ed333.easyBot.events.bot.GroupEvent;

/**
 * 群成员被解除禁言事件<br/>
 * 操作方法与 {@link MemberMuteEvent} 相同，<br/>
 * 但没有了{@link MemberMuteEvent#getDurationSeconds()} 方法 <br/>
 * 在本事件中使用此方法会返回数值 0
 */
public class MemberUnmuteEvent extends MemberMuteEvent {
    public MemberUnmuteEvent(String json) {
        super(json);
    }
}
