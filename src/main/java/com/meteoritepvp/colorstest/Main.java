package com.meteoritepvp.colorstest;

import com.meteoritepvp.api.MeteoritePlugin;
import com.meteoritepvp.colorstest.commands.ColorCommand;
import com.meteoritepvp.sampleplugin.commands.GenericCommands;
import com.meteoritepvp.sampleplugin.commands.MainCommand;
import com.meteoritepvp.sampleplugin.commands.SampleCommand;

import java.util.ArrayList;
import java.util.List;

public class Main extends MeteoritePlugin {

    // Remove the main command
    @Override
    protected void onRegisterMainCommand(String description) { }

    @Override
    protected void onRegisterCommands(String... aliases) {
        // Create an alias for the /color command
        super.onRegisterCommands("colors", "color", "c");
    }

    @Override
    protected void onInit() {
        super.onInit();

        ColorCommand colorCommand = new ColorCommand(this);
        registerCommandObject(colorCommand);
    }


    @Override
    public void onEnable() {
        super.onEnable();

        saveDefaultConfig();
        reloadConfig();

        registerPlaceholderParameter("colorParam", (sender -> getColors()));
    }

    private static List<String> colors = new ArrayList<>();

    static {
        colors.add("white");
        colors.add("orange");
        colors.add("magenta");
        colors.add("light_blue");
        colors.add("yellow");
        colors.add("lime");
        colors.add("pink");
        colors.add("gray");
        colors.add("light_gray");
        colors.add("cyan");
        colors.add("purple");
        colors.add("blue");
        colors.add("brown");
        colors.add("green");
        colors.add("red");
        colors.add("black");
    }

    public static List<String> getColors() {
        return colors;
    }

}