package br.com.extraplays.extracash.utils;

import com.google.common.collect.ImmutableMap;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;
import java.util.Map;

public class MessageUtil {

    private static Map<String, String> messages;

    public static void LoadMessages(FileConfiguration fileConfiguration){

        ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();

        fileConfiguration.getValues(true)
                .forEach((path, value)->
                        builder.put(path, ChatColor.translateAlternateColorCodes('&', value.toString())));

        messages = builder.build();

    }

    public static String getMessage(String path){
        return messages.getOrDefault(path, "Fail to get " + path);
    }

}
