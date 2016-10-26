package model.entities;

import model.GameField;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Created by xakep666 on 05.10.16.
 *
 * Describes small food particle behavior
 */
public class FoodEntity extends GameEntity{
    public static double MIN_MASS = GameEntity.MIN_MASS/2;
    public static double MAX_MASS = MIN_MASS;

    public FoodEntity(@NotNull Point2D.Double centerCoordinate,
                      @NotNull Color color,
                      @NotNull GameField gameField) {
        super(MIN_MASS, color, centerCoordinate, gameField);
    }

    @Override
    public double getMinMass() {return MIN_MASS;}
    @Override
    public double getMaxMass() {return MAX_MASS;}
}
