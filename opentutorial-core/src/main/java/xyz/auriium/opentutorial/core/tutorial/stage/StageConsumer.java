package xyz.auriium.opentutorial.core.tutorial.stage;

import xyz.auriium.beetle.utility.aspect.UUIDCloseable;
import xyz.auriium.opentutorial.core.tutorial.Tutorial;

public interface StageConsumer<T extends Stage> extends UUIDCloseable {

    void started(T options, Tutorial continuable);

    Class<T> stageClass();

}