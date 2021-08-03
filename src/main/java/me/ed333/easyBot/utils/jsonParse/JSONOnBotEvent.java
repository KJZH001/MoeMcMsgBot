package me.ed333.easyBot.utils.jsonParse;

import lombok.Data;

/**
 * 接收到 Bot 自身事件的 json 时解析以下对象
 */
@Data
public class JSONOnBotEvent {
    /**Bot 的QQ号码*/
    public long qq;
}
