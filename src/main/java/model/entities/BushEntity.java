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
    public void slowDownMoveTo(@NotNull Point2D.Double distance) {

    }

    @Override
    public double getMinMass() {return MIN_MASS;}
    @Override
    public double getMaxMass() {return MAX_MASS;}
}
