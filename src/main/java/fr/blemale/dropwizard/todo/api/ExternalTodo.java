package fr.blemale.dropwizard.todo.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ExternalTodo {
    @JsonProperty
    private final long id;
    @JsonProperty
    private final String title;
    @JsonProperty
    private final String content;
    @JsonProperty
    private final String selfUrl;

    public ExternalTodo(@JsonProperty("id") long id, @JsonProperty("title") String title, @JsonProperty("content") String content, @JsonProperty("selfUrl") String selfUrl) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.selfUrl = selfUrl;
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

    public String getSelfUrl() {
        return selfUrl;
    }
}
