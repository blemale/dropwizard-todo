package fr.blemale.dropwizard.todo.core;

import javax.validation.constraints.NotNull;

public class Todo {
    private final long id;
    @NotNull
    private final String title;
    private final String content;

    public Todo(long id, String title, String content) {
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
}
