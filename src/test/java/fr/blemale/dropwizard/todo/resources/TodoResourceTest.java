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
import fr.blemale.dropwizard.todo.core.User;
import fr.blemale.dropwizard.todo.jdbi.TodoDAO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.WebApplicationException;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

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
        User user = User.Builder.anUser("user").build();
        ImmutableList<Todo> todos = ImmutableList.of(Todo.Builder.aTodo(0L, "title").build());
        ExternalTodoList expectedTodos = new ExternalTodoList.Mapper().fromTodoList(todos);
        when(this.todoDAO.getTodos()).thenReturn(todos);

        ExternalTodoList actualTodos = this.todoResource.getTodos(user);

        assertThat("getting todos produces an ExternalTodoList with expected todos inside", actualTodos, is(expectedTodos));
    }

    @Test
    public void createTodo() {
        User user = User.Builder.anUser("user").build();
        TodoCreationRequest todoCreationRequest = new TodoCreationRequest("title", null);
        Todo todo = Todo.Builder.aTodo(0L, "title").build();
        ExternalTodoLight expectedTodo = new ExternalTodoLight.Mapper().fromId(0L);
        when(this.todoDAO.createTodo(todo)).thenReturn(0L);

        ExternalTodoLight actualTodo = this.todoResource.createTodo(user, todoCreationRequest);

        assertThat("create a todo produces an ExternalTodoLight", actualTodo, is(expectedTodo));
    }

    @Test
    public void updateTodoWithAnExistingTodo() {
        User user = User.Builder.anUser("user").build();
        TodoUpdateRequest todoCreationRequest = new TodoUpdateRequest("title", null);
        Todo todo = Todo.Builder.aTodo(0L, "title").build();
        ExternalTodoLight expectedTodo = new ExternalTodoLight.Mapper().fromId(0L);
        when(this.todoDAO.updateTodo(todo)).thenReturn(1);

        this.todoResource.updateTodo(user, new LongParam("0"), todoCreationRequest);

        verify(this.todoDAO).updateTodo(todo);
    }

    @Test(expected = WebApplicationException.class)
    public void updateTodoWithANonExistingTodo() {
        User user = User.Builder.anUser("user").build();
        TodoUpdateRequest todoCreationRequest = new TodoUpdateRequest("title", null);
        Todo todo = Todo.Builder.aTodo(0L, "title").build();
        when(this.todoDAO.updateTodo(todo)).thenReturn(0);

        this.todoResource.updateTodo(user, new LongParam("0"), todoCreationRequest);
    }

    @Test
    public void getTodoWithAnId() {
        User user = User.Builder.anUser("user").build();
        long id = 0L;
        Todo todo = Todo.Builder.aTodo(id, "title").build();
        ExternalTodo expectedTodo = new ExternalTodo.Mapper().fromTodo(todo);
        when(this.todoDAO.getTodo(id)).thenReturn(Optional.of(todo));

        ExternalTodo actualTodo = this.todoResource.getTodo(user, new LongParam(Long.toString(id)));

        assertThat("get a todo with an existing id produces an ExternalTodo", actualTodo, is(expectedTodo));
    }

    @Test(expected = WebApplicationException.class)
    public void getTodoWithANonId() {
        User user = User.Builder.anUser("user").build();
        long id = 0L;
        when(this.todoDAO.getTodo(id)).thenReturn(Optional.<Todo>absent());

        this.todoResource.getTodo(user, new LongParam(Long.toString(id)));
    }


}
