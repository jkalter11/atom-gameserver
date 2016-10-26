package model.entities;

import model.GameField;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Created by xakep666 on 05.10.16.
 *
 * Describes small particles emited by player controlled cell
 */
public class EmitedParticleEntity extends GameEntity implements SlowDownMoving {
    public static final double MAX_MASS = 2*MIN_MASS;
    static {
        assert(MAX_MASS<=GameEntity.MAX_MASS);
    }

    public EmitedParticleEntity(@NotNull Point2D.Double centerCoordinate,
                                double mass,
                                @NotNull Color color,
                                @NotNull GameField gameField) {
        super(mass, color, centerCoordinate, gameField);
    }

    @Override
    public void slowDownMoveTo(@NotNull Point2D.Double dest) {
        //TODO: implement movement logic
    }

    @Override
    public double getMinMass() {return MIN_MASS;}
    @Override
    public double getMaxMass() {return MAX_MASS;}
}
