package model.entities;

import model.GameField;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Collections;

/**
 * Created by xakep666 on 05.10.16.
 *
 * Describes green bush behavior
 */
public class BushEntity extends GameEntity implements Splittable {
    public static final double minRadius = 10;
    public static final double maxRadius = 20;

    public BushEntity(@NotNull Point2D.Double centerCoordinate, double radius, @NotNull GameField gameField) {
        super(radius, Color.GREEN, centerCoordinate,gameField);
    }

    public double getMinRadius() {
        return minRadius;
    }

    public double getMaxRadius() {
        return maxRadius;
    }

    public java.util.List<CellEntity> split(int children) {
        //TODO: implement splitting logic
        return Collections.EMPTY_LIST;
    }
}
