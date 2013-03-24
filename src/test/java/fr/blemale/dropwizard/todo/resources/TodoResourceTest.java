package fr.blemale.dropwizard.todo.resources;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.yammer.dropwizard.jersey.params.LongParam;
import fr.blemale.dropwizard.todo.api.todo.external.ExternalTodo;
import fr.blemale.dropwizard.todo.api.todo.external.ExternalTodoLight;
import fr.blemale.dropwizard.todo.api.todo.external.ExternalTodoList;
import fr.blemale.dropwizard.todo.api.todo.request.TodoCreationRequest;
import fr.blemale.dropwizard.todo.api.todo.request.TodoUpdateRequest;
import fr.blemale.dropwizard.todo.core.Todo;
import fr.blemale.dropwizard.todo.core.TodoBuilder;
import fr.blemale.dropwizard.todo.jdbi.TodoDAO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.WebApplicationException;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TodoResourceTest {
    private TodoDAO todoDAO;
    private TodoResource todoResource;

    @Before
    public void setUp() {
        this.todoDAO = mock(TodoDAO.class);
        this.todoResource = new TodoResource(this.todoDAO);
    }

    @After
    public void cleanUp() {
        this.todoDAO = null;
        this.todoResource = null;
    }

    @Test
    public void getTodos() {
        List<Todo> todos = ImmutableList.of(TodoBuilder.aTodo(0L, "title").build());
        ExternalTodoList expectedTodos = new ExternalTodoList.Mapper().fromTodoList(todos);
        when(this.todoDAO.getTodos()).thenReturn(todos);

        ExternalTodoList actualTodos = this.todoResource.getTodos();

        assertThat("getting todos produces an ExternalTodoList with expected todos inside", actualTodos, is(expectedTodos));
    }

    @Test
    public void createTodo() {
        TodoCreationRequest todoCreationRequest = new TodoCreationRequest("title", null);
        Todo todo = TodoBuilder.aTodo(0L, "title").build();
        ExternalTodoLight expectedTodo = new ExternalTodoLight.Mapper().fromTodo(todo);
        when(this.todoDAO.createTodo(todo)).thenReturn(todo);

        ExternalTodoLight actualTodo = this.todoResource.createTodo(todoCreationRequest);

        assertThat("create a todo produces an ExternalTodoLight", actualTodo, is(expectedTodo));
    }

    @Test
    public void updateTodoWithAnExistingTodo() {
        TodoUpdateRequest todoCreationRequest = new TodoUpdateRequest("title", null);
        Todo todo = TodoBuilder.aTodo(0L, "title").build();
        ExternalTodoLight expectedTodo = new ExternalTodoLight.Mapper().fromTodo(todo);
        when(this.todoDAO.updateTodo(todo)).thenReturn(Optional.of(todo));

        ExternalTodoLight actualTodo = this.todoResource.updateTodo(new LongParam("0"), todoCreationRequest);

        assertThat("create a todo produces an ExternalTodoLight", actualTodo, is(expectedTodo));
    }

    @Test(expected = WebApplicationException.class)
    public void updateTodoWithANonExistingTodo() {
        TodoUpdateRequest todoCreationRequest = new TodoUpdateRequest("title", null);
        Todo todo = TodoBuilder.aTodo(0L, "title").build();
        Optional<Todo> absentTodo = Optional.absent();
        when(this.todoDAO.updateTodo(todo)).thenReturn(absentTodo);

        this.todoResource.updateTodo(new LongParam("0"), todoCreationRequest);
    }

    @Test
    public void getTodoWithAnId() {
        long id = 0L;
        Todo todo = TodoBuilder.aTodo(id, "title").build();
        ExternalTodo expectedTodo = new ExternalTodo.Mapper().fromTodo(todo);
        when(this.todoDAO.getTodo(id)).thenReturn(Optional.of(todo));

        ExternalTodo actualTodo = this.todoResource.getTodo(new LongParam(Long.toString(id)));

        assertThat("get a todo with an existing id produces an ExternalTodo", actualTodo, is(expectedTodo));
    }

    @Test(expected = WebApplicationException.class)
    public void getTodoWithANonId() {
        long id = 0L;
        Optional<Todo> absentTodo = Optional.absent();
        when(this.todoDAO.getTodo(id)).thenReturn(absentTodo);

        this.todoResource.getTodo(new LongParam(Long.toString(id)));
    }


}
