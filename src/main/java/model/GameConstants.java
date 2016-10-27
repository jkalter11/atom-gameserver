package model;

/**
 * Global static constants
 *
 * @author apomosov
 */
public interface GameConstants {
    int MAX_PLAYERS_IN_SESSION = 10;
    int MAX_PLAYERS_IN_SINGLE_SESSION = 1;
    double SIZE_X = 500;
    double SIZE_Y = 500;
    int TICKS_PER_SECOND = 20;
    double MAX_RAND_SPLIT_CHILDREN=10;
    double INITIAL_SPEED = 10;
    double ACCELERATION = -2;
}
