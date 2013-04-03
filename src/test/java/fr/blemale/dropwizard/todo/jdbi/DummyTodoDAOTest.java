package fr.blemale.dropwizard.todo.jdbi;

import com.google.common.base.Optional;
import fr.blemale.dropwizard.todo.core.Todo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class DummyTodoDAOTest {
    private DummyTodoDAO dummyTodoDAO;

    @Before
    public void setUp() {
        dummyTodoDAO = new DummyTodoDAO();
    }

    @After
    public void cleanUp() {
        dummyTodoDAO = null;
    }

    @Test
    public void createTodo() {
        Todo newTodo = Todo.Builder.aTodo(1L, "title").withContent(Optional.of("content")).build();
        Todo expectedTodo = Todo.Builder.aTodo(newTodo).withId(0L).build();

        Long actualTodoId = dummyTodoDAO.createTodo(newTodo);

        assertThat("creating a new Todo returns a valid id", actualTodoId, is(expectedTodo.getId()));
    }

    @Test
    public void updateTodoWithAnExistingTodo() {
        Todo newTodo = Todo.Builder.aTodo(0L, "title").withContent(Optional.of("content")).build();
        Todo updatedTodo = Todo.Builder.aTodo(newTodo).withTitle("updatedTodo").withContent(Optional.of("updatedContent")).build();
        Optional<Todo> expectedTodo = Optional.of(updatedTodo);

        dummyTodoDAO.createTodo(newTodo);
        int nbRowUpdated = dummyTodoDAO.updateTodo(updatedTodo);

        assertThat("updating an existing Todo returns 1", nbRowUpdated, is(1));
    }


    @Test
    public void updateTodoWithANonExistingTodo() {
        Todo updatedTodo = Todo.Builder.aTodo(0L, "updatedTitle").withContent(Optional.of("updatedContent")).build();
        Optional<Todo> expectedTodo = Optional.absent();

        int nbRowUpdated = dummyTodoDAO.updateTodo(updatedTodo);

        assertThat("updating a non existing Todo returns 0", nbRowUpdated, is(0));
    }

    @Test
    public void getTodosWithNoTodos() {
        List<Todo> expectedTodos = Collections.EMPTY_LIST;

        List<Todo> actualTodos = dummyTodoDAO.getTodos();

        assertThat("getting todos when any todo created returns an empty list", actualTodos, is(expectedTodos));
    }

    @Test
    public void getTodosWhithTodos() {
        Todo newTodo0 = Todo.Builder.aTodo(0L, "title0").withContent(Optional.of("content0")).build();
        Todo newTodo1 = Todo.Builder.aTodo(1L, "title1").withContent(Optional.of("content1")).build();

        dummyTodoDAO.createTodo(newTodo0);
        dummyTodoDAO.createTodo(newTodo1);
        List<Todo> actualTodos = dummyTodoDAO.getTodos();

        // Compilation failed with javac not with IntelliJ ?! It should be a Hamcrest bug.
        // assertThat("getting todos when two Todo are created returns a list with these two Todos", actualTodos, hasItems(newTodo0, newTodo1));
        // Workaround
        assertTrue("getting todos when two Todo are created returns a list with these two Todos", actualTodos.contains(newTodo0) && actualTodos.contains(newTodo1));
    }

    @Test
    public void getTodoWithAnExistingTodo() {
        long id = 0L;
        Todo newTodo = Todo.Builder.aTodo(0L, "title").withContent(Optional.of("content")).build();
        Optional<Todo> expectedTodo = Optional.of(newTodo);

        dummyTodoDAO.createTodo(newTodo);
        Optional<Todo> actualTodo = dummyTodoDAO.getTodo(id);

        assertThat("getting an existing todo returns the present todo", actualTodo, is(expectedTodo));
    }

    @Test
    public void getTodoWithANonExistingTodo() {
        long id = 0L;
        Optional<Todo> expectedTodo = Optional.absent();

        Optional<Todo> actualTodo = dummyTodoDAO.getTodo(id);

        assertThat("getting a non existing todo returns an absent todo", actualTodo, is(expectedTodo));
    }
}
