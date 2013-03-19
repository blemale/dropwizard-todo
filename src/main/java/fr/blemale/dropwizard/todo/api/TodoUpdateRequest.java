package fr.blemale.dropwizard.todo.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

public class TodoUpdateRequest {
    @NotNull
    @JsonProperty
    private final long id;
    @JsonProperty
    private final String title;
    @JsonProperty
    private final String content;

    @JsonCreator
    public TodoUpdateRequest(@JsonProperty("id") long id, @JsonProperty("title") String title, @JsonProperty("content") String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
