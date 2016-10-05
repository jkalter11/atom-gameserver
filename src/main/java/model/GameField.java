package model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by xakep666 on 05.10.16.
 *
 * Provides a game field
 */
public class GameField {
    public static double SIZE_X = 500;
    public static double SIZE_Y = 500;
    public static Logger log = LogManager.getLogger(GameField.class);

    public void spawnEntity(Class<? extends GameEntity> entitykind) {
        GameEntity entity;
        try {
            entity = entitykind.newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            log.error(String.format("Could not spawn entity \"%s\" because of %s",entitykind.getName(),e));
        }

    }
}
