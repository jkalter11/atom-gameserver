package model;

import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Created by xakep666 on 05.10.16.
 *
 * Describes small food particle behavior
 */
public class FoodEntity extends GameEntity{
    private static double radius = 5;

    public FoodEntity(@NotNull Point2D.Double centerCoordinate,
                      double radius,
                      @NotNull Color color,
                      @NotNull GameField gameField) {
        super(radius, color, centerCoordinate, gameField);
    }

    public double getMinRadius() {
        return radius;
    }

    public double getMaxRadius() {
        return radius;
    }
}
