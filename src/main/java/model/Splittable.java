package model;

import java.util.List;

/**
 * Created by xakep666 on 11.10.16.
 */
public interface Splittable {
    List<? extends GameEntity> split(int children);
}
