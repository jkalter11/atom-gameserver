package model;

import org.jetbrains.annotations.NotNull;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.*;

/**
 * Created by xakep666 on 05.10.16.
 *
 * Describes player controlled cell behavior
 */
public class CellEntity extends GameEntity implements ISlowDownMoving,IEvenlyMoving,IInterracting {
    private Player owner;
    private static double minradius = 10;
    private static double maxradius = Math.min(GameField.SIZE_X,GameField.SIZE_Y)/2-10;

    public CellEntity(@NotNull Point2D.Double centerCoordinate, double radius,
                      @NotNull Color color,@NotNull Player owner, @NotNull GameField gameField) {
        super(radius, color, centerCoordinate, gameField);
        this.owner=owner;
    }

    public Player getOwner() {
        return owner;
    }

    public double getMinRadius() {
        return minradius;
    }

    public double getMaxRadius() {
        return maxradius;
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

    public List<CellEntity> split(int numchild) {
        //TODO: implement splitting logic
        return Collections.EMPTY_LIST;
    }
}
