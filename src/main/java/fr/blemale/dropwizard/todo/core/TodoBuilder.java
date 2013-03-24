package fr.blemale.dropwizard.todo.core;

import com.google.common.base.Optional;

public class TodoBuilder {
    private long id;
    private String title;
    private Optional<String> content = Optional.absent();

    private TodoBuilder(long id, String title) {
        this.id = id;
        this.title = title;
    }

    private TodoBuilder(Todo todo) {
        this.id = todo.getId();
        this.title = todo.getTitle();
        this.content = todo.getContent();
    }

    public static TodoBuilder aTodo(long id, String title) {
        return new TodoBuilder(id, title);
    }

    public static TodoBuilder aTodo(Todo todo) {
        return new TodoBuilder(todo);
    }

    public TodoBuilder withId(long id) {
        this.id = id;
        return this;
    }

    public TodoBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    public TodoBuilder withContent(Optional<String> content) {
        this.content = content;
        return this;
    }

    public Todo build() {
        Todo todo = new Todo(id, title, content);
        return todo;
    }
}
