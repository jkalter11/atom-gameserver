package model.entities;

import model.GameField;
import model.Player;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Collections;
import java.util.List;

/**
 * Created by xakep666 on 05.10.16.
 *
 * Describes player controlled cell behavior
 */
public class CellEntity extends GameEntity implements SlowDownMoving, EvenlyMoving, Interacting, Splittable {
    private Player owner;
    public static double minRadius = 10;
    public static double maxRadius = Math.min(GameField.SIZE_X,GameField.SIZE_Y)/2-10;

    public CellEntity(@NotNull Point2D.Double centerCoordinate, double radius,
                      @NotNull Color color,@NotNull Player owner, @NotNull GameField gameField) {
        super(radius, color, centerCoordinate, gameField);
        this.owner=owner;
    }

    public Player getOwner() {
        return owner;
    }

    public double getMinRadius() {
        return minRadius;
    }

    public double getMaxRadius() {
        return maxRadius;
    }

    public void interact(@NotNull GameEntity entity) {
        //TODO: implement interaction logic
    }

    public void evenlyMoveTo(@NotNull Point2D.Double dest) {
        //TODO: implement movement logic
    }

    public void slowDownMoveTo(@NotNull Point2D.Double dest) {
        //TODO: implement movement logic
    }

    public List<CellEntity> split(int children) {
        //TODO: implement splitting logic
        return Collections.EMPTY_LIST;
    }

}
