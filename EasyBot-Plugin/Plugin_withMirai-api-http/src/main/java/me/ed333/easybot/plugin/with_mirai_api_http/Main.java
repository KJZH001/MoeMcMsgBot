package me.ed333.easybot.plugin.with_mirai_api_http;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.ed333.easybot.api.BotAPI;
import me.ed333.easybot.api.EConfigKeys;
import me.ed333.easybot.plugin.with_mirai_api_http.cmd.CommandHandler;
import me.ed333.easybot.plugin.with_mirai_api_http.utils.*;
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

    // old data files //
    private final File boundData = new File(getDataFolder(), "boundData.yml");
    private final File playerConfig = new File(getDataFolder(), "playerConfig.yml");
    private final File f = new File(getDataFolder(), "migrated");
    //===============//

    @Override
    public void onLoad() {
        if (!getDataFolder().exists()) {
            getDataFolder().mkdirs();
        }
    }

    @Override
    public void onEnable() {
        ConfigManager cm = new ConfigManager(
                new File(getDataFolder(), "config.yml"),
                new File(getDataFolder(), "lang.yml"),
                new File(getDataFolder(), "data.yml")
        );

        cm.checkFile();
        initInterFace(cm);
        BotUtils.setKeySets();
        BotAPI.getILanguageUtils().loadLang();
        if (boundData.exists() && playerConfig.exists()) {
            migrate();
        }

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            hasPAPI = true;
            new PlaceHolders().register();
            sender.sendMessage("§3BOT: §a注册了 Placeholder 变量");
        }

        if (Configs.ENABLE_BOT.getBoolean() && HttpRequestUtils.canConnect()) {
            BotUtils.initialize();
            sender.sendMessage("§3BOT: §a已全局启用，将转发群内消息到服务器中。");
        } else {
            sender.sendMessage("§3BOT: §e被全局禁用，将不会转发群内的消息到服务器中。");
        }

        Bukkit.getPluginManager().registerEvents(new Listener(), this);

        PluginCommand cmd = this.getCommand("bot");
        if (cmd != null) {
            cmd.setExecutor(new CommandHandler());
        }

        if (Configs.UPDATE_CHECK.getBoolean()) {
            sender.sendMessage("§3BOT: §e检查更新...");
            checkUpdate();
        }
    }

    private void checkUpdate() {
        String updateUrl = "https://gitee.com/ed3/easyBot_Reloaded/raw/master/update/latest.json";
        JsonObject updateJson = new JsonParser().parse(HttpRequestUtils.doGet(updateUrl)).getAsJsonObject();
        int latestV = updateJson.get("Versions").getAsJsonObject().get("Plugin_api-http").getAsJsonObject().get("priority").getAsInt();
        Integer cfgV = Configs.CFG_VERSION.getInt();
        if (latestV > cfgV) {
            sender.sendMessage("§3BOT: §a发现了新版本！更新内容如下：");
            JsonArray array = updateJson.get("Versions").getAsJsonObject().get("Plugin_api-http").getAsJsonObject().get("updateLogs").getAsJsonArray();
            for (JsonElement json : array) {
                sender.sendMessage(json.getAsString());
            }
        }
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
        sender.sendMessage(String.format("§3BOT: §e检测到旧版本文件 %s，迁移数据中...", playerConfig.getName()));
        if (!f.exists()) {
            f.mkdirs();
        }
        boundData.renameTo(new File(f, "boundData.yml"));
        playerConfig.renameTo(new File(f, "playerConfig.yml"));
        new MigrateData(new File(f, "boundData.yml"), new File(f, "playerConfig.yml"));
    }
}
