package xyz.auriium.opentutorial.core.config;

import space.arim.dazzleconf.ConfigurationOptions;
import xyz.auriium.opentutorial.core.config.types.general.GeneralConfig;
import xyz.auriium.opentutorial.core.config.types.messages.MessageConfSerializer;
import xyz.auriium.opentutorial.core.config.types.messages.MessageConfig;
import xyz.auriium.opentutorial.core.model.Colorer;

import java.nio.file.Path;

public class CommonConfigCentralizer implements ConfigCentralizer {

    private final ReloadableHelper<MessageConfig> messageConfig;
    private final ReloadableHelper<GeneralConfig> generalConfig;

    public CommonConfigCentralizer(ConfigExceptionHandler handler, Colorer colorer, Path directory) {
        this.messageConfig = new ReloadableHelper<>(
                MessageConfig.class,
                directory,
                "messages.yml",
                handler,
                new ConfigurationOptions.Builder().addSerialiser(new MessageConfSerializer(colorer)).build()
        );


        this.generalConfig = new ReloadableHelper<>(
                GeneralConfig.class,
                directory,
                "general.yml",
                handler,
                ConfigurationOptions.defaults()
        );
    }

    @Override
    public ConfigHolder<MessageConfig> getMessageConfig() {
        return messageConfig;
    }

    @Override
    public ConfigHolder<GeneralConfig> getGeneralConfig() {
        return generalConfig;
    }

    @Override
    public void startup() {
        messageConfig.reload();
        generalConfig.reload();
    }

    @Override
    public void reload() {
        this.startup();
    }

    @Override
    public void shutdown() {
        //no-ops
    }
}