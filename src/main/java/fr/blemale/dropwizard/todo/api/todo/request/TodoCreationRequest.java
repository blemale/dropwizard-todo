package fr.blemale.dropwizard.todo.api.todo.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;
import com.google.common.base.Strings;
import fr.blemale.dropwizard.todo.core.Todo;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

public class TodoCreationRequest {
    @NotBlank
    @Length(min = 1, max = 50)
    @JsonProperty
    private final String title;
    @Length(max = 500)
    @JsonProperty
    private final String content;

    @JsonCreator
    public TodoCreationRequest(@JsonProperty("title") String title, @JsonProperty("content") String content) {
        this.title = title;
        this.content = Strings.emptyToNull(content);
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TodoCreationRequest that = (TodoCreationRequest) o;

        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        return title.equals(that.title);

    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + (content != null ? content.hashCode() : 0);
        return result;
    }

    public static class Mapper {
        public Todo toTodo(TodoCreationRequest todoCreationRequest) {
            return Todo.Builder.aTodo(0, todoCreationRequest.getTitle()).withContent(Optional.fromNullable(todoCreationRequest.getContent())).build();
        }
    }
}
