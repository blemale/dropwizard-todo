package fr.blemale.dropwizard.todo.core;

public class Todo {
    private final long id;
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
}
