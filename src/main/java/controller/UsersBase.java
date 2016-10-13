package controller;

import model.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;
import java.util.UUID;

/**
 * Created by xakep666 on 12.10.16.
 *
 * Provides an abstraction layer for databases
 */
public interface UsersBase {
    /**
     * Token`s life time. When expired, must be requested again with requestToken()
     */
    Duration tokenLifeTime = Duration.ofHours(2);

    /**
     * Generates access token for username (authorization with password)
     * @param username user`s name
     * @param password user`s password
     * @return null if password is invalid or user not registered, access token otherwise
     */
    @Nullable
    UUID requestToken(@NotNull String username, @NotNull String password);

    /**
     * Register user on server
     * @param username user`s name
     * @param password user`s password
     * @return true if registration successful, false otherwise
     */
    boolean register(@NotNull String username, @NotNull String password);

    /**
     * Change user password (token must belong to user and user must submit his password)
     * @param username user who wants change his pass
     * @param oldpwd user`s old password
     * @param newpwd user`s new password
     * @param token user`s access token
     * @return true if password changed, false otherwise
     */
    boolean changePassword(@NotNull String username, @NotNull String oldpwd,
                            @NotNull String newpwd, @NotNull UUID token);

    /**
     * Removes access token from valid tokens (logout user)
     * @param token token to remove
     */
    void logout(@NotNull UUID token);

    /**
     * Checks if token is valid (exists and not expired)
     * @param token token to check
     * @return true if valid, false otherwise
     */
    boolean isValidToken(@NotNull UUID token);

    /**
     * Finds user and creates Player object by user name
     * @param name user name to find
     * @param token user`s access token
     * @return Player object if found and token valid, null otherwise
     */
    @Nullable
    Player getPlayerByName(@NotNull String name, @NotNull UUID token);

    /**
     * Finds token owner
     * @param token token to search
     * @return username
     */
    @Nullable
    String getTokenOwner(@NotNull UUID token);
}
