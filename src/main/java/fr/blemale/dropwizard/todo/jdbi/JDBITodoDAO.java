package fr.blemale.dropwizard.todo.jdbi;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import fr.blemale.dropwizard.todo.core.Todo;
import org.skife.jdbi.v2.sqlobject.*;

public interface JDBITodoDAO extends TodoDAO {
    @Override
    @GetGeneratedKeys
    @SqlUpdate("INSERT INTO Todo (title, content) VALUES (:title, :content)")
    Long createTodo(@BindBean Todo todo);

    @Override
    @SqlUpdate("UPDATE Todo SET title = :title, content = :content WHERE id = :id")
    int updateTodo(@BindBean Todo todo);

    @Override
    @SqlQuery("SELECT id, title, content FROM Todo")
    ImmutableList<Todo> getTodos();

    @Override
    @SqlQuery("SELECT id, title, content FROM Todo WHERE id = :id")
    Optional<Todo> getTodo(@Bind("id") long id);
}
