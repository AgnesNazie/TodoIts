package se.Lexicon;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import se.Lexicon.AppConfig.AppConfig;
import se.Lexicon.DAOs.AppUserDAO;
import se.Lexicon.models.AppRole;
import se.Lexicon.models.AppUser;

public class Main {
    public static void main(String[] args) {
        // Create Spring container
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        // Get AppUserDAO bean
        AppUserDAO appUserDAO = context.getBean(AppUserDAO.class);

        // Test it!
        AppUser newUser = new AppUser("Agnes_Nazie", "password123", AppRole.ROLE_APP_USER);
        appUserDAO.persist(newUser);

        System.out.println("Saved user: " + appUserDAO.findByUsername("Agnes_Nazie"));

        // Close context
        context.close();
    }

}