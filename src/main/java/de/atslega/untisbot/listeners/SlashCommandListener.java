package de.atslega.untisbot.listeners;

import de.atslega.untisbot.UntisBot;
import de.atslega.untisbot.commands.Command;
import de.atslega.untisbot.exeption.CommandException;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Map;

import static de.atslega.untisbot.UntisBot.getInstance;

public class SlashCommandListener extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getGuild() == null) return;

        Map<String, Command> commands = null;
        try {
            commands = getInstance().getCommands();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            commands.get(event.getName()).run(event);
        } catch (CommandException e) {
            throw new RuntimeException(e);
        }
    }
}
