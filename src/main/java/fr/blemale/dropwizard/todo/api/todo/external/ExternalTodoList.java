package fr.blemale.dropwizard.todo.api.todo.external;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import fr.blemale.dropwizard.todo.core.Todo;

import javax.validation.constraints.NotNull;
import java.util.List;

public class ExternalTodoList {
    @NotNull
    @JsonProperty
    private final List<ExternalTodo> todos;

    @JsonCreator
    public ExternalTodoList(List<ExternalTodo> todos) {
        this.todos = todos;
    }

    public List<ExternalTodo> getTodos() {
        return todos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExternalTodoList that = (ExternalTodoList) o;

        if (!todos.equals(that.todos)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return todos.hashCode();
    }

    public static class Mapper {
        public ExternalTodoList fromTodoList(List<Todo> todos) {
            ExternalTodo.Mapper mapper = new ExternalTodo.Mapper();
            List<ExternalTodo> externalTodos = Lists.newArrayList();
            for (Todo todo : todos) {
                externalTodos.add(mapper.fromTodo(todo));
            }
            return new ExternalTodoList(ImmutableList.copyOf(externalTodos));
        }
    }
}
