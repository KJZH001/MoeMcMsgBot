package me.ed333.easyBot.events.bot.GroupEvent;

import me.ed333.easyBot.BOT;
import me.ed333.easyBot.utils.JsonParse;
import me.ed333.easyBot.utils.MessageChain;
import me.ed333.easyBot.utils.Messages;
import net.sf.json.JSONObject;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * <p>被继承的事件类</p>
 * <p>该事件不会被触发</p>
 */
public class GroupEvent extends Event {
    private final JSONObject json;
    private final JsonParse p = new JsonParse();
    private static final HandlerList handlers = new HandlerList();

    public GroupEvent(JSONObject json) {
        super(true);
        this.json = json;
    }

    @Override
    public HandlerList getHandlers() { return handlers; }

    public static HandlerList getHandlerList() { return handlers; }

    public JSONObject getGroupObj() {
        return json.getJSONObject("group");
    }

    /**
     * 群号
     * @return Group name
     */
    public Long getGroup_Id() {
        return getGroupObj().getLong("id");
    }

    /**
     * 群名
     * @return Group name
     */
    public String getGroup_Name() {
        return getGroupObj().getString("name");
    }

    /**
     * Bot 在群中的权限
     * @return Bot permission in group
     */
    public String getGroup_Perm() {
        return getGroupObj().getString("permission");
    }


    /**
     * 发送群聊消息
     * @param quote 是否引用消息
     * @param code 引用消息的id，不引用设为0
     * @param chain 发送的消息链
     * @return 返回消息的id
     * @throws Exception 发送失败后抛出
     */
    public String sendGroupMessage(boolean quote, int code, MessageChain chain) throws Exception {
        String result = BOT.sendGroupMessage(p.getGroupID(json), quote, code, chain);
        Messages.printDEBUG(result);
        return result;
    }
}
