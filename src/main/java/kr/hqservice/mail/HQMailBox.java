package kr.hqservice.mail;

import org.bukkit.plugin.java.JavaPlugin;

public class HQMailBox extends JavaPlugin {

    private static HQMailBox INSTANCE;

    public static HQMailBox getInstance() {
        return INSTANCE;
    }

    @Override
    public void onLoad() {
        INSTANCE = this;
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {

    }
}
