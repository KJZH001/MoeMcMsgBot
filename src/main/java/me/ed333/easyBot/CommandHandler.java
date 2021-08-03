package me.ed333.easyBot;

import lombok.SneakyThrows;
import me.ed333.easyBot.command.*;
import me.ed333.easyBot.utils.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;



public class CommandHandler implements CommandExecutor {

    @SneakyThrows
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (command.getName().equalsIgnoreCase("bot")) {
            if (args.length >= 1 ) {
                switch (args[0].toLowerCase()) {
                    case "help":
                        new Help(sender);
                        break;
                    case "on":
                        new On(sender);
                        break;
                    case "off":
                        new Off(sender);
                        break;
                    case "reload":
                        new Reload(sender);
                        break;
                    case "enable":
                        new Enable(sender);
                        break;
                    case "disable":
                        new Disable(sender);
                        break;
                    case "verify":
                        new Verify(args, sender);
                        break;
                    case "bind":
                        new Bind(args, sender);
                        break;
                    case "debug":
                        new Debug(sender);
                        break;
                    case "book":
                        new Book(args, sender);
                        break;
                    default:
                        sender.sendMessage(Messages.getMsg("unKnowCmd", null));
                }
            } else sender.sendMessage(Messages.getMsg("unKnowCmd", null));
        }
        return false;
    }
}

