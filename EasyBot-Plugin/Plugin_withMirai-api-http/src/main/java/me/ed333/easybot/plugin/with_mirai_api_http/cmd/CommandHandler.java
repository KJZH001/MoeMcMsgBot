package me.ed333.easybot.plugin.with_mirai_api_http.cmd;

import me.ed333.easybot.plugin.with_mirai_api_http.cmd.sub.BindChange;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class CommandHandler implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (command.getName().equalsIgnoreCase("bot") && args.length >= 1) {
            if (args[0].equalsIgnoreCase("bindchange") || args[0].equalsIgnoreCase("bc")) {
                new BindChange(sender, args);
                return false;
            }

            Class<?> subCmdClass;
            try {
                subCmdClass = Class.forName("me.ed333.easybot.plugin.with_mirai_api_http.cmd.sub." + captureFirstChar(args[0]));
                Constructor<?> subConstructor = subCmdClass.getConstructor(CommandSender.class, String[].class);
                subConstructor.newInstance(sender, args);
            } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
                //
                // sender.sendMessage(BotAPI.getILanguageUtils().getLangText("unKnowCmd"));
            }
        }
        return false;
    }

    // https://blog.csdn.net/zhurhyme/article/details/27951099
    private @NotNull String captureFirstChar(@NotNull String str){
        char[] cs=str.toLowerCase().toCharArray();
        cs[0]-=32;
        return String.valueOf(cs);
    }
}
