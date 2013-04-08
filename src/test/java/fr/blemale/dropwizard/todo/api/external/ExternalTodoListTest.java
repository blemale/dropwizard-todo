package fr.blemale.dropwizard.todo.api.external;

import com.google.common.collect.Lists;
import org.junit.Test;

import static com.yammer.dropwizard.testing.JsonHelpers.asJson;
import static com.yammer.dropwizard.testing.JsonHelpers.jsonFixture;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ExternalTodoListTest {

    @Test
    public void producesTheExpectedJson() throws Exception {
        ExternalTodoList externalTodoList =
                new ExternalTodoList(Lists.newArrayList(
                        new ExternalTodo(1L, "title1", "content1", "selfUrl1"),
                        new ExternalTodo(2L, "title2", "content2", "selfUrl2")));

        assertThat("rendering a externalTodoList as JSON produces a valid API representation",
                asJson(externalTodoList),
                is(jsonFixture("fixtures/externalTodoList.json")));
    }
}