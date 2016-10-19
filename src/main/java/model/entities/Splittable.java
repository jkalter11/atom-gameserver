package model.entities;

import java.util.List;

/**
 * Created by xakep666 on 11.10.16.
 *
 * Interface for entities which can split
 */
interface Splittable {
    List<? extends GameEntity> split(int children);
}
