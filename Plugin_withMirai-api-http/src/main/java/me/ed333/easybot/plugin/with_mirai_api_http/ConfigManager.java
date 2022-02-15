package me.ed333.easybot.plugin.with_mirai_api_http;

import me.ed333.easybot.api.utils.IConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigManager implements IConfigManager {
    private static final ConsoleCommandSender sender = Bukkit.getConsoleSender();
    private static ConfigManager cm;
    private File configFile;
    private File langFile;
    private File dataFile;

    private YamlConfiguration config;
    private YamlConfiguration lang;
    private YamlConfiguration data;

    public ConfigManager(File configFile, File langFile, File dataFile) {
        cm = this;
        this.dataFile = dataFile;
        this.configFile = configFile;
        this.langFile = langFile;
    }

    @Override
    public File getLangFile() { return langFile; }

    @Override
    public File getConfigFile() { return configFile;}

    @Override
    public File getDataFile() {
        return dataFile;
    }

    @Override
    public IConfigManager getInstance() { return cm; }

    @Override
    public void reloadConfig() {
        config = YamlConfiguration.loadConfiguration(configFile);
        lang = YamlConfiguration.loadConfiguration(langFile);
        data = YamlConfiguration.loadConfiguration(dataFile);
    }

    @Override
    public void releaseALLFiles() {
        cm = null;
        langFile = null;
        configFile = null;
        dataFile = null;
    }

    @Override
    public YamlConfiguration getData() {
        return data;
    }

    @Override
    public YamlConfiguration getLang() { return lang; }

    @Override
    public YamlConfiguration getConfig() { return config; }

    @Override
    public void releaseAllYamlConfigurations() {
        data = null;
        config = null;
        lang = null;
    }

    @Override
    public void saveData() {
        try {
            data.save(dataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void checkFile() {
        try {
            if (!cm.getConfigFile().exists()) {
                Main.getPlugin(Main.class).saveResource("config.yml",false);
            }
            if (!cm.getLangFile().exists()) {
                Main.getPlugin(Main.class).saveResource("lang.yml", false);
            }
            if (!dataFile.createNewFile()) {
                dataFile.createNewFile();
            }

            config = YamlConfiguration.loadConfiguration(configFile);
            lang = YamlConfiguration.loadConfiguration(langFile);
            data = YamlConfiguration.loadConfiguration(dataFile);

            if (data.getKeys(false).isEmpty()) {
                data.createSection("Player");
                data.save(dataFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
