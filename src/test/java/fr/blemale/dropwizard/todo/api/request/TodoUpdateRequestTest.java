package fr.blemale.dropwizard.todo.api.request;

import org.junit.Test;

import static com.yammer.dropwizard.testing.JsonHelpers.fromJson;
import static com.yammer.dropwizard.testing.JsonHelpers.jsonFixture;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TodoUpdateRequestTest {
    @Test
    public void consumesTheExpectedJsonWithAllFields() throws Exception {
        TodoUpdateRequest todoUpdateRequest = new TodoUpdateRequest("title", "content");
        assertThat("parsing a valid API representation produces a TodoUpdateRequest",
                fromJson(jsonFixture("fixtures/todoUpdateRequest.json"), TodoUpdateRequest.class),
                is(todoUpdateRequest));
    }

    @Test
    public void consumesTheExpectedJsonWithMandatoryFields() throws Exception {
        TodoUpdateRequest todoUpdateRequest = new TodoUpdateRequest("title", null);
        assertThat("parsing a valid API representation with only mandatory fields produces a TodoUpdateRequest",
                fromJson(jsonFixture("fixtures/todoUpdateRequestWithOnlyMandatory.json"), TodoUpdateRequest.class),
                is(todoUpdateRequest));
    }
}
