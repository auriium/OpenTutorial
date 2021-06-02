package xyz.auriium.opentutorial.core.model;

/**
 * Denotates that something is cycleable and needs a certain order to startup with a corresponding bootstrap
 */
public interface Cycleable {

    void startup();
    void reload();
    void shutdown();

}
