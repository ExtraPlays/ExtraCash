package br.com.extraplays.extracash.config;

import br.com.extraplays.extracash.ExtraCash;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ExtraConfig {

    private final File file;
    private String fileName;
    private FileConfiguration fileConfiguration;
    private final ExtraCash plugin = ExtraCash.getInstance();

    public ExtraConfig(String fileName){

        this.file = new File(plugin.getDataFolder(), fileName + ".yml");
        this.fileName = fileName;
        if (plugin.getResource(this.file.getName()) != null) {
            plugin.saveResource(this.file.getName(), false);
        }else {
            try{
                this.file.createNewFile();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }

        fileConfiguration = YamlConfiguration.loadConfiguration(this.file);
    }

    public FileConfiguration config(){
        return this.fileConfiguration;
    }

    public void save(){
        try {
            this.fileConfiguration.save(file);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void reload(){
        try {
            this.fileConfiguration.load(this.file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }



}
