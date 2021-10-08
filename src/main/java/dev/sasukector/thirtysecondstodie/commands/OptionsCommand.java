package dev.sasukector.thirtysecondstodie.commands;

import dev.sasukector.thirtysecondstodie.controllers.EventsController;
import dev.sasukector.thirtysecondstodie.controllers.GameController;
import dev.sasukector.thirtysecondstodie.helpers.ServerUtilities;
import dev.sasukector.thirtysecondstodie.models.Event;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;

public class OptionsCommand implements CommandExecutor, TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player && player.isOp()) {
            if (args.length > 1) {
                String option = args[0];
                String subOption = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
                if (validOptions().contains(option) && validSubOptions(option).contains(subOption)) {
                    switch (option) {
                        case "event" -> {
                            Optional<Event> event = EventsController.getInstance().getEvents().stream()
                                    .filter(e -> e.getName().equals(subOption))
                                    .findFirst();
                            if (event.isPresent()) {
                                GameController.getInstance().setSetEvent(event.get());
                                ServerUtilities.sendServerMessage(player, "§bSe estableció el siguiente evento como: " +
                                        ServerUtilities.getCategoryColor(event.get().getCategory()) + "§l" + event.get().getName());
                            } else {
                                ServerUtilities.sendServerMessage(player, "§cEl evento seleccionado no es válido");
                            }
                        }
                        case "category" -> {
                            GameController.Category category = GameController.Category.getCategory(subOption);
                            if (category != null) {
                                GameController.getInstance().setCurrentCategory(category);
                                ServerUtilities.sendServerMessage(player, "§bSe estableció la categoría como: " + ServerUtilities.getCategoryStyle(category));
                            } else {
                                ServerUtilities.sendServerMessage(player, "§cLa categoría seleccionada no es válida");
                            }
                        }
                    }
                } else {
                    ServerUtilities.sendServerMessage(player, "§cSelecciona una opción válida");
                }
            } else {
                ServerUtilities.sendServerMessage(player, "§cSelecciona una opción");
            }
        } else if (sender instanceof Player player) {
            ServerUtilities.sendServerMessage(player, "§cPermisos insuficientes");
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> completions = new ArrayList<>();

        if(sender instanceof Player) {
            if (args.length == 1) {
                String partialItem = args[0];
                StringUtil.copyPartialMatches(partialItem, validOptions(), completions);
            } else if (args.length == 2) {
                String option = args[0];
                String partialItem = args[1];
                StringUtil.copyPartialMatches(partialItem, validSubOptions(option), completions);
            }
        }

        Collections.sort(completions);

        return completions;
    }

    public List<String> validOptions() {
        List<String> valid = new ArrayList<>();
        valid.add("event");
        valid.add("category");
        Collections.sort(valid);
        return valid;
    }

    public List<String> validSubOptions(String option) {
        GameController.Category category = GameController.getInstance().getCurrentCategory();
        List<String> valid = new ArrayList<>();
        switch (option.toLowerCase()) {
            case "event" -> valid = EventsController.getInstance().getEvents().stream()
                                        .filter(e -> e.getCategory() == category)
                                        .map(Event::getName).collect(Collectors.toList());
            case "category" -> valid = GameController.Category.getCategories();
        }
        Collections.sort(valid);
        return valid;
    }
}
