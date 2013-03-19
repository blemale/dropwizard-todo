package fr.blemale.dropwizard.todo.api.todo.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

public class TodoCreationRequest {
    @NotNull
    @JsonProperty
    private final String title;
    @JsonProperty
    private final String content;

    @JsonCreator
    public TodoCreationRequest(@JsonProperty("title") String title, @JsonProperty("content") String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
