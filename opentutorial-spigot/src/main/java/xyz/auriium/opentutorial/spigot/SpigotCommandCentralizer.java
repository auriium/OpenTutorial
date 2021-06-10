package xyz.auriium.opentutorial.spigot;

import co.aikar.commands.BukkitCommandManager;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.auriium.opentutorial.core.model.Cycleable;

public class SpigotCommandCentralizer implements Cycleable {

    private final JavaPlugin plugin;
    private final TutorialCommand tutorialCommand;

    public SpigotCommandCentralizer(JavaPlugin plugin, TutorialCommand tutorialCommand) {
        this.plugin = plugin;
        this.tutorialCommand = tutorialCommand;
    }


    @Override
    public void startup() {

    }

    @Override
    public void reload() {

    }

    @Override
    public void shutdown() {

    }
}
