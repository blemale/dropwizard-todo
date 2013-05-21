package fr.blemale.dropwizard.todo;

import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.auth.basic.BasicAuthProvider;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.db.DatabaseConfiguration;
import com.yammer.dropwizard.migrations.MigrationsBundle;
import dagger.ObjectGraph;
import fr.blemale.dropwizard.todo.resources.TodoResource;

public class TodoService extends Service<TodoConfiguration> {
    public static void main(String[] args) throws Exception {
        new TodoService().run(args);
    }

    @Override
    public void initialize(Bootstrap<TodoConfiguration> bootstrap) {
        bootstrap.setName("todo");

        bootstrap.addBundle(new MigrationsBundle<TodoConfiguration>() {
            @Override
            public DatabaseConfiguration getDatabaseConfiguration(TodoConfiguration configuration) {
                return configuration.getDatabaseConfiguration();
            }
        });
    }

    @Override
    public void run(TodoConfiguration configuration, Environment environment) throws Exception {
        ObjectGraph objectGraph = ObjectGraph.create(new TodoModule(configuration, environment));
        environment.addProvider(objectGraph.get(BasicAuthProvider.class));
        environment.addResource(objectGraph.get(TodoResource.class));
    }
}
