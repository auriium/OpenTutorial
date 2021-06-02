package xyz.auriium.opentutorial.centralized.registry;

import xyz.auriium.opentutorial.centralized.NoConsumerException;
import xyz.auriium.opentutorial.centralized.Tutorial;
import xyz.auriium.opentutorial.stage.Stage;
import xyz.auriium.opentutorial.stage.StageConsumer;
import xyz.auriium.opentutorial.stage.StageSerializer;
import xyz.auriium.opentutorial.stage.await.AwaitConsumer;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class CommonRegistry implements ConsumerRegistry {

    private final Map<Class<? extends Stage>, StageConsumer<? extends Stage>> consumers = new HashMap<>();
    private final Map<String, StageSerializer<?>> serializers = new HashMap<>();

    private final EventBus bus;

    public CommonRegistry(EventBus bus) {
        this.bus = bus;
    }

    @Override
    public void closeSingle(UUID uuid) {
        for (StageConsumer<? extends Stage> consumer : consumers.values()) {
            consumer.closeSingle(uuid);
        }
    }

    @Override
    public void close() {
        for (StageConsumer<? extends Stage> consumer : consumers.values()) {
            consumer.close();
        }
    }

    @Override
    public <T extends Stage> void consumeStage(T stage, Tutorial tutorial) {
        StageConsumer<T> consumer = (StageConsumer<T>) consumers.get(stage);

        if (consumer == null) throw new NoConsumerException("Consumer for stage of type " + stage.getClass() + " not found!");
        if (!consumer.stageClass().isInstance(stage)) throw new IllegalStateException("How did this happen? StageConsumer does not take type T!");

        consumer.started(stage, tutorial);
    }

    @Override
    public Optional<StageSerializer<?>> getSerializer(String identifier) {
        return Optional.ofNullable(serializers.get(identifier.toLowerCase()));
    }

    @Override
    public <T extends Event, E extends Stage> ConsumerRegistry register(StageConsumer<E> stageConsumer, StageSerializer<E> serializer) {

        consumers.put(stageConsumer.stageClass(),stageConsumer);
        serializers.put(serializer.identifier().toLowerCase(),serializer);

        if (stageConsumer instanceof AwaitConsumer) {
            AwaitConsumer<E,T> consumer = (AwaitConsumer<E, T>) stageConsumer;

            bus.register(consumer.eventClass(),consumer);
        }

        return this;
    }
}