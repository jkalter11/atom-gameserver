package model;

import java.awt.geom.Point2D;

/**
 * Created by xakep666 on 05.10.16.
 *
 * Physics of evenly moving objects
 */
public interface EvenlyMoving {
    /**
     * Move object to provided coordinate with constant velocity
     * @param dest target point
     */
    void evenlyMoveTo(Point2D.Double dest);
}
