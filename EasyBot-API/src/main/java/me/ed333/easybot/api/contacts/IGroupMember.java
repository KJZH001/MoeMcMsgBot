package me.ed333.easybot.api.contacts;

public interface IGroupMember {
    /**
     * 群员的qq号码
     * @return 群员的qq号码
     */
    long ID();

    /**
     * 群员的群名片
     * @return 群员的群名片
     */
    String MemberName();

    /**
     * 群员的头衔
     * @return 群员的头衔
     */
    String SpecialTitle();

    /**
     * 群员在群中的权限
     * @return 群员在群中的权限
     */
    String Permission();

    /**
     * 群员入群时间
     * @return 群员入群时间
     */
    long JoinTimestamp();

    /**
     * 群员上一次说话的时间
     * @return 群员上一次说话的时间
     */
    long LastSpeakTimestamp();

    /**
     * 群员剩余的禁言时间
     * @return 群员剩余的禁言时间
     */
    long muteTimeRemaining();
}

