package me.aurium.opentutorial.centralized.registry;

import me.aurium.opentutorial.centralized.NoConsumerException;
import me.aurium.opentutorial.centralized.Tutorial;
import me.aurium.opentutorial.stage.ConsumerPackage;
import me.aurium.opentutorial.stage.ConsumerSerializer;
import me.aurium.opentutorial.stage.Stage;
import me.aurium.opentutorial.stage.StageConsumer;
import me.aurium.opentutorial.stage.await.AwaitConsumer;

import java.util.*;

public class CommonRegistry implements ConsumerRegistry {

    private final Map<Class<? extends Stage>, StageConsumer<? extends Stage>> consumers = new HashMap<>();
    private final Map<String, ConsumerSerializer<?>> serializers = new HashMap<>();

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
    public void closeAll() {
        for (StageConsumer<? extends Stage> consumer : consumers.values()) {
            consumer.closeAll();
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
    public Optional<ConsumerSerializer<?>> getSerializer(String identifier) {
        return Optional.ofNullable(serializers.get(identifier));
    }

    @Override
    public <T extends Event, E extends Stage> void register(StageConsumer<E> stageConsumer, ConsumerSerializer<E> serializer) {

        consumers.put(stageConsumer.stageClass(),stageConsumer);
        serializers.put(serializer.identifier(),serializer);

        if (stageConsumer instanceof AwaitConsumer) {
            AwaitConsumer<E,T> consumer = (AwaitConsumer<E, T>) stageConsumer;

            bus.register(consumer.eventClass(),consumer);
        }


    }
}