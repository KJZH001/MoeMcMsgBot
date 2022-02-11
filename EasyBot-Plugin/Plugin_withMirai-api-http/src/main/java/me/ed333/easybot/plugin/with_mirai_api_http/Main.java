package me.ed333.easybot.plugin.with_mirai_api_http;

import me.ed333.easybot.api.BotAPI;
import me.ed333.easybot.api.EConfigKeys;
import me.ed333.easybot.plugin.with_mirai_api_http.cmd.CommandHandler;
import me.ed333.easybot.plugin.with_mirai_api_http.utils.BotUtils;
import me.ed333.easybot.plugin.with_mirai_api_http.utils.CacheUtils;
import me.ed333.easybot.plugin.with_mirai_api_http.utils.LanguageUtils;
import me.ed333.easybot.plugin.with_mirai_api_http.utils.MigrateData;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Collection;

public class Main extends JavaPlugin {
    private static boolean hasPAPI = false;
    private static final ConsoleCommandSender sender = Bukkit.getConsoleSender();
    private final File boundData = new File(getDataFolder(), "boundData.yml");
    private final File playerConfig = new File(getDataFolder(), "playerConfig.yml");
    private final File f = new File(getDataFolder(), "migrated");

    @Override
    public void onEnable() {

        getDataFolder().mkdirs();

        ConfigManager cm = new ConfigManager(
                new File(getDataFolder(), "config.yml"),
                new File(getDataFolder(), "lang.yml"),
                new File(getDataFolder(), "data.yml")
        );

        if (!cm.getConfigFile().exists()) saveResource("config.yml",false);
        if (!cm.getLangFile().exists()) saveResource("lang.yml", false);
        cm.checkFile();

        checkUpdate();
        initInterFace(cm);

        BotUtils.setKeySets();
        BotAPI.getILanguageUtils().loadLang();

        if (boundData.exists() && playerConfig.exists()) {
            migrate();
        }

        if (Configs.ENABLE_BOT.getBoolean()) {
            BotUtils.initialize();
            BotUtils.apiVer();
            sender.sendMessage("§3BOT: §a已全局启用，将转发群内消息到服务器中。");
        } else {
            sender.sendMessage("§3BOT: §e被全局禁用，将不会转发群内的消息到服务器中。");
        }


        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            hasPAPI = true;
            new PlaceHolders().register();
        }

        Bukkit.getPluginManager().registerEvents(new Listener(), this);

        PluginCommand cmd = this.getCommand("bot");
        if (cmd != null) {
            cmd.setExecutor(new CommandHandler());
        }

        // 防止使用 reload 命令后启用 bot 的玩家接收不到消息
        Collection<? extends Player> onlinePlayers = getServer().getOnlinePlayers();
        for (Player p : onlinePlayers) {
            if (BotAPI.getiConfigManager().getData().getBoolean("Player." + p.getUniqueId() + ".enableBot")) {
                BotAPI.getIbu().addEnableBotPlayer(p);
            }
        }
    }

    private void checkUpdate() {
        String updateUrl = "";
    }

    @Override
    public void onDisable() {
        if (SocketClient.isConnected) {
            BotUtils.close();
        }
        BotAPI.getiConfigManager().saveData();
    }

    public static boolean hasPAPI() { return hasPAPI; }

    private void initInterFace(ConfigManager cm) {
        BotAPI.setIConfigManager(cm);
        EConfigKeys.config = cm.getConfig();
        Configs.config = cm.getConfig();

        BotAPI.setICacheUtils(new CacheUtils());
        BotAPI.setILanguageUtils(new LanguageUtils());

        BotUtils b = new BotUtils();
        BotAPI.setIBotUtils(b);
        BotAPI.setIBotMiraiHttpUtils(b);
    }

    private void migrate() {
        sender.sendMessage(String.format("§3BOT: §e检测到旧版本文件 %s，迁移数据中...", boundData.getName()));
        if (!f.exists()) {
            f.mkdirs();
        }
        boundData.renameTo(new File(f, "boundData.yml"));

        sender.sendMessage(String.format("§3BOT: §e检测到旧版本文件 %s，迁移数据中...", playerConfig.getName()));
        if (!f.exists()) {
            f.mkdirs();
        }
        playerConfig.renameTo(new File(f, "playerConfig.yml"));


        new MigrateData(new File(f, "boundData.yml"), new File(f, "playerConfig.yml"));
    }
}
