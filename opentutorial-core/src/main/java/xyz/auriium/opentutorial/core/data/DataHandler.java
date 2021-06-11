package xyz.auriium.opentutorial.core.data;

import xyz.auriium.opentutorial.core.model.Cycleable;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface DataHandler {

    CompletableFuture<Boolean> isPresent(String templateID, UUID userID);
    void insertPresent(String templateID, UUID userID);

}
