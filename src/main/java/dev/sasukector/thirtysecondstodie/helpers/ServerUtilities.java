package dev.sasukector.thirtysecondstodie.helpers;

import dev.sasukector.thirtysecondstodie.controllers.GameController;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ServerUtilities {

    private final static @Getter MiniMessage miniMessage = MiniMessage.get();

    public static Component getPluginNameColored() {
        return miniMessage.parse("<bold><gradient:#DC3636:#8B1919>☠ Bits</gradient></bold>");
    }

    public static String getCategoryStyle(GameController.Category category) {
        String result = "?";
        switch (category) {
            case NORMAL -> result = "§7NORMAL";
            case RARE -> result = "§2RARA";
            case EPIC -> result = "§dÉPICA";
            case LEGENDARY -> result = "§bLEGENDARIA";
            case GOD -> result = "§eDIVINA";
        }
        return result;
    }

    public static void sendBroadcastMessage(String message) {
        Bukkit.broadcast(getPluginNameColored()
                .append(Component.text(" ▶ ", TextColor.color(0xC0C1C2)))
                .append(Component.text(message, TextColor.color(0xFFFFFF))));
    }

    public static void sendBroadcastMessage(Component message) {
        Bukkit.broadcast(getPluginNameColored()
                .append(Component.text(" ▶ ", TextColor.color(0xC0C1C2)))
                .append(message));
    }

    public static void sendServerMessage(Player player, String message) {
        player.sendMessage(getPluginNameColored()
                .append(Component.text(" ▶ ", TextColor.color(0xC0C1C2)))
                .append(Component.text(message, TextColor.color(0xFFFFFF))));
    }

    public static void sendServerMessage(Player player, Component message) {
        player.sendMessage(getPluginNameColored()
                .append(Component.text(" ▶ ", TextColor.color(0xC0C1C2)))
                .append(message));
    }

}
