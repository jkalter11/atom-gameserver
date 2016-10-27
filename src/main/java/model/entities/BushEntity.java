package model.entities;

import model.GameConstants;
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
public class BushEntity extends GameEntity implements Splittable,SlowDownMoving, Eating {
    public static final double MAX_MASS = GameEntity.MIN_MASS*4;

    private Point2D.Double dest;
    private double speed = 0;

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
        this.speed= GameConstants.INITIAL_SPEED;
        this.dest=dest;
    }

    @Override
    public double getMinMass() {return MIN_MASS;}
    @Override
    public double getMaxMass() {return MAX_MASS;}

    @Override
    public void tickMove() {
        //decrease speed
        speed+= GameConstants.ACCELERATION/ GameConstants.TICKS_PER_SECOND;
        if (speed<=0) return;
        double totalMovement = dest.distance(getCenterCoordinate());
        double tickMovement = speed/ GameConstants.TICKS_PER_SECOND;
        double angle = Math.acos((dest.x-getCenterCoordinate().x)/totalMovement);
        //check if we can move (new coordinates inside field)
        if (getCenterCoordinate().x+getRadius()+tickMovement> GameConstants.SIZE_X ||
                getCenterCoordinate().x-getRadius()+tickMovement<0) return;
        if (getCenterCoordinate().y+getRadius()+tickMovement> GameConstants.SIZE_Y ||
                getCenterCoordinate().y-getRadius()+tickMovement<0) return;
        //calculate new center coordinate
        getCenterCoordinate().x+=tickMovement*Math.cos(angle);
        getCenterCoordinate().y+=tickMovement*Math.sin(angle);
    }

    @Override
    public void eat(@NotNull GameEntity entity) {
        setMass(getMass()+entity.getMass());
    }
}
