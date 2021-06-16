package xyz.auriium.opentutorial.core.stage.plainkeyword;

import xyz.auriium.opentutorial.core.event.chat.PlatformlessChatEvent;
import xyz.auriium.opentutorial.core.platform.base.TeachableRegistry;
import xyz.auriium.opentutorial.core.config.ConfigHolder;
import xyz.auriium.opentutorial.core.config.messages.MessageConfig;
import xyz.auriium.opentutorial.core.platform.base.Scheduler;
import xyz.auriium.opentutorial.core.tutorial.Tutorial;
import xyz.auriium.opentutorial.core.tutorial.stage.AbstractDelayConsumer;

/**
 * Handles response stages by checking if they contain a keyword
 */
public class PlainKeywordStageConsumer extends AbstractDelayConsumer<PlainKeywordStage, PlatformlessChatEvent> {


    public PlainKeywordStageConsumer(Scheduler scheduler, TeachableRegistry registry, MessageConfig config) {
        super(scheduler, registry, config);
    }

    @Override
    public Class<PlainKeywordStage> stageClass() {
        return PlainKeywordStage.class;
    }

    @Override
    public void consume(PlainKeywordStage stage, PlatformlessChatEvent event, Tutorial tutorial) {
       String message = event.getMessage();

       for (String matchable : stage.getMatchables()) {
           if (message.toLowerCase().contains(matchable.toLowerCase())) {
               tutorial.fireNext();
               return;
           }
       }

       if (stage.isCancelOnFail()) {
           tutorial.fireCancel();
       }
    }

    @Override
    public Class<PlatformlessChatEvent> eventClass() {
        return PlatformlessChatEvent.class;
    }

}