package me.ed333.easyBot.utils.jsonParse;

import lombok.Data;

/**
 * 好友消息撤回时，对 json 的解析以下对象
 */
@Data
public class JSONOnFriendRecall {
    // 好友的QQ号码
    public long authorId;
    // 撤回消息的 id
    public int messageId;
}
