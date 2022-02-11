package me.ed333.easybot.api.utils;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public interface IConfigManager {

    /**
     * lang.yml
     * @return File
     */
    File getLangFile();

    /**
     * config.yml
     * @return File
     */
    File getConfigFile();

    /**
     * data.yml
     * @return File
     */
    File getDataFile();

    /**
     * 释放所有 File
     */
    void releaseALLFiles();

    /**
     * data.yml
     * @return YamlConfiguration
     */
    YamlConfiguration getData();

    /**
     * lang.yml
     * @return YamlConfiguration
     */
    YamlConfiguration getLang();

    /**
     * config.yml
     * @return YamlConfiguration
     */
    YamlConfiguration getConfig();

    /**
     * 释放所有 YamlConfiguration
     */
    void releaseAllYamlConfigurations();

    void saveData();

    // Instance of ConfigManager
    IConfigManager getInstance();

    // Read changes in disk and reload it to memory.
    void reloadConfig();

    void checkFile();
}
