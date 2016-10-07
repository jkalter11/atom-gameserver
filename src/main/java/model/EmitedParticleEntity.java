package model;

import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Created by xakep666 on 05.10.16.
 *
 * Describes small particles emited by player controlled cell
 */
public class EmitedParticleEntity extends GameEntity implements SlowDownMoving, Interacting {
    private static double radius = 10;

    public EmitedParticleEntity(@NotNull Point2D.Double centerCoordinate,
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

    public void slowDownMoveTo(@NotNull Point2D.Double dest) {
        //TODO: implement movement logic
    }

    public void interact(@NotNull GameEntity entity) {
        //TODO: implement interraction logic
    }
}
