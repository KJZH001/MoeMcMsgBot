package me.ed333.easyBot.events.bot.GroupEvent;


import me.ed333.easyBot.BOT;
import me.ed333.easyBot.utils.JsonParse;
import me.ed333.easyBot.utils.MessageChain;
import me.ed333.easyBot.utils.Messages;
import net.sf.json.JSONObject;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * 群员退群事件
 */
public class MemberLeaveEventQuit extends Event {
    private final JSONObject json;
    private final JsonParse p = new JsonParse();
    private static final HandlerList handlers = new HandlerList();


    public MemberLeaveEventQuit(JSONObject json) {
        super(true);
        this.json = json;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    private JSONObject getMemberObj() {
        return json.getJSONObject("member");
    }

    private JSONObject getGroupObj() {
        return getMemberObj().getJSONObject("group");
    }

    /**
     * 群员 Id
     * @return Member id
     */
    public Long getMember_Id() {
        return getMemberObj().getLong("Id");
    }

    /**
     * 群员名字
     * @return Member name
     */
    public String getMemberName() {
        return getMemberObj().getString("memberName");
    }

    /**
     * 群号
     * @return Group id
     */
    public Long getGroupId() {
        return getGroupObj().getLong("id");
    }

    /**
     * 群名
     * @return Group name
     */
    public String getGroupName() {
        return getGroupObj().getString("name");
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

