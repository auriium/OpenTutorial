package xyz.auriium.opentutorial.core.stage.age;

import xyz.auriium.opentutorial.core.event.chat.PlatformlessChatEvent;
import xyz.auriium.opentutorial.core.platform.base.TeachableRegistry;
import xyz.auriium.opentutorial.core.config.messages.MessageConfig;
import xyz.auriium.opentutorial.core.config.templates.impl.Interpret;
import xyz.auriium.opentutorial.core.platform.base.Teachable;
import xyz.auriium.opentutorial.core.platform.base.Scheduler;
import xyz.auriium.opentutorial.core.tutorial.Tutorial;
import xyz.auriium.opentutorial.core.tutorial.stage.AbstractDelayConsumer;

import java.util.Optional;

public class AgeStageConsumer extends AbstractDelayConsumer<AgeStage, PlatformlessChatEvent> {

    public AgeStageConsumer(Scheduler scheduler, TeachableRegistry registry, MessageConfig config) {
        super(scheduler, registry, config);
    }

    @Override
    public Class<AgeStage> stageClass() {
        return AgeStage.class;
    }

    @Override
    public void consume(AgeStage stage, PlatformlessChatEvent event, Tutorial tutorial) {
        String message = event.getMessage().replaceAll("\\D+","");
        Optional<Teachable> sender = registry.getAudienceByUUID(tutorial.getIdentifier());

        try {
            int age = Integer.parseInt(message);

            sender.ifPresent(player -> {
                if (age < stage.getBelowAge()) {
                    Interpret.ifStringPresent(stage.getRunOnFail(), cmd ->
                            player.runConsole(cmd.replaceAll("%PLAYER%",player.getName()))
                    );

                    if (stage.isCancelOnFail()) {
                        tutorial.fireCancel();
                    }
                }
            });

        } catch (NumberFormatException e) {
            sender.ifPresent(player -> config.notNumberMessage().send(player));
        }

        tutorial.fireNext();
    }

    @Override
    public Class<PlatformlessChatEvent> eventClass() {
        return PlatformlessChatEvent.class;
    }
}