package model;

import java.awt.geom.Point2D;
import java.awt.Color;
import java.util.List;
import java.util.Collections;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

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
    private Point2D centerCoordinate;
    @NotNull
    private GameField gameField;
    private static Logger log = LogManager.getLogger(GameEntity.class);

    /**
     * Create new entity
     * @param radius radius of entity
     * @param color color of entity
     * @param centerCoordinate coordinates on game field
     */
    public GameEntity(double radius,
                      @NotNull Color color,
                      @NotNull Point2D centerCoordinate,
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
    public Point2D getCenterCoordinate() {
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
     * Method defines logic of interaction between two entities
     * @param entity entity which interact
     */
    public abstract void interact(@NotNull GameEntity entity);

    /**
     * Sets up new center coordinate.
     * If new coordinate is not inside game field, does nothing
     * @param centerCoordinate new center coordinate
     */
    protected void setCenterCoordinate(@NotNull Point2D centerCoordinate) {
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
    protected abstract double getMinRadius();

    /**
     * Sets up new radius.
     * If new radius less than minimal radius for entity, does nothing
     * @param radius new radius
     */
    protected void setRadius(double radius) {
        if (radius>=getMinRadius())
            this.radius=radius;
    }

    /**
     * Method searches game entities which collides with this entity or covered by it
     * @return list of found entities
     */
    public List<GameEntity> findCollided() {
        //TODO: implement search of objects, which collides with this or covered by this
        return Collections.EMPTY_LIST;
    }

    /**
     * Unlink entity from game field.
     * Method needed for correct GC.
     */
    public void removeFromField() {
        this.gameField=null;
        this.centerCoordinate=null;
        this.color=null;
    }
}
