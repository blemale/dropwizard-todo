package fr.blemale.dropwizard.todo.core;

import com.google.common.base.Optional;
import fr.blemale.dropwizard.todo.api.todo.external.ExternalTodo;
import fr.blemale.dropwizard.todo.api.todo.external.ExternalTodoLight;
import fr.blemale.dropwizard.todo.api.todo.external.ExternalTodoList;
import fr.blemale.dropwizard.todo.api.todo.request.TodoCreationRequest;
import fr.blemale.dropwizard.todo.api.todo.request.TodoUpdateRequest;

public interface TodoService {
    ExternalTodoLight createTodo(TodoCreationRequest todoCreationRequest);

    ExternalTodoLight updateTodo(long id, TodoUpdateRequest todoUpdateRequest);

    ExternalTodoList getTodos();

    Optional<ExternalTodo> getTodo(long id);
}
