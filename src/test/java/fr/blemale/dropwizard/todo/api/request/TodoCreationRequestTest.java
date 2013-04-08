package fr.blemale.dropwizard.todo.api.request;

import org.junit.Test;

import static com.yammer.dropwizard.testing.JsonHelpers.fromJson;
import static com.yammer.dropwizard.testing.JsonHelpers.jsonFixture;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TodoCreationRequestTest {
    @Test
    public void consumesTheExpectedJsonWithAllFields() throws Exception {
        TodoCreationRequest todoCreationRequest = new TodoCreationRequest("title", "content");
        assertThat("parsing a valid API representation produces a TodoCreationRequest",
                fromJson(jsonFixture("fixtures/todoCreationRequest.json"), TodoCreationRequest.class),
                is(todoCreationRequest));
    }

    @Test
    public void consumesTheExpectedJsonWithMandatoryFields() throws Exception {
        TodoCreationRequest todoCreationRequest = new TodoCreationRequest("title", null);
        assertThat("parsing a valid API representation with only mandatory fields produces a TodoCreationRequest",
                fromJson(jsonFixture("fixtures/todoCreationRequestWithOnlyMandatory.json"), TodoCreationRequest.class),
                is(todoCreationRequest));
    }
}
