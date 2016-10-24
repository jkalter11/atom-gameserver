package model.database;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;
import java.util.UUID;

/**
 * Created by xakep666 on 23.10.16.
 *
 * Token is a unique identifier of user
 */
public class Token {
    public static final Duration LIFE_TIME = Duration.ofHours(2);
    @NotNull
    private UUID token;
    /**
     * Generates new random token
     */
    public Token() {
        token = UUID.randomUUID();
    }

    private Token(@NotNull UUID token) {
        this.token = token;
    }

    /**
     * Create token from string
     * @param rawToken string to parse
     * @return Token object if parse was successful, null otherwise
     */
    @Nullable
    public static Token parse(@NotNull String rawToken) {
        try {
            UUID token = UUID.fromString(rawToken);
            return new Token(token);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }


    @Override
    public boolean equals(Object o) {
        return (o==this) || (o instanceof Token)
                && ((Token)o).token.equals(this.token);
    }

    @Override
    public int hashCode() {
        return token.hashCode();
    }

    @Override
    public String toString() {return token.toString();}
}
