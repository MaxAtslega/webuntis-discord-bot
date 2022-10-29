package de.atslega.untisbot.commands;

import de.atslega.untisbot.exeption.CommandException;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

import java.awt.*;

public class PingCommand extends Command {

    public PingCommand(CommandData data) {
        super(data);
    }

    @Override
    public void run(SlashCommandInteractionEvent ctx) throws CommandException {
        reply(ctx, "Pong",  Color.BLUE, true);
    }
}
