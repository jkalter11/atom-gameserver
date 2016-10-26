package model.entities;

import model.GameField;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xakep666 on 05.10.16.
 *
 * Describes green bush behavior
 */
public class BushEntity extends GameEntity implements Splittable,SlowDownMoving {
    public static final double MAX_MASS = GameEntity.MIN_MASS*4;

    private Point2D.Double dest;
    private static final double INITIAL_SPEED = 10;
    private double speed = INITIAL_SPEED;
    private static final double ACCELERATION = -2;
    static {
        assert(MAX_MASS<=GameEntity.MAX_MASS);
    }

    public BushEntity(@NotNull Point2D.Double centerCoordinate, double mass, @NotNull GameField gameField) {
        super(mass, Color.GREEN, centerCoordinate,gameField);
    }

    @Override
    @NotNull
    public List<BushEntity> split(int children) {
        List<BushEntity> ret = new ArrayList<>(children);
        //TODO: implement splitting logic
        return ret;
    }

    @Override
    public void slowDownMoveTo(@NotNull Point2D.Double dest) {
        this.dest=dest;
    }

    @Override
    public double getMinMass() {return MIN_MASS;}
    @Override
    public double getMaxMass() {return MAX_MASS;}

    @Override
    public void tickMove() {
        //decrease speed
        speed+=ACCELERATION/GameField.TICKS_PER_SECOND;
        if (speed<=0) return;
        double totalMovement = dest.distance(getCenterCoordinate());
        double tickMovement = speed/GameField.TICKS_PER_SECOND;
        double angle = Math.acos((dest.x-getCenterCoordinate().x)/totalMovement);
        //check if we can move (new coordinates inside field)
        if (getCenterCoordinate().x+getRadius()+tickMovement>GameField.SIZE_X ||
                getCenterCoordinate().x-getRadius()+tickMovement<0) return;
        if (getCenterCoordinate().y+getRadius()+tickMovement>GameField.SIZE_Y ||
                getCenterCoordinate().y-getRadius()+tickMovement<0) return;
        //calculate new center coordinate
        getCenterCoordinate().x+=tickMovement*Math.cos(angle);
        getCenterCoordinate().y+=tickMovement*Math.sin(angle);
    }
}
