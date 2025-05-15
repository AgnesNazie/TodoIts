package se.Lexicon.DAOs.Impl;

import se.Lexicon.DAOs.AppUserDAO;
import se.Lexicon.Exception.AppUserNotFoundException;
import se.Lexicon.Exception.UserNameAlreadyExistsException;
import se.Lexicon.models.AppUser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AppUserDAOCollection implements AppUserDAO {
    //create a list to store app user object
    private final List<AppUser> users = new ArrayList<>();


    @Override
    public AppUser persist(AppUser appUser) {
        boolean exists = users.stream()
                .allMatch(u -> u.getUsername().equalsIgnoreCase(appUser.getUsername()));
        if (exists) {
            throw new UserNameAlreadyExistsException("User Name already exists" + appUser.getUsername());

        }
        users.add(appUser);
        return appUser;
    }

    @Override
    public AppUser findByUsername(String username) {

        return users.stream()
                .filter(u -> u.getUsername().equalsIgnoreCase(username))
                .findFirst()
                .orElseThrow(()->
                     new AppUserNotFoundException("AppUser not found with username:" + username));
    }

    @Override
    public Collection<AppUser> findAll() {
        return new ArrayList<>(users);
    }

    @Override
    public void remove(String username) {
        AppUser user = findByUsername(username);
        users.remove(user);

    }
}
