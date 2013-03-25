package fr.blemale.dropwizard.todo.core;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;

public class Todo {
    private final long id;
    private final String title;
    private final Optional<String> content;

    private Todo(long id, String title, Optional<String> content) {
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

    public Optional<String> getContent() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Todo todo = (Todo) o;

        if (id != todo.id) return false;
        if (content != null ? !content.equals(todo.content) : todo.content != null) return false;
        if (!title.equals(todo.title)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + title.hashCode();
        result = 31 * result + (content != null ? content.hashCode() : 0);
        return result;
    }

    public static class Builder {
        private long id;
        private String title;
        private Optional<String> content = Optional.absent();

        private Builder(long id, String title) {
            this.id = id;
            this.title = title;
        }

        private Builder(Todo todo) {
            this.id = todo.getId();
            this.title = todo.getTitle();
            this.content = todo.getContent();
        }

        public static Builder aTodo(long id, String title) {
            return new Builder(id, title);
        }

        public static Builder aTodo(Todo todo) {
            return new Builder(todo);
        }

        public Builder withId(long id) {
            this.id = id;
            return this;
        }

        public Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder withContent(Optional<String> content) {
            this.content = content;
            return this;
        }

        public Todo build() {
            Preconditions.checkNotNull(this.id, "id can't be null");
            Preconditions.checkNotNull(this.title, "title can't be null");
            Preconditions.checkNotNull(this.content, "content can't be null");

            Todo todo = new Todo(id, title, content);
            return todo;
        }
    }
}
