package fr.blemale.dropwizard.todo.api.todo.external;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ExternalTodoLight {
    @JsonProperty
    private final long id;
    @JsonProperty
    private final String title;
    @JsonProperty
    private final String selfUrl;

    @JsonCreator
    public ExternalTodoLight(@JsonProperty("id") long id, @JsonProperty("title") String title, @JsonProperty("selfUrl") String selfUrl) {
        this.id = id;
        this.title = title;
        this.selfUrl = selfUrl;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getSelfUrl() {
        return selfUrl;
    }
}
