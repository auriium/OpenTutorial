package xyz.auriium.opentutorial.centralized.config.messages;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class Message {

    private final String translatable;

    public Message(String translatable) {
        this.translatable = translatable;
    }

    public void send(CommandSender sender) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',translatable));
    }
}
