package model;

import org.jetbrains.annotations.NotNull;
import java.util.List;

/**
 * Created by xakep666 on 05.10.16.
 *
 * Logic of interaction with other entity
 */
public interface IInterracting {
    /**
     * Method defines logic of interaction between two entities
     * @param entity entity which interact
     */
    void interact(@NotNull GameEntity entity);
}
