package model.entities;

import model.GameConstants;
import model.GameField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Created by xakep666 on 05.10.16.
 *
 * Superclass for all game entities
 */
public abstract class GameEntity {
    private double mass;
    @NotNull
    private Color color;
    @NotNull
    private Point2D.Double centerCoordinate;

    private GameField gameField;
    private static Logger log = LogManager.getLogger(GameEntity.class);


    private static final double DENSITY = 2;

    public static double massToRadius(double mass) {
        return Math.sqrt(mass/ DENSITY /Math.PI);
    }
    public static double radiusToMass(double radius) {
        return DENSITY *Math.pow(radius,2)*Math.PI;
    }
    public static final double MIN_MASS = 10;
    public static final double MIN_RADIUS = massToRadius(MIN_MASS);
    public static final double MAX_RADIUS = Math.min(GameConstants.SIZE_X, GameConstants.SIZE_Y);
    public static final double MAX_MASS = radiusToMass(MAX_RADIUS);

    public double getMinMass() {return MIN_MASS;}
    public double getMinRadius() {return MIN_RADIUS;}
    public double getMaxRadius() {return MAX_RADIUS;}
    public double getMaxMass() {return MAX_MASS;}

    /**
     * Create new entity
     * @param mass mass of entity
     * @param color color of entity
     * @param centerCoordinate coordinates on game field
     */
    public GameEntity(double mass,
                      @NotNull Color color,
                      @NotNull Point2D.Double centerCoordinate,
                      @NotNull GameField gameField) {
        this.mass=mass;
        this.color=color;
        this.centerCoordinate=centerCoordinate;
        this.gameField=gameField;
        log.debug(String.format("Created entity \"%s\" on game field \"%s\" with parameters: (%s) ",
            this.getClass().getName(),
            gameField,
            this));
    }

    @Override
    public String toString() {
        return String.format("mass: %f, color: %s, centerCoordinate: \"%s\" ",
                mass,color,centerCoordinate);
    }

    @NotNull
    public Point2D.Double getCenterCoordinate() {
        return centerCoordinate;
    }

    public double getRadius() {
        return massToRadius(mass);
    }

    @NotNull
    public Color getColor() {
        return color;
    }

    /**
     * Sets up new center coordinate.
     * If new coordinate is not inside game field, does nothing
     * @param centerCoordinate new center coordinate
     */
    protected void setCenterCoordinate(@NotNull Point2D.Double centerCoordinate) {
        boolean rightOfLeftBorder = centerCoordinate.getX()>=getRadius();
        boolean lowerOfTopBorder = centerCoordinate.getY()<= GameConstants.SIZE_Y-getRadius();
        boolean leftOfRightBorder = centerCoordinate.getX()< GameConstants.SIZE_X-getRadius();
        boolean upperOfBottomBorder = centerCoordinate.getY()>=getRadius();
        if (rightOfLeftBorder && leftOfRightBorder && lowerOfTopBorder && upperOfBottomBorder)
            this.centerCoordinate=centerCoordinate;
    }

    /**
     * Sets up new radius.
     * If new radius less than minimal radius for entity or greater then maximal, does nothing
     * @param radius new radius
     */
    protected void setRadius(double radius) {
        if (radius>=getMinRadius() && radius<=getMaxRadius())
            mass=radiusToMass(radius);
    }

    /**
     * @return mass of entity
     */
    public double getMass() {
        return mass;
    }

    /**
     * Set up new mass of entity, does nothing if not between MIN_MASS and MAX_MASS
     * @param newMass a new mass value
     */
    public void setMass(double newMass) {
        if (newMass<=getMaxMass() && newMass>=getMinMass())
            mass=newMass;
    }

    public void destroy() {
        gameField=null;
    }
}
