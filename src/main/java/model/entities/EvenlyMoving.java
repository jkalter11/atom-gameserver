package model.entities;

import java.awt.geom.Point2D;

/**
 * Created by xakep666 on 05.10.16.
 *
 * Physics of evenly moving objects
 */
interface EvenlyMoving extends Moving{
    /**
     * Move object to provided coordinate with constant velocity
     * @param dest target point
     */
    void evenlyMoveTo(Point2D.Double dest);
}
