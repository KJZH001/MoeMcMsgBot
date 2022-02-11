package me.ed333.easybot.api;

/**
 * 该类供插件使用
 *
 * 用于提供 papi 的变量
 */
public class Vars {

    // 消息发送的时间
    public static Long time = 0L;
    // 消息的 ID
    public static Integer msgID;
    // 图片的 ID
    public static String imgId;
    // 图片的地址
    public static String imgUrl;
    // 被 AT 的 QQ
    public static Long atTarget;
    // 表情名称
    public static String faceName;
    // 表情 ID
    public static Integer faceId;
    // 发送者的 QQ
    public static Long senderId;
    // 发送者的群名片
    public static String senderNick;
    // 发送者所在的群号
    public static Long groupID;
}
