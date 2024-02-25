package com.meteoritepvp.colorstest.commands;

import com.meteoritepvp.api.MeteoritePlugin;
import com.meteoritepvp.api.command.Command;
import com.meteoritepvp.api.command.CommandClass;
import com.meteoritepvp.api.command.DefaultCommand;
import com.meteoritepvp.api.inventory.MeteoriteInventory;
import com.meteoritepvp.api.inventory.presets.BackableInventory;
import com.meteoritepvp.api.inventory.presets.BasicInventory;
import com.meteoritepvp.api.utils.CC;
import com.meteoritepvp.api.utils.ItemBuilder;
import com.meteoritepvp.api.utils.Message;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

@DefaultCommand
public class ColorCommand {

    private MeteoriteInventory inventory;
    private BasicInventory page;
    private List<Material> chosenColors = new ArrayList<>();

    public SampleCommand(MeteoritePlugin plugin) {
        // Create the inventory for displaying colored wool
        inventory = new MeteoriteInventory(plugin, CC.BLUE + CC.BOLD + "Your Colors", 9, 6, false);

        // Create the only page for this inventory, initially empty
        page = new BasicInventory();
        page.update();
        inventory.applyPage(page);
    }

    // Updates the inventory page with the correct wool blocks
    private void updateInventory() {
        // Populate the inventory with the chosen colors
        for (int i=0; i<chosenColors.size(); i++) {
            Material mat = chosenColors.get(i);
            page.setItem(i, new ItemStack(mat));
        }

        // Fill the rest with blank spaces (16 is the number of possible wool colors)
        for (int i=chosenColors.size(); i<16; i++) {
            page.setItem(i, new ItemStack(Material.AIR));
        }

        // Finally, update the page
        page.update();
    }

    @Command(name = "", description = "View the chosen colors.")
    public void viewColorsCommand(CommandSender sender, Player player) {
        if (player == null) {
            sender.sendMessage("This command must be used by a player.");
        } else {
            // If command was run by a player, show them the colors inventory
            inventory.show(player);
        }
    }

    @Command(name = "add", description = "Add a color to the list.", params = "@colorParam")
    public void addColorCommand(CommandSender sender, Player player, String[] params) {
        String color = params[0];
        Material wool = Material.getMaterial((color + "_wool").toUpperCase());

        // Check if this color of wool exists
        if (wool == null) {
            sender.sendMessage(Message.fromString(player, "&4'" + color + "' is not a valid wool color."));
        } else {
            // Only add it to the list if it's not already on the list
            if (chosenColors.contains(wool)) {
                sender.sendMessage(Message.fromString(player, "&4The color '" + color + "' is already on the list."));
            } else {
                chosenColors.add(wool);
                updateInventory();
                sender.sendMessage(Message.fromString(player, "&aThe color '" + color + "' has been added to the list!"));
            }
        }
    }

    @Command(name = "remove", description = "Remove a color from the list.", params = "@colorParam")
    public void removeColorCommand(CommandSender sender, Player player, String[] params) {
        String color = params[0];
        Material wool = Material.getMaterial((color + "_wool").toUpperCase());

        // Check if this color of wool exists
        if (wool == null) {
            sender.sendMessage(Message.fromString(player, "&4'" + color + "' is not a valid wool color."));
        } else {
            // Remove the color if possible, and check if it was on the list
            if (chosenColors.remove(wool)) {
                updateInventory();
                sender.sendMessage(Message.fromString(player, "&aThe color '" + color + "' has been removed from the list!"));
            } else {
                sender.sendMessage(Message.fromString(player, "&4The color '" + color + "' was not on the list."));
            }
        }
    }

}
