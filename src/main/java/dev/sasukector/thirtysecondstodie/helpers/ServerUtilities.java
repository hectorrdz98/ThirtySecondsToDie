package dev.sasukector.thirtysecondstodie.helpers;

import dev.sasukector.thirtysecondstodie.controllers.GameController;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ServerUtilities {

    private final static @Getter MiniMessage miniMessage = MiniMessage.miniMessage();

    // Associate all world names
    private final static Map<String, String> worldsNames;
    static {
        worldsNames = new HashMap<>();
        worldsNames.put("overworld", "world");
        worldsNames.put("nether", "world_nether");
        worldsNames.put("end", "world_the_end");
    }

    public static Component getPluginNameColored() {
        return miniMessage.deserialize("<gradient:#DC3636:#8B1919>30 ☠ segundos</gradient>");
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

    public static String getCategoryColor(GameController.Category category) {
        switch (category) {
            case NORMAL -> {
                return "§7";
            }
            case RARE -> {
                return "§2";
            }
            case EPIC -> {
                return "§d";
            }
            case LEGENDARY -> {
                return "§b";
            }
            case GOD -> {
                return "§e";
            }
        }
        return "";
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

    public static World getOverworld() {
        if (worldsNames.containsKey("overworld")) {
            return Bukkit.getWorld(worldsNames.get("overworld"));
        }
        return null;
    }

    public static char getCharFromString(String base) {
        int hex = Integer.parseInt(base, 16);
        return (char) hex;
    }

    public static void teleportPlayerToSafeHeight(Player player, Location location) {
        List<Integer> ys = Stream.iterate(2, n -> n + 1).limit(100).collect(Collectors.toList());
        Collections.shuffle(ys);
        for (int y : ys) {
            location.setY(y);
            Block cBlock = location.getBlock();
            Block tBlock = location.add(0, 1, 0).getBlock();
            Block lBlock = location.add(0, -2, 0).getBlock();
            if ((cBlock.getType() == Material.AIR || cBlock.getType() == Material.WATER) &&
                    (tBlock.getType() == Material.AIR || tBlock.getType() == Material.WATER) &&
                    (lBlock.getType().isSolid() || lBlock.getType() == Material.WATER)) {
                location.setY(y);
                player.teleport(location);
                break;
            }
        }
    }
}
