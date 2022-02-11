package me.ed333.easybot.plugin.with_mirai_api_http.cmd.sub;

import me.ed333.easybot.api.BotAPI;
import me.ed333.easybot.api.utils.ILanguageUtils;
import me.ed333.easybot.plugin.with_mirai_api_http.utils.BotUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class Verify {
    public Verify(CommandSender sender, String[] args) {
        ILanguageUtils ilu = BotAPI.getILanguageUtils();

        if (!(sender instanceof Player)) {
            sender.sendMessage(ilu.getLangText("notPlayer"));
            return;
        }

        Player p = (Player) sender;

        // 参数长度是否正确
        if (args.length != 2) {
            sender.sendMessage(ilu.getLangText("InvalidArgs"));
            return;
        }

        // 验证码的格式是否正确
        if (!isCode(args[1])) {
            sender.sendMessage(ilu.getLangText("verify_nonCode"));
            return;
        }

        try{
            Set<Player> players = new HashSet<>();
            BotUtils.getVerifyingPlayers().forEach((a, b) -> players.add(a.getPlayer()));
            if (!players.contains(p)) {
                sender.sendMessage(ilu.getLangText("verify_nonVerifyPlayer"));
                throw new Error();
            }

            BotUtils.getVerifyingPlayers().forEach((vfp, code) -> {
                if (vfp.getPlayer().equals(p)) {
                    if (Integer.parseInt(args[1]) == code) {
                        ConfigurationSection section = BotAPI.getiConfigManager().getData().getConfigurationSection("Player");
                        if (section != null) {
                            section.set(p.getUniqueId() + ".bind", vfp.getQQ());
                        }
                        BotUtils.addBindQQ(vfp.getQQ(), p.getName());
                        BotAPI.getiConfigManager().saveData();
                        BotUtils.remVerifyPlayer(vfp);
                        BotUtils.remPlayerInCool(vfp.getPlayer());
                        sender.sendMessage(ilu.getLangText("verify_success"));
                        throw new Error();
                    } else {
                        sender.sendMessage(ilu.getLangText("verifyCode_Err"));
                    }
                }
            });
        } catch (Error ignored) {}
    }

    private boolean isCode(String code) {
        if (code.length() != 6) {
            return false;
        }
        return code.matches("[0-9][0-9]{5}");
    }
}