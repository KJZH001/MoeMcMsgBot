package me.ed333.easybot.api.utils;

import me.ed333.easybot.api.contacts.IGroup;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

/**
 * Bot 的通用接口
 */
public interface IBotUtils {
    /**
     * 获取启用群的属性
     * @param id 群号码
     * @return Group
     */
    IGroup getGroup(Long id);

    /**
     * 获取消息发送者所在的群
     * @return IGroup
     */
    IGroup getSenderGroup();

    /**
     * 获取所有启用群的属性
     * @return Groups
     */
    HashMap<Long, IGroup> getAllGroups();

    /**
     * 是否初始化了Bot
     */
    Boolean isInitialized();

    /**
     * 通过QQ号码获取群员绑定的游戏id，不存在则返回 null
     * @param qqNum qq 号码
     * @return game name
     */
    String getGameName(long qqNum);

    /**
     * 获取游戏名字，带有聊天组件({@link TextComponent})
     * @param qqNum qq 号码
     * @return TextComponent
     */
    TextComponent getGameName_ToComponent(long qqNum);

    /**
     * qq 号码是否被绑定了
     * @param qqNum QQ 号码
     * @return qq 号码是否被绑定了
     */
    boolean qq_isBound(long qqNum);

    /**
     * 游戏名字是否被绑定了
     * @param name 游戏名字
     * @return 游戏名字是否被绑定了
     */
    boolean name_isBound(String name);

    /**
     * 添加一个启用了Bot的玩家
     * @param p 玩家
     */
    void addEnableBotPlayer(Player p);

    /**
     * 添加多个启用Bot的玩家
     * @param p 玩家的集合
     */
    void addEnableBotPlayers(Set<Player> p);

    /**
     * 删除一个启用了Bot的玩家
     * @param p 玩家
     */
    void removeEnableBotPlayer(Player p);

    /**
     * 删除多个启用了Bot的玩家
     * @param p 玩家的集合
     */
    void removeEnableBotPlayers(Set<Player> p);

    /**
     * 删除所有启用了Bot的玩家
     */
    void removeAllEnabledBotPlayers();

    /**
     * 获取所有启用了Bot的玩家
     * @return 所有启用了Bot的玩家
     */
    Set<Player> getEnabledPlayers();

    /**
     * 获取一个启用了Bot的玩家
     * @param name 玩家名
     * @return Player
     */
    Player getEnableBotPlayer(String name);

    /**
     * 获取一个启用了Bot的玩家
     * @param uuid 玩家的uuid
     * @return Player
     */
    Player getEnableBotPlayer(UUID uuid);
}
