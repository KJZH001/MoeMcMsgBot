package me.ed333.easybot.api.contacts;

import me.ed333.easybot.api.messages.MessageSection;

import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

public interface IGroup {
    /**
     * 给这个群发送一条消息
     * @param chain 消息
     */
    void sendMessage(MessageSection chain);

    /**
     * 给这个群的群员发送条临时消息
     * @param qq 群员的 qq
     * @param chain 消息链
     */
    void sendTempMessage(Long qq, MessageSection chain);

    /**
     * 群名称
     * @return 群名称
     */
    String name();

    /**
     * 群号码
     * @return 群号码
     */
    long id();

    /**
     * 撤回消息
     * @param sourceID 消息的ID
     */
    void recall(int sourceID);

    /**
     * 禁言群员
     * @param member 群员属性
     * @param time 时间 单位: 秒
     */
    void mute(IGroupMember member, int time);

    /**
     * 解除群成员禁言
     * @param member 被解除禁言的群员
     */
    void unmute(IGroupMember member);

    /**
     * 全员禁言
     */
    void muteAll();

    /**
     * 移除群成员
     * @param member 群员
     * @param reason 踢出原因
     */
    void kick(IGroupMember member, String reason);

    /**
     * 通过qq获取启用群的一个群员属性
     * @param qq 群员的QQ号码
     * @return GroupMember
     */
    IGroupMember getGroupMember(Long qq);

    /**
     * 通过游戏名字获取启用群的一个群员属性
     * @param gameName 玩家的游戏名
     * @return GroupMember
     */
    IGroupMember getGroupMember(String gameName);

    /**
     * 通过UUID获取启用群的一个群员属性 <br/>
     * @param uid 群员的uuid
     * @return GroupMember
     */
    IGroupMember getGroupMember(UUID uid);

    /**
     * 获取部分群员的属性
     * @param qqs 群员qq的集合
     * @return GroupMember
     */
    HashMap<Long, IGroupMember> getGMembsByQQ(Set<Long> qqs);

    /**
     * 获取部分群员的属性
     * @param gameNames 游戏名字的集合
     * @return GroupMember
     */
    HashMap<Long, IGroupMember> getGMembsByGameName(Set<String> gameNames);

    /**
     * 获取部分群员的属性
     * @param uuids UUID 的集合
     * @return GroupMember
     */
    HashMap<Long, IGroupMember> getGMembsByUUID(Set<UUID> uuids);

    /**
     * 获取所有群员的属性
     * @return GroupMember
     */
    HashMap<Long, IGroupMember> getAll();
}
