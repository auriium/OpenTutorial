package xyz.auriium.opentutorial.core.insertion;

import xyz.auriium.opentutorial.core.stage.age.AgeStageInsertion;
import xyz.auriium.opentutorial.core.stage.chat.ChatStageInsertion;
import xyz.auriium.opentutorial.core.stage.chat.SuppressStageInsertion;
import xyz.auriium.opentutorial.core.stage.clickblock.ClickBlockInsertion;
import xyz.auriium.opentutorial.core.stage.command.CommandStageInsertion;
import xyz.auriium.opentutorial.core.stage.delay.DelayStageInsertion;
import xyz.auriium.opentutorial.core.stage.invisible.InvisibleStageInsertion;
import xyz.auriium.opentutorial.core.stage.lock.LockableStageInsertion;
import xyz.auriium.opentutorial.core.stage.plainkeyword.PlainKeywordInsertion;
import xyz.auriium.opentutorial.core.stage.playsound.SoundStageInsertion;
import xyz.auriium.opentutorial.core.stage.teleport.TeleportStageInsertion;
import xyz.auriium.opentutorial.core.tutorial.stage.StageInsertion;

import java.util.*;

public class CommonInsertionRegistry implements InsertionRegistry{

    private final Map<String, StageInsertion> serializers = new HashMap<>();

    @Override
    public Collection<StageInsertion> getAllInsertions() {
        return serializers.values();
    }

    @Override
    public Optional<StageInsertion> getInsertion(String identifier) {
        return Optional.ofNullable(serializers.get(identifier));
    }

    @Override
    public InsertionRegistry register(StageInsertion insertion) {
        serializers.put(insertion.identifier(),insertion);

        return this;
    }

    public static InsertionRegistry defaults() {
        return new CommonInsertionRegistry()
                .register(AgeStageInsertion.INIT)
                .register(ChatStageInsertion.INIT)
                .register(ClickBlockInsertion.INIT)
                .register(CommandStageInsertion.INIT)
                .register(DelayStageInsertion.INIT)
                .register(InvisibleStageInsertion.INIT)
                .register(PlainKeywordInsertion.INIT)
                .register(SoundStageInsertion.INIT)
                .register(new LockableStageInsertion())
                .register(TeleportStageInsertion.INIT)
                .register(new SuppressStageInsertion());
    }
}
