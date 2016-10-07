package model;

import org.jetbrains.annotations.NotNull;

/**
 * Created by xakep666 on 05.10.16.
 *
 * Logic of interaction with other entity
 */
public interface Interacting {
    /**
     * Method defines logic of interaction between two entities
     * @param entity entity which interact
     */
    void interact(@NotNull GameEntity entity);
}
