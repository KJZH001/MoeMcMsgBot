package me.ed333.easybot.plugin.with_mirai_api_http.event;


import com.google.gson.JsonObject;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginManager;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class Handler {
    private final JsonObject eventJson;
    private final PluginManager manager = Bukkit.getPluginManager();

    public Handler(JsonObject eventJson) {
        this.eventJson = eventJson;
        callEvent(eventJson.get("type").getAsString());
    }

    private void callEvent(String eventName) {
        Class<?> eventClass;
        try {
            if (eventName.startsWith("Bot")) {
                eventClass = Class.forName("me.ed333.easybot.api.events.botevent." + eventName);
                Constructor<?> eventConstructor = eventClass.getConstructor(JsonObject.class);
                manager.callEvent((Event) eventConstructor.newInstance(eventJson));
            }

            if (eventName.startsWith("Member") || eventName.equals("GroupMuteAllEvent")) {
                eventClass = Class.forName("me.ed333.easybot.api.events.groupevent." + eventName);
                Constructor<?> eventConstructor = eventClass.getConstructor(JsonObject.class);
                manager.callEvent((Event) eventConstructor.newInstance(eventJson));
            }

            if (eventName.endsWith("Message")) {
                eventClass = Class.forName("me.ed333.easybot.api.events.messageevent." + eventName);
                Constructor<?> eventConstructor = eventClass.getConstructor(JsonObject.class);
                manager.callEvent((Event) eventConstructor.newInstance(eventJson));
            }
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException ignored) {}
    }
}
