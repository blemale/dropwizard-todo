package fr.blemale.dropwizard.todo;

import com.yammer.dropwizard.auth.Authenticator;
import com.yammer.dropwizard.auth.basic.BasicAuthProvider;
import com.yammer.dropwizard.auth.basic.BasicCredentials;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.jdbi.DBIFactory;
import dagger.Module;
import dagger.Provides;
import fr.blemale.dropwizard.todo.auth.DummyAuthenticator;
import fr.blemale.dropwizard.todo.core.User;
import fr.blemale.dropwizard.todo.jdbi.JDBITodoDAO;
import fr.blemale.dropwizard.todo.jdbi.TodoDAO;
import fr.blemale.dropwizard.todo.resources.TodoResource;
import org.skife.jdbi.v2.DBI;

import javax.inject.Singleton;

@Module(injects = {BasicAuthProvider.class, TodoResource.class}, library = true)
public class TodoModule {

    private final TodoConfiguration configuration;
    private final Environment environment;

    public TodoModule(TodoConfiguration configuration, Environment environment) {
        this.configuration = configuration;
        this.environment = environment;
    }

    @Provides
    @Singleton
    public TodoDAO provideTodoDAO() {
        try {
            final DBIFactory factory = new DBIFactory();
            final DBI jdbi = factory.build(this.environment, this.configuration.getDatabaseConfiguration(), "postgresql");
            return jdbi.onDemand(JDBITodoDAO.class);
        } catch (ClassNotFoundException cnfe) {
            throw new RuntimeException(cnfe);
        }
    }

    @Provides
    @Singleton
    public Authenticator<BasicCredentials, User> provideAuthenticator() {
        return new DummyAuthenticator(configuration.getPassword());
    }

    @Provides
    @Singleton
    public BasicAuthProvider provideBasicAuthProvider(Authenticator<BasicCredentials, User> authenticator) {
        return new BasicAuthProvider<User>(authenticator, "Protect Area");
    }
}
