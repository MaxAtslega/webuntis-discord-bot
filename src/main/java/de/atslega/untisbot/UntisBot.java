package de.atslega.untisbot;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.atslega.untisbot.commands.SlashCommand;
import de.atslega.untisbot.commands.PingCommand;
import de.atslega.untisbot.utils.Config;
import de.atslega.untisbot.utils.FileUtils;
import io.sentry.Sentry;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static de.atslega.untisbot.utils.ShutdownCodes.FATAL_FAILURE;

public class UntisBot extends ListenerAdapter {
    private static final Logger log = LoggerFactory.getLogger(UntisBot.class);

    private static UntisBot instance;
    private JDA jda;

    private Map<String, SlashCommand> commands;

    public static final File configPath = new File("config.json");
    private static Config config;
    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .serializeNulls()
            .setLenient()
            .create();

    public static UntisBot getInstance() throws Exception {
        if (!hasInstance()) {
            instance = new UntisBot();
        }
        return instance;
    }

    public static boolean hasInstance() {
        return instance != null;
    }

    private UntisBot() throws Exception {
        init();
    }


    public static void main(String[] args){
        try {
            Sentry.init(options -> {
                options.setDsn(getConfig().getSentryDSN());
                options.setTracesSampleRate(1.0);
            });
        } catch (Exception e){
            log.error("Cannot init Sentry.");
        }


        try {
            instance = new UntisBot();
        } catch (Exception e) {
            log.error("Could not complete Main Thread routine!", e);
            System.exit(FATAL_FAILURE);
        }
    }

    public Map<String, SlashCommand> getCommands() {
        if (commands == null) {
            List<SlashCommand> slashCommands = Arrays.asList(
                    // bot
                    new PingCommand()
                   
            );

            this.commands = new HashMap<>();
            for (SlashCommand cmd : slashCommands) {
                this.commands.put(cmd.getData().getName(), cmd);
            }
        }
        return commands;
    }


    public void logoff() {
        log.info("Logging off...");
        if (jda != null) {
            jda.shutdown();
        }
    }

    public void init() throws LoginException {
        log.info("Logging in...");
        jda = JDABuilder.createDefault(getConfig().getToken())
                .addEventListeners(this)
                .setEnableShutdownHook(false)
                .build();

        jda.setRequiredScopes("applications.commands");

        jda.updateCommands()
                .addCommands(getCommands().values()
                        .stream()
                        .map(SlashCommand::getData)
                        .collect(Collectors.toList())
                )
                .queue();
    }



    public static Config getConfig() {
        if (config == null) {
            // load config
            try {
                if (configPath.exists()) {
                    config = gson.fromJson(FileUtils.readAll(configPath.toPath()), Config.class);
                } else {
                    config = new Config();
                    saveConfig();
                }
            } catch (IOException ex) {
                log.warn("Failed to load or create new config file", ex);
            }
        }
        return config;
    }

    public static void setConfig(Config config) {
        UntisBot.config = config;
    }

    public static void saveConfig() throws IOException {
        FileUtils.writeAll(configPath, gson.toJson(config));
    }



}
