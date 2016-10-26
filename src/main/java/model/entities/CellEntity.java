package model.entities;

import model.GameField;
import model.Player;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xakep666 on 05.10.16.
 *
 * Describes player controlled cell behavior
 */
public class CellEntity extends GameEntity implements SlowDownMoving, EvenlyMoving, Splittable {
    private Player owner;

    public CellEntity(@NotNull Point2D.Double centerCoordinate, double radius,
                      @NotNull Color color,@NotNull Player owner, @NotNull GameField gameField) {
        super(radius, color, centerCoordinate, gameField);
        this.owner=owner;
    }

    public Player getOwner() {
        return owner;
    }

    /**
     * Consume other entity to increase mass
     * @param entity entity to consume
     */
    public void eat(@NotNull GameEntity entity) {
        setMass(getMass()+entity.getMass());
    }

    @Override
    public void evenlyMoveTo(@NotNull Point2D.Double dest) {
        //TODO: implement movement logic
    }

    @Override
    public void slowDownMoveTo(@NotNull Point2D.Double dest) {
        //TODO: implement movement logic
    }

    @NotNull
    @Override
    public List<CellEntity> split(int children) {
        List<CellEntity> ret = new ArrayList<>(children);
        double newMass = getMass()/(double)children;
        if (newMass<MIN_MASS) return ret;
        /*for(int i=0;i<children;i++) {
            Point2D.Double newCenter = new Point2D.Double(

            );
            ret.add(new CellEntity());
        }*/
        return ret;
    }

}
