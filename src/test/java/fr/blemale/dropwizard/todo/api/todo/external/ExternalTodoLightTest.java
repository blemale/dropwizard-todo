package fr.blemale.dropwizard.todo.api.todo.external;

import org.junit.Test;

import static com.yammer.dropwizard.testing.JsonHelpers.asJson;
import static com.yammer.dropwizard.testing.JsonHelpers.jsonFixture;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ExternalTodoLightTest {
    @Test
    public void producesTheExpectedJson() throws Exception {
        ExternalTodoLight externalTodoLight = new ExternalTodoLight(1L, "selfUrl");

        assertThat("rendering a externalTodoLight as JSON produces a valid API representation",
                asJson(externalTodoLight),
                is(jsonFixture("fixtures/externalTodoLight.json")));
    }
}
