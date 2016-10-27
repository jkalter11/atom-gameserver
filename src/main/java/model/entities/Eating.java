package model.entities;

/**
 * Created by xakep666 on 27.10.16.
 *
 * Physics of objects which can consume other entities
 */
public interface Eating {
    /**
     * Consume other entity to increase mass
     * @param e entity to consume
     */
    void eat(GameEntity e);
}
