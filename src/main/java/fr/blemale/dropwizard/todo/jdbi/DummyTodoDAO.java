package fr.blemale.dropwizard.todo.jdbi;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import fr.blemale.dropwizard.todo.core.Todo;
import fr.blemale.dropwizard.todo.core.TodoBuilder;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class DummyTodoDAO implements TodoDAO {
    private final Map<Long, Todo> todoStore = Maps.newConcurrentMap();
    private final AtomicLong currentId = new AtomicLong(0L);

    @Override
    public Todo createTodo(Todo todo) {
        Todo newTodo = TodoBuilder.aTodo(todo).withId(currentId.getAndIncrement()).build();
        todoStore.put(newTodo.getId(), newTodo);
        return newTodo;
    }

    @Override
    public Optional<Todo> updateTodo(Todo todo) {
        Optional<Todo> currentTodo = Optional.fromNullable(todoStore.remove(todo.getId()));
        if (currentTodo.isPresent()) {
            todoStore.put(todo.getId(), todo);
            return Optional.of(todo);
        } else {
            return Optional.absent();
        }
    }

    @Override
    public List<Todo> getTodos() {
        return ImmutableList.copyOf(todoStore.values());
    }

    @Override
    public Optional<Todo> getTodo(long id) {
        return Optional.fromNullable(todoStore.get(id));
    }
}
