package fr.blemale.dropwizard.todo.api.todo.external;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

public class ExternalTodoLight {
    @JsonProperty
    private final long id;
    @NotNull
    @JsonProperty
    private final String title;
    @NotNull
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExternalTodoLight that = (ExternalTodoLight) o;

        if (id != that.id) return false;
        if (!selfUrl.equals(that.selfUrl)) return false;
        if (!title.equals(that.title)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + title.hashCode();
        result = 31 * result + selfUrl.hashCode();
        return result;
    }
}
