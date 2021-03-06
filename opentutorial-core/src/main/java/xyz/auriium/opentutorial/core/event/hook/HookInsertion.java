package xyz.auriium.opentutorial.core.event.hook;

import xyz.auriium.opentutorial.core.config.ConfigController;
import xyz.auriium.opentutorial.core.event.InnerEventConsumer;
import xyz.auriium.opentutorial.core.platform.Platform;
import xyz.auriium.opentutorial.core.tutorial.TutorialController;

public interface HookInsertion {

    InnerEventConsumer<?> build(Platform<?> platform, TutorialController tutorialController, ConfigController configController);

}
