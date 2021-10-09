package dev.sasukector.thirtysecondstodie;

import dev.sasukector.thirtysecondstodie.commands.GameCommand;
import dev.sasukector.thirtysecondstodie.commands.OptionsCommand;
import dev.sasukector.thirtysecondstodie.events.EpicEvents;
import dev.sasukector.thirtysecondstodie.events.NormalEvents;
import dev.sasukector.thirtysecondstodie.events.RareEvents;
import dev.sasukector.thirtysecondstodie.events.SpawnEvents;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class ThirtySecondsToDie extends JavaPlugin {

    private static @Getter ThirtySecondsToDie instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info(ChatColor.DARK_PURPLE + "ThirtySecondsToDie startup!");
        instance = this;

        // Register events
        this.getServer().getPluginManager().registerEvents(new SpawnEvents(), this);
        this.getServer().getPluginManager().registerEvents(new NormalEvents(), this);
        this.getServer().getPluginManager().registerEvents(new RareEvents(), this);
        this.getServer().getPluginManager().registerEvents(new EpicEvents(), this);

        // Register commands
        Objects.requireNonNull(ThirtySecondsToDie.getInstance().getCommand("game")).setExecutor(new GameCommand());
        Objects.requireNonNull(ThirtySecondsToDie.getInstance().getCommand("options")).setExecutor(new OptionsCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info(ChatColor.DARK_PURPLE + "ThirtySecondsToDie shutdown!");
    }
}
