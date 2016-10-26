package model.entities;

import java.awt.geom.Point2D;

/**
 * Created by xakep666 on 05.10.16.
 *
 * Physics of objects which velocity slows down to zero
 */
interface SlowDownMoving extends Moving{
    /**
     * Move object to provided coordinate with slowing down (negative acceleration)
     * @param dest target point
     */
    void slowDownMoveTo(Point2D.Double dest);
}
