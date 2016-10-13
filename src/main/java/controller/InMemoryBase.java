package controller;
import model.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

/**
 * Created by xakep666 on 12.10.16.
 *
 * DataBase uses memory to keep user data
 */
public class InMemoryBase implements UsersBase{
    @NotNull
    private String digestAlg = "sha-1";

    @NotNull
    private Logger log = LogManager.getLogger(InMemoryBase.class);

    /**
     * UserName-Password table
     */
    @NotNull
    private Map<String,byte[]> userpass = new TreeMap<>();

    /**
     * Token-UserName table
     */
    @NotNull
    private Map<UUID, String> tokensOwned = new TreeMap<>();

    /**
     * UserName-Token table
     */
    @NotNull
    private Map<String, UUID> tokensOwnedReversed = new TreeMap<>();

    /**
     * Token-CreationDate table
     */
    @NotNull
    private Map<UUID, Date> tokensTimed = new TreeMap<>();

    public InMemoryBase() {
        log.info("Created in-memory database, using algorithm "+digestAlg);
    }

    public boolean register(@NotNull String username, @NotNull String password) {
        if (userpass.containsKey(username)) return false;
        MessageDigest md;
        try {
            md = MessageDigest.getInstance(digestAlg);
            md.update(password.getBytes());
        } catch (NoSuchAlgorithmException e) {
            log.error("Cannot calculate password hash: "+e);
            return false;
        }
        userpass.put(username,md.digest());
        log.info("User \""+username+"\" registered");
        return true;
    }

    @Nullable
    @Override
    public UUID requestToken(@NotNull String username, @NotNull String password) {
        if (!userpass.containsKey(username)) return null;
        MessageDigest md;
        try {
            md = MessageDigest.getInstance(digestAlg);
            md.update(password.getBytes());
        } catch (NoSuchAlgorithmException e) {
            log.error("Cannot calculate password hash: "+e);
            return null;
        }
        UUID foundToken = tokensOwnedReversed.get(username);
        if(foundToken!=null && isValidToken(foundToken)) return foundToken;
        if(MessageDigest.isEqual(md.digest(),userpass.get(username))) {
            UUID newToken = UUID.randomUUID();
            tokensTimed.put(newToken, new Date());
            tokensOwned.put(newToken, username);
            tokensOwnedReversed.put(username, newToken);
            return newToken;
        }
        return null;
    }

    @Override
    public boolean isValidToken(@NotNull UUID token) {
        if (!tokensTimed.containsKey(token) || !tokensOwned.containsKey(token)
                || !tokensOwnedReversed.containsValue(token)) return false;

        Date now = new Date();
        return Math.abs(now.getTime()-tokensTimed.get(token).getTime())<tokenLifeTime.toMillis();
    }

    @Override
    public void logout(@NotNull UUID token) {
        tokensOwnedReversed.remove(tokensOwned.get(token));
        tokensOwned.remove(token);
        tokensTimed.remove(token);
    }

    @Override
    public boolean changePassword(@NotNull String username, @NotNull String oldpwd,
                            @NotNull String newpwd, @NotNull UUID token) {
        if (!isValidToken(token) || !tokensOwned.get(token).equals(username)
                || !userpass.containsKey(username)) return false;
        MessageDigest md;
        try {
            md = MessageDigest.getInstance(digestAlg);
        } catch (NoSuchAlgorithmException e) {
            log.error("Cannot calculate password hash: "+e);
            return false;
        }
        md.update(oldpwd.getBytes());
        if (!MessageDigest.isEqual(md.digest(),userpass.get(username))) return false;
        md.update(newpwd.getBytes());
        userpass.put(username,md.digest());
        return true;
    }

    @Override
    @Nullable
    public Player getPlayerByName(@NotNull String name, @NotNull UUID token) {
        return userpass.containsKey(name) && isValidToken(token) ? new Player(name) : null;
    }

    @Override
    @Nullable
    public String getTokenOwner(@NotNull UUID token) {
        return tokensOwned.get(token);
    }
}
