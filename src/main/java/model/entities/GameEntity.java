package model.entities;

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
    private double radius;
    @NotNull
    private Color color;
    @NotNull
    private Point2D.Double centerCoordinate;
    @NotNull
    private GameField gameField;
    private static Logger log = LogManager.getLogger(GameEntity.class);

    private static double density = 2;

    /**
     * Create new entity
     * @param radius radius of entity
     * @param color color of entity
     * @param centerCoordinate coordinates on game field
     */
    public GameEntity(double radius,
                      @NotNull Color color,
                      @NotNull Point2D.Double centerCoordinate,
                      @NotNull GameField gameField) {
        this.radius=radius;
        this.color=color;
        this.centerCoordinate=centerCoordinate;
        this.gameField=gameField;

        if (log.isDebugEnabled()) {
            log.debug(String.format("Created entity \"%s\" on game field \"%s\" with parameters: (%s) ",
                    this.getClass().getName(),
                    gameField,
                    this));
        }
    }

    @Override
    public String toString() {
        return String.format("radius: %f, color: %s, centerCoordinate: \"%s\" ",
                radius,color,centerCoordinate);
    }

    @NotNull
    public Point2D.Double getCenterCoordinate() {
        return centerCoordinate;
    }

    public double getRadius() {
        return radius;
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
        boolean rightOfLeftBorder = centerCoordinate.getX()>=radius;
        boolean lowerOfTopBorder = centerCoordinate.getY()<=GameField.SIZE_Y-radius;
        boolean leftOfRightBorder = centerCoordinate.getX()<GameField.SIZE_X-radius;
        boolean upperOfBottomBorder = centerCoordinate.getY()>=radius;
        if (rightOfLeftBorder && leftOfRightBorder && lowerOfTopBorder && upperOfBottomBorder)
            this.centerCoordinate=centerCoordinate;
    }

    /**
     * @return minimal radius for entity
     */
    public abstract double getMinRadius();

    /**
     * @return maximal radius for entity
     */
    public abstract double getMaxRadius();

    /**
     * Sets up new radius.
     * If new radius less than minimal radius for entity or greater then maximal, does nothing
     * @param radius new radius
     */
    protected void setRadius(double radius) {
        if (radius>=getMinRadius() && radius<=getMaxRadius())
            this.radius=radius;
    }

    /**
     * Calculates and returns mass of entity.
     * @return mass of entity
     */
    public double getMass() {
        return Math.PI*radius*radius*density;
    }

    /**
     * Calculates and sets radius from new mass
     * @param newMass a new mass value
     */
    public void setMass(double newMass) {
        double newRadius = Math.sqrt(newMass/density/Math.PI);
        setRadius(newRadius);
    }

}
