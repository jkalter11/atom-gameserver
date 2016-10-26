package model.entities;

/**
 * Created by xakep666 on 26.10.16.
 *
 * Physics of moving objects
 */
public interface Moving {

    /**
     * Method moves an entity in given direction per one tick
     */
    void tickMove();
}
