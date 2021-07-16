package me.ed333.easyBot;

import me.ed333.easyBot.events.ListeningEvent;
import me.ed333.easyBot.utils.HttpRequest;
import me.ed333.easyBot.utils.Messages;
import me.ed333.easyBot.utils.PlaceHolders;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class BotMain extends JavaPlugin {
    
    public static BotMain INSTANCE;
    public BotMain() { INSTANCE = this; }
    
    public final File dataFolder = getDataFolder();
    public final File cfgFile = new File(dataFolder, "config.yml");
    public final File langFile = new File(dataFolder, "lang.yml");
    public final File boundDataFile = new File(dataFolder, "boundData.yml");
    public final File playerConfigFile = new File(dataFolder, "playerConfig.yml");

    public static YamlConfiguration cfg = new YamlConfiguration();
    public static YamlConfiguration lang = new YamlConfiguration();
    public static YamlConfiguration boundData = new YamlConfiguration();
    public static YamlConfiguration playerConfig = new YamlConfiguration();

    public static boolean hasPAPI = false;
    @Override
    public void onEnable() {
        try {

            if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
                hasPAPI = true;
                new PlaceHolders().register();
            }

            checkFile();
            Messages.initializeMsg();
            if (BOT.enableBot) BOT.initializeBot();

            Bukkit.getPluginManager().registerEvents(new ListeningEvent(), this);
            this.getCommand("bot").setExecutor(new CommandHandler());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        ConsoleCommandSender s = Bukkit.getConsoleSender();
        s.sendMessage("§3BOT: §a关闭并保存数据中...");
        try {
            if (Client.isConnected) BOT.close();
            playerConfig.save(playerConfigFile);
            boundData.save(boundDataFile);
            s.sendMessage("§3BOT: §a数据保存完毕！再见，感谢使用 EasyBot.");
        } catch (Exception e) {
            s.sendMessage("§3BOT: §c出错了！原因: " + e.getCause());
            e.printStackTrace();
        }
    }

    public void checkFile() throws IOException {
        if (!dataFolder.exists()) dataFolder.mkdirs();
        if (!boundDataFile.exists()) boundDataFile.createNewFile();
        if (!playerConfigFile.exists()) playerConfigFile.createNewFile();
        if (!cfgFile.exists()) saveResource("config.yml",false);
        if (!langFile.exists()) saveResource("lang.yml", false);

        cfg = YamlConfiguration.loadConfiguration(cfgFile);
        lang = YamlConfiguration.loadConfiguration(langFile);
        boundData = YamlConfiguration.loadConfiguration(boundDataFile);
        playerConfig = YamlConfiguration.loadConfiguration(playerConfigFile);

        if (boundData.getKeys(false).isEmpty()) {
            boundData.createSection("QQ_Bound");
            boundData.createSection("Name_Bound");
        }
    }
}
