package de.atslega.untisbot.exeption;

import de.atslega.untisbot.commands.Command;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public abstract class CommandException extends Exception {
    private Command slashCommand;
    private SlashCommandInteractionEvent context;

    public CommandException(Command cmd, SlashCommandInteractionEvent ctx) {
        this.slashCommand = cmd;
        this.context = ctx;
    }

    public Command getCommand() {
        return slashCommand;
    }

    public SlashCommandInteractionEvent getContext() {
        return context;
    }

    public abstract String getReplyMessage();
}
