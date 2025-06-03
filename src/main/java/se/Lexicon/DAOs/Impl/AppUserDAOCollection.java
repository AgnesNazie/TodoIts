package se.Lexicon.DAOs.Impl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import se.Lexicon.DAOs.AppUserDAO;
import se.Lexicon.Exception.AppuserException.AppUserNotFoundException;
import se.Lexicon.Exception.AppuserException.InvalidAppUserException;
import se.Lexicon.Exception.AppuserException.UserNameAlreadyExistsException;
import se.Lexicon.models.AppUser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component(value = "AppUserDAO")
public class AppUserDAOCollection implements AppUserDAO {
    //create a list to store app user object
    private final List<AppUser> users = new ArrayList<>();


    @Override
    public AppUser persist(AppUser appUser) {
        if (appUser == null || appUser.getUsername() == null || appUser.getUsername().trim().isEmpty()) {
            throw new InvalidAppUserException("AppUser is invalid: username must not be null or empty.");
        }
        boolean exists = users.stream()
                .anyMatch(u -> u.getUsername().equalsIgnoreCase(appUser.getUsername()));
        if (exists) {
            throw new UserNameAlreadyExistsException("Username already exists" + appUser.getUsername());

        }
        users.add(appUser);
        return appUser;
    }

    @Override
    public AppUser findByUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new InvalidAppUserException("Username must not be null or empty.");
        }

        return users.stream()
                .filter(u -> u.getUsername().equalsIgnoreCase(username))
                .findFirst()
                .orElseThrow(() ->
                        new AppUserNotFoundException("AppUser not found with username:" + username));
    }

    @Override
    public Collection<AppUser> findAll() {
        return new ArrayList<>(users);
    }

    @Override
    public void remove(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new InvalidAppUserException("Username must not be null or empty.");
        }
        AppUser user = findByUsername(username);
        users.remove(user);

    }
}
