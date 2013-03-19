package fr.blemale.dropwizard.todo.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ExternalTodoList {
    @JsonProperty
    private final List<ExternalTodoLight> todos;

    @JsonCreator
    public ExternalTodoList(List<ExternalTodoLight> todos) {
        this.todos = todos;
    }

    public List<ExternalTodoLight> getTodos() {
        return todos;
    }
}
