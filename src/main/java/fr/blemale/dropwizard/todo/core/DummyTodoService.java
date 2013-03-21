package fr.blemale.dropwizard.todo.core;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import fr.blemale.dropwizard.todo.api.todo.external.ExternalTodo;
import fr.blemale.dropwizard.todo.api.todo.external.ExternalTodoLight;
import fr.blemale.dropwizard.todo.api.todo.external.ExternalTodoList;
import fr.blemale.dropwizard.todo.api.todo.request.TodoCreationRequest;
import fr.blemale.dropwizard.todo.api.todo.request.TodoUpdateRequest;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class DummyTodoService implements TodoService {
    private final Map<Long, Todo> todoStore = Maps.newConcurrentMap();
    private final AtomicLong currentId = new AtomicLong(0L);

    @Override
    public ExternalTodoLight createTodo(TodoCreationRequest todoCreationRequest) {
        Todo newTodo =
                new Todo(currentId.getAndIncrement(),
                        todoCreationRequest.getTitle(),
                        Optional.fromNullable(todoCreationRequest.getContent()));
        todoStore.put(newTodo.getId(), newTodo);
        return new ExternalTodoLight.Mapper().fromTodo(newTodo);
    }

    @Override
    public ExternalTodoLight updateTodo(long id, TodoUpdateRequest todoUpdateRequest) {
        todoStore.remove(id);
        Todo updatedTodo =
                new Todo(id,
                        todoUpdateRequest.getTitle(),
                        Optional.fromNullable(todoUpdateRequest.getContent()));
        todoStore.put(updatedTodo.getId(), updatedTodo);
        return new ExternalTodoLight.Mapper().fromTodo(updatedTodo);
    }

    @Override
    public ExternalTodoList getTodos() {
        List<ExternalTodoLight> todos = Lists.newArrayList();
        for (Todo todo : this.todoStore.values()) {
            todos.add(new ExternalTodoLight.Mapper().fromTodo(todo));
        }
        return new ExternalTodoList(todos);
    }

    @Override
    public Optional<ExternalTodo> getTodo(long id) {
        Optional<Todo> optionalTodo = Optional.fromNullable(this.todoStore.get(id));
        if (optionalTodo.isPresent()) {
            Todo todo = optionalTodo.get();
            return Optional.of(new ExternalTodo.Mapper().fromTodo(todo));
        } else {
            return Optional.absent();
        }
    }
}
