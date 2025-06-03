package se.Lexicon.Services;

import org.springframework.stereotype.Component;
import se.Lexicon.DAOs.AppUserDAO;
import se.Lexicon.models.AppUser;

import java.util.Collection;

@Component
public class AppUserService {
    private final AppUserDAO appUserDAO;

    public AppUserService(AppUserDAO appUserDAO) {
        this.appUserDAO = appUserDAO;
    }

    public AppUser createUser(AppUser user) {
        return appUserDAO.persist(user);
    }

    public AppUser findByUsername(String username) {
        return appUserDAO.findByUsername(username);
    }

    public Collection<AppUser> findAllUsers() {
        return appUserDAO.findAll();
    }

    public void removeUser(String username) {
        appUserDAO.remove(username);
    }
}
