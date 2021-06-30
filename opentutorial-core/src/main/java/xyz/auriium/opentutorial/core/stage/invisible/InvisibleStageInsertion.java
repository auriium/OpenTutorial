package xyz.auriium.opentutorial.core.stage.invisible;

import space.arim.dazzleconf.error.BadValueException;
import space.arim.dazzleconf.serialiser.FlexibleType;
import xyz.auriium.opentutorial.core.config.ConfigController;
import xyz.auriium.opentutorial.core.config.templates.util.Interpret;
import xyz.auriium.opentutorial.core.platform.Platform;
import xyz.auriium.opentutorial.core.tutorial.stage.StageInsertion;
import xyz.auriium.opentutorial.core.tutorial.stage.StageConsumer;

import java.util.Map;

public class InvisibleStageInsertion implements StageInsertion {

    InvisibleStageInsertion() {}

    public static InvisibleStageInsertion INIT = new InvisibleStageInsertion();

    @Override
    public StageConsumer<?> build(Platform<?> platform, ConfigController configController) {
        return new InvisibleStageConsumer(platform.userRegistry());
    }

    @Override
    public String identifier() {
        return "invisible";
    }

    @Override
    public InvisibleStage deserialize(Map<String, FlexibleType> map) throws BadValueException {

        boolean enabled = Interpret.getRequired("is_invisible", map, FlexibleType::getBoolean);

        return new InvisibleStage(enabled);
    }
}
