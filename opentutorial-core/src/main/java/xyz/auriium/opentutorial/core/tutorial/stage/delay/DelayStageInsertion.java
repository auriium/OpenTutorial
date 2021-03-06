package xyz.auriium.opentutorial.core.tutorial.stage.delay;

import space.arim.dazzleconf.error.BadValueException;
import space.arim.dazzleconf.serialiser.FlexibleType;
import xyz.auriium.opentutorial.core.config.ConfigController;
import xyz.auriium.opentutorial.core.config.templates.util.Interpret;
import xyz.auriium.opentutorial.core.platform.Platform;
import xyz.auriium.opentutorial.core.tutorial.stage.Identifiers;
import xyz.auriium.opentutorial.core.tutorial.stage.StageConsumer;
import xyz.auriium.opentutorial.core.tutorial.stage.StageInsertion;

import java.util.Map;

public class DelayStageInsertion implements StageInsertion {

    @Override
    public StageConsumer<?> build(Platform<?> platform, ConfigController configController) {
        return new DelayStageConsumer(platform.scheduler());
    }

    @Override
    public String identifier() {
        return "delay";
    }

    @Override
    public DelayStage deserialize(Map<String, FlexibleType> map) throws BadValueException {

        long delay = Interpret.getRequired(Identifiers.DELAY,map,FlexibleType::getLong);

        return new DelayStage(delay);
    }
}
