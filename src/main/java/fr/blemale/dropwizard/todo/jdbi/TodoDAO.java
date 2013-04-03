package fr.blemale.dropwizard.todo.jdbi;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import fr.blemale.dropwizard.todo.core.Todo;

import java.util.List;

public interface TodoDAO {
    Long createTodo(Todo todo);

    int updateTodo(Todo todo);

    ImmutableList<Todo> getTodos();

    Optional<Todo> getTodo(long id);
}
