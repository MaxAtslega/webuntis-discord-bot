package de.atslega.untisbot.exeption;

import de.atslega.untisbot.commands.SlashCommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public abstract class CommandException extends Exception {
    private SlashCommand slashCommand;
    private SlashCommandInteractionEvent context;

    public CommandException(SlashCommand cmd, SlashCommandInteractionEvent ctx) {
        this.slashCommand = cmd;
        this.context = ctx;
    }

    public SlashCommand getCommand() {
        return slashCommand;
    }

    public SlashCommandInteractionEvent getContext() {
        return context;
    }

    public abstract String getReplyMessage();
}
