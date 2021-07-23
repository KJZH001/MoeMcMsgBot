package me.ed333.easyBot;

import me.ed333.easyBot.utils.MessageChain;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.UUID;

import static me.ed333.easyBot.Client.isConnected;
import static me.ed333.easyBot.utils.Messages.*;
import static me.ed333.easyBot.BOT.*;
import static me.ed333.easyBot.BotMain.*;
import static me.ed333.easyBot.utils.Messages.DEBUG.info;

public class CommandHandler implements CommandExecutor {

    private final BotMain i = BotMain.INSTANCE;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        try {

            if (command.getName().equalsIgnoreCase("bot")) {
                if (args.length == 0) sender.sendMessage(getMsg("unKnowCmd", null)); else {
                    if (args[0].equalsIgnoreCase("help")) {
                        for (String s : getList("help")) sender.sendMessage(s);
// /bot reload
                    } else if (args[0].equalsIgnoreCase("reload")) {
                        if (sender.hasPermission("bot.reload")) {
                            sender.sendMessage("§3BOT: §a保存配置并重新连接...");
                            boundData.save(i.boundDataFile);
                            playerConfig.save(i.playerConfigFile);
                            i.checkFile();
                            reloadMsg();

                            enableBot = cfg.getBoolean("enable_Bot");
                            if (enableBot) {
                                if (isConnected) close();
                                initializeBot();
                            } else close();

                            if (hasPAPI) new PlaceHolders().register();
                        } else sender.sendMessage(getMsg("permissionDeny", null));
// /bot on
                    } else if (args[0].equalsIgnoreCase("on")) {
                        if (sender.hasPermission("bot.on")) {
                            cfg.set("enable_Bot", true);
                            cfg.save(i.cfgFile);
                            if (!isConnected) initializeBot();
                            sender.sendMessage(getMsg("enable_Bot", null));
                        } else sender.sendMessage(getMsg("permissionDeny", null));
// /bot off
                    } else if (args[0].equalsIgnoreCase("off")) {
                        if (sender.hasPermission("bot.off")) {
                            BotMain.cfg.set("enable_Bot", false);
                            BotMain.cfg.save(i.cfgFile);
                            if (isConnected) close();
                            sender.sendMessage(getMsg("disable_Bot", null));
                        } else sender.sendMessage(getMsg("permissionDeny", null));
// /bot debug
                    } else if (args[0].equalsIgnoreCase("debug")) {
                        if (sender.hasPermission("bot.debug")) {
                            sender.sendMessage("enableBot_Players: " + enableBot_Players.toString());
                            sender.sendMessage("connect: " + isConnected);
                            sender.sendMessage("has PAPI: " + hasPAPI);
                        } else sender.sendMessage(getMsg("permissionDeny", null));
                    }else if (sender instanceof Player) {
                        Player p = (Player) sender;
                        UUID uid = p.getUniqueId();
// /bot enable
                        if (args[0].equalsIgnoreCase("enable")) {
                            enableBot_Players.add(p);
                            cfg.set(uid.toString() + ".enable_Bot", true);
                            sender.sendMessage(getMsg("player_enableBot", null));
// /bot disable
                        } else if (args[0].equalsIgnoreCase("disable")) {
                            enableBot_Players.remove(p);
                            cfg.set(uid.toString() + ".enable_Bot", false);
                            sender.sendMessage(getMsg("player_disableBot", null));
// /bot bind
                        } else if (args[0].equalsIgnoreCase("bind")) {
                            if (args.length == 2 && isQQ(args[1])) {
                                if (qq_isBound(Long.parseLong(args[1]))) {
                                    codeMap.put(p.getName(), genVerifyCode());
                                    verifyPlayers.put(p.getName(), Long.parseLong(args[1]));
                                    sender.sendMessage(getMsg("verify_msgSend", null));

                                    // 发送临时消息
                                    String str = sendTempMessage(
                                            Long.parseLong(args[1]),
                                            groupID,
                                            false,
                                            0,
                                            new MessageChain().addPlain(getMsg("verify_text", p)
                                                    .replace("%1", codeMap.get(p.getName()).toString())
                                                    .replace("%2", String.valueOf(BOT.verifyTime))
                                            )
                                    );
                                    // 建立验证码自销任务
                                    new codeAutomaticallyExpires(p.getName()).runTaskLater(BotMain.getPlugin(BotMain.class), verifyTime*60*20);

                                    info(" send result: " + str);
                                    info( "codeMap: " + codeMap);
                                } else sender.sendMessage(getMsg("QQisBound", null));

                            } else sender.sendMessage(getMsg("InvalidArgs", null));
                        } else if (args[0].equalsIgnoreCase("verify") && args.length == 2) {
                            if (BOT.codeMap.containsKey(p.getName())) {
                                if (args[1].equals(codeMap.get(p.getName()).toString())) {
                                    boundData.set("QQ_Bound." + verifyPlayers.get(p.getName()), p.getName());
                                    boundData.set("Name_Bound." + p.getName(), verifyPlayers.get(p.getName()));
                                    codeMap.remove(p.getName());
                                    verifyPlayers.remove(p.getName());
                                    boundData.save(INSTANCE.boundDataFile);
                                    info(codeMap.toString());
                                    info(verifyPlayers.toString());
                                    sender.sendMessage(getMsg("verify_success",null));
                                } else sender.sendMessage(getMsg("verifyCode_Err", null));
                            } else {
                                sender.sendMessage(getMsg("notInVerifying", null));
                            }
                        }
                    } else sender.sendMessage(getMsg("notPlayer", null));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean isQQ(@NotNull String qq) {
        return qq.matches(Objects.requireNonNull(BotMain.cfg.getString("regex")));
    }
}

