package controller;
import com.sun.org.apache.xml.internal.security.algorithms.MessageDigestAlgorithm;
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
    private String digestAlg = MessageDigestAlgorithm.ALGO_ID_DIGEST_SHA1;

    @NotNull
    private Logger log = LogManager.getLogger(InMemoryBase.class);

    /**
     * UserName-Password table
     */
    @NotNull
    private Map<String,MessageDigest> userpass = new TreeMap<>();

    /**
     * Token-UserName table
     */
    @NotNull
    private Map<UUID, String> tokensOwned = new TreeMap<>();

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
        userpass.put(username,md);
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
        if(md.equals(userpass.get(username))) {
            UUID newToken;
            do {
                newToken = UUID.randomUUID();
            } while (tokensTimed.containsKey(newToken) && tokensOwned.containsKey(newToken));
            tokensTimed.put(newToken, new Date());
            tokensOwned.put(newToken, username);
            return newToken;
        }
        return null;
    }

    @Override
    public boolean isValidToken(@NotNull UUID token) {
        if (!tokensTimed.containsKey(token) || !tokensOwned.containsKey(token)) return false;

        Date now = new Date();
        return Math.abs(now.getTime()-tokensTimed.get(token).getTime())<tokenLifeTime.toMillis();
    }

    @Override
    public void logout(@NotNull UUID token) {
        tokensOwned.remove(token);
        tokensTimed.remove(token);
    }

    @Override
    public boolean changePasssword(@NotNull String username, @NotNull String oldpwd,
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
        if (!userpass.get(username).equals(md)) return false;
        md.update(newpwd.getBytes());
        userpass.put(username,md);
        return true;
    }

    @Override
    @Nullable
    public Player getPlayerByName(@NotNull String name, @NotNull UUID token) {
        return userpass.containsKey(name) && isValidToken(token) ? new Player(name) : null;
    }
}
