package fr.blemale.dropwizard.todo.api.external;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import fr.blemale.dropwizard.todo.core.Todo;

import javax.validation.constraints.NotNull;
import java.net.URI;
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

    @Override
    public String toString() {
        return "ExternalTodoList{" +
                "todos=" + todos +
                '}';
    }

    public static class Mapper {
        public ExternalTodoList fromTodoList(URI baseUri, List<Todo> todos) {
            ExternalTodo.Mapper mapper = new ExternalTodo.Mapper();
            List<ExternalTodo> externalTodos = Lists.newArrayList();
            for (Todo todo : todos) {
                externalTodos.add(mapper.fromTodo(baseUri, todo));
            }
            return new ExternalTodoList(ImmutableList.copyOf(externalTodos));
        }
    }
}
