package fr.blemale.dropwizard.todo;

import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import fr.blemale.dropwizard.todo.jdbi.DummyTodoDAO;
import fr.blemale.dropwizard.todo.jdbi.TodoDAO;
import fr.blemale.dropwizard.todo.resources.TodoResource;

public class TodoService extends Service<TodoConfiguration> {

    @Override
    public void initialize(Bootstrap<TodoConfiguration> bootstrap) {
        bootstrap.setName("todo");
    }

    @Override
    public void run(TodoConfiguration configuration, Environment environment) throws Exception {
        final TodoDAO todoDAO = new DummyTodoDAO();
        environment.addResource(new TodoResource(todoDAO));
    }
}
