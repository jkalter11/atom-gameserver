package model;

import java.awt.geom.Point2D;

/**
 * Created by xakep666 on 05.10.16.
 *
 * Physics of objects which velocity slows down to zero
 */
public interface SlowDownMoving {
    /**
     * Move object to provided coordinate with slowing down (negative acceleration)
     * @param dest target point
     */
    void slowDownMoveTo(Point2D.Double dest);
}
