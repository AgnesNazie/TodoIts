package se.Lexicon.AppConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import se.Lexicon.DAOs.AppUserDAO;
import se.Lexicon.DAOs.Impl.AppUserDAOCollection;
import se.Lexicon.DAOs.Impl.PersonDAOCollection;
import se.Lexicon.DAOs.Impl.TodoItemDAOCollection;
import se.Lexicon.DAOs.Impl.TodoItemTaskDAOCollection;
import se.Lexicon.DAOs.PersonDAO;
import se.Lexicon.DAOs.TodoItemDAO;
import se.Lexicon.DAOs.TodoItemTaskDAO;
import se.Lexicon.Services.AppUserService;
import se.Lexicon.Services.PersonService;
import se.Lexicon.Services.TodoItemService;
import se.Lexicon.Services.TodoItemTaskService;

@Configuration
@ComponentScan(basePackages = "se.Lexicon.*")
public class AppConfig {
}