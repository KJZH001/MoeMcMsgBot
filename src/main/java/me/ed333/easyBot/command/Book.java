package me.ed333.easyBot.command;

import me.ed333.easyBot.utils.BookCreator;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.ed333.easyBot.utils.Messages.getMsg;

public class Book {


    public Book(String[] args, CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(getMsg("notPlayer", null));
            return;
        }

        Player p = (Player) sender;
        // /bot book open Book_123
        if (args.length == 2) {
            BookCreator.openBook(args[1], p);
        } else p.sendMessage(getMsg("unKnowCmd", null));
    }
}
