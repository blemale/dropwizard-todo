package fr.blemale.dropwizard.todo.api.todo.external;

import org.junit.Test;

import static com.yammer.dropwizard.testing.JsonHelpers.asJson;
import static com.yammer.dropwizard.testing.JsonHelpers.jsonFixture;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ExternalTodoTest {
    @Test
    public void producesTheExpectedJson() throws Exception {
        ExternalTodo externalTodo = new ExternalTodo(1L, "title", "content", "selfUrl");

        assertThat("rendering a externalTodo as JSON produces a valid API representation",
                asJson(externalTodo),
                is(jsonFixture("fixtures/externalTodo.json")));
    }
}
