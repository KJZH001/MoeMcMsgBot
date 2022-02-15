package me.ed333.easybot.plugin.with_mirai_api_http.utils;

import me.ed333.easybot.plugin.with_mirai_api_http.Configs;
import me.ed333.easybot.plugin.with_mirai_api_http.Main;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class VerifyingPlayer extends BukkitRunnable{
    private final Player p;
    private final Long qq;

    public VerifyingPlayer(Player p, Long qq) {
        this.p = p;
        this.qq = qq;
        BotUtils.addPlayerInCool(p);

        new BukkitRunnable() {
            @Override
            public void run() {
                BotUtils.remPlayerInCool(p);
            }
        }.runTaskLater(Main.getPlugin(Main.class), Configs.CODE_COOLDOWN.getInt()*60*20);
    }

    public Player getPlayer() {
        return p;
    }

    public Long getQQ() {
        return qq;
    }

    @Override
    public void run() {
        BotUtils.remVerifyPlayer(this);
    }

    @Override
    public synchronized void cancel() throws IllegalStateException {
        super.cancel();
    }
}
