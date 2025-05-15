package se.Lexicon.DAOs;

import se.Lexicon.models.AppUser;

import java.util.Collection;
import java.util.Collections;

public interface AppUserDAO {
    AppUser persist(AppUser appUser);

    AppUser findByUserName(AppUser username);

    Collection<AppUser> findAll();

    void remove(String username);


}
