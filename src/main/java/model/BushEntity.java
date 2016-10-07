package model;

import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Created by xakep666 on 05.10.16.
 *
 * Describes green bush behavior
 */
public class BushEntity extends GameEntity{
    public static final double minRadius = 10;
    public static final double maxRadius = 20;

    public BushEntity(@NotNull Point2D.Double centerCoordinate, double radius, @NotNull GameField gameField) {
        super(radius, Color.GREEN, centerCoordinate,gameField);
    }

    public double getMinRadius() {
        return minRadius;
    }

    public double getMaxRadius() {
        return maxRadius;
    }

    @Override
    public double getMass() { return 0;}
}
