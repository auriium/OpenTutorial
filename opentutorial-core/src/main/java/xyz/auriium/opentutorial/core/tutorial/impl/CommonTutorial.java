package xyz.auriium.opentutorial.core.tutorial.impl;

import xyz.auriium.opentutorial.api.construct.Tutorial;
import xyz.auriium.opentutorial.core.tutorial.TutorialController;
import xyz.auriium.opentutorial.api.construct.Stage;

import java.util.Queue;
import java.util.UUID;

/**
 * Simply just a template with a UUID attached
 */
public class CommonTutorial implements Tutorial {

    private final UUID uuid;
    private final Queue<Stage> stageQueue;
    private final TutorialController controller;

    public CommonTutorial(UUID uuid, Queue<Stage> stageQueue, TutorialController controller) {
        this.uuid = uuid;
        this.stageQueue = stageQueue;
        this.controller = controller;
    }

    @Override
    public UUID getIdentifier() {
        return uuid;
    }

    @Override
    public void fireNext() {

        if (stageQueue.isEmpty()) {
            controller.cancelByUUID(uuid);
            return;

        }

        controller.consumeStage(stageQueue.remove(), this);
    }

    @Override
    public void fireCancel() {
        controller.cancelByUUID(uuid);
    }
}
