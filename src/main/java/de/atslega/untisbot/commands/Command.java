package de.atslega.untisbot.commands;

import de.atslega.untisbot.exeption.CommandException;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

import java.awt.*;

public abstract class Command {
    protected final CommandData data;
    protected boolean _public = false;

    protected Command(CommandData data) {
        this.data = data;
    }

    public CommandData getData() {
        return data;
    }

    public boolean isPublic() {
        return _public;
    }

    public abstract void run(SlashCommandInteractionEvent ctx) throws CommandException;

    protected void reply(SlashCommandInteractionEvent ctx, CharSequence text, Color color) {
        reply(ctx, text, color, _public);
    }

    protected void reply(SlashCommandInteractionEvent ctx, CharSequence text, Color color, boolean _public) {
        ctx.replyEmbeds(new EmbedBuilder()
                .setDescription(text)
                .setColor(color)
                .build()
        ).setEphemeral(!_public).queue();
    }


}
