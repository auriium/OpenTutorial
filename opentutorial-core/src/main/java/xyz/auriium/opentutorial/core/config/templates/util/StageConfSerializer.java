package xyz.auriium.opentutorial.core.config.templates.util;

import space.arim.dazzleconf.error.BadValueException;
import space.arim.dazzleconf.serialiser.Decomposer;
import space.arim.dazzleconf.serialiser.FlexibleType;
import space.arim.dazzleconf.serialiser.ValueSerialiser;
import xyz.auriium.opentutorial.core.insertion.InsertionRegistry;
import xyz.auriium.opentutorial.core.tutorial.stage.StageInsertion;
import xyz.auriium.opentutorial.core.tutorial.Stage;

import java.util.Map;
import java.util.Optional;

public class StageConfSerializer implements ValueSerialiser<Stage> {

    private final InsertionRegistry serializerRegistry;

    public StageConfSerializer(InsertionRegistry serializerRegistry) {
        this.serializerRegistry = serializerRegistry;
    }

    @Override
    public Class<Stage> getTargetClass() {
        return Stage.class;
    }

    @Override
    public Stage deserialise(FlexibleType value) throws BadValueException {
        Map<String, FlexibleType> stageConfigMap = value.getMap((key, v) -> Map.entry(key.getString(), v));

        String type = Optional.ofNullable(stageConfigMap.get("type")).orElseThrow(() -> new BadValueException.Builder().key("type").message("Stage missing value for type! Add `type:` to the stage!")
                .build()).getString();

        StageInsertion serializer = serializerRegistry.getInsertion(type).orElseThrow(() -> new BadValueException.Builder().key(type).message("No type exists in OpenTutorial with that type identifier! Check your spelling and try again, or read the wiki!").build());

        return serializer.deserialize(stageConfigMap);
    }

    @Override
    public Object serialise(Stage stage, Decomposer decomposer) {


        throw new UnsupportedOperationException("Not implemented! (Yet!)");
    }
}
