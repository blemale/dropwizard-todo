package fr.blemale.dropwizard.todo.jdbi;

import com.google.common.base.Optional;
import fr.blemale.dropwizard.todo.core.Todo;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TodoMapper implements ResultSetMapper<Todo> {

    @Override
    public Todo map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
        return Todo.Builder.aTodo(resultSet.getLong("id"), resultSet.getString("title")).withContent(Optional.fromNullable(resultSet.getString("content"))).build();
    }
}
