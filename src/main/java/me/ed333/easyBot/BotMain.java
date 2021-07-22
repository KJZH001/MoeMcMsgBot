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
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Set;

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

            /**
             * 防止使用 reload 命令后启用bot的玩家接收不到消息
             */
            Collection<? extends Player> onlinePlayers = getServer().getOnlinePlayers();
            for (Player p : onlinePlayers) {
                if (playerConfig.getBoolean(p.getUniqueId() + ".enable_Bot")) {
                    BOT.enableBot_Players.add(p);
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
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

        if (cfgFile.exists() || langFile.exists()) {
            // 自定添加配置
            InputStream cfgStream_in = getResource("config.yml");
            YamlConfiguration resourceCfg = YamlConfiguration.loadConfiguration(new InputStreamReader(cfgStream_in));
            Set<String> resourceCfgKeys = resourceCfg.getKeys(true);

            for (String key : resourceCfgKeys) {
                Object configVal = cfg.get(key);
                if (configVal == null) cfg.set(key, resourceCfg.get(key));
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(getResource("lang.yml"), StandardCharsets.UTF_8));
            YamlConfiguration resourceLang = YamlConfiguration.loadConfiguration(br);
            Set<String> resourceLangKeys = resourceLang.getKeys(true);

            for (String key : resourceLangKeys) {
                Object langVal = lang.get(key);
                if (langVal == null) lang.set(key, resourceLang.get(key));
            }

            cfg.save(cfgFile);
            lang.save(langFile);
        }
    }
}
