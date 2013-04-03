package fr.blemale.dropwizard.todo;

import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.auth.basic.BasicAuthProvider;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.jdbi.DBIFactory;
import fr.blemale.dropwizard.todo.auth.DummyAuthenticator;
import fr.blemale.dropwizard.todo.core.User;
import fr.blemale.dropwizard.todo.jdbi.JDBITodoDAO;
import fr.blemale.dropwizard.todo.jdbi.TodoDAO;
import fr.blemale.dropwizard.todo.resources.TodoResource;
import org.skife.jdbi.v2.DBI;

public class TodoService extends Service<TodoConfiguration> {
    public static void main(String[] args) throws Exception {
        new TodoService().run(args);
    }

    @Override
    public void initialize(Bootstrap<TodoConfiguration> bootstrap) {
        bootstrap.setName("todo");
    }

    @Override
    public void run(TodoConfiguration configuration, Environment environment) throws Exception {
        final String password = configuration.getPassword();
        environment.addProvider(new BasicAuthProvider<User>(new DummyAuthenticator(password), "Protect Area"));

        final DBIFactory factory = new DBIFactory();
        final DBI jdbi = factory.build(environment, configuration.getDatabaseConfiguration(), "postgresql");
        final TodoDAO todoDAO = jdbi.onDemand(JDBITodoDAO.class);
        environment.addResource(new TodoResource(todoDAO));
    }
}
