package fr.blemale.dropwizard.todo.resources;

import com.google.common.base.Optional;
import com.yammer.dropwizard.auth.Auth;
import com.yammer.dropwizard.jersey.params.LongParam;
import com.yammer.metrics.annotation.Timed;
import fr.blemale.dropwizard.todo.api.external.ExternalTodo;
import fr.blemale.dropwizard.todo.api.external.ExternalTodoLight;
import fr.blemale.dropwizard.todo.api.external.ExternalTodoList;
import fr.blemale.dropwizard.todo.api.request.TodoCreationRequest;
import fr.blemale.dropwizard.todo.api.request.TodoUpdateRequest;
import fr.blemale.dropwizard.todo.core.Todo;
import fr.blemale.dropwizard.todo.core.User;
import fr.blemale.dropwizard.todo.jdbi.TodoDAO;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("/todos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TodoResource {

    private final TodoDAO todoDAO;

    public TodoResource(TodoDAO todoDAO) {
        this.todoDAO = todoDAO;
    }

    @Timed
    @GET
    public ExternalTodoList getTodos(@Context UriInfo uriInfo, @Auth User user) {
        return new ExternalTodoList.Mapper().fromTodoList(uriInfo.getBaseUri(), this.todoDAO.getTodos());
    }

    @Timed
    @POST
    public ExternalTodoLight createTodo(@Context UriInfo uriInfo, @Auth User user, @Valid TodoCreationRequest todoCreationRequest) {
        Long createdTodoId = this.todoDAO.createTodo(new TodoCreationRequest.Mapper().toTodo(todoCreationRequest));
        return new ExternalTodoLight.Mapper().fromId(uriInfo.getBaseUri(),createdTodoId);
    }

    @Timed
    @Path("{id}")
    @GET
    public ExternalTodo getTodo(@Context UriInfo uriInfo, @Auth User user, @PathParam("id") LongParam id) {
        Optional<Todo> todo = this.todoDAO.getTodo(id.get());
        if (todo.isPresent()) {
            return new ExternalTodo.Mapper().fromTodo(uriInfo.getBaseUri(),todo.get());
        } else {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }

    @Timed
    @Path("{id}")
    @PUT
    public void updateTodo(@Context UriInfo uriInfo, @Auth User user, @PathParam("id") LongParam id, @Valid TodoUpdateRequest todoUpdateRequest) {
        Todo updatedTodo = new TodoUpdateRequest.Mapper().toTodo(id.get(), todoUpdateRequest);
        int nbRowUpdated = this.todoDAO.updateTodo(updatedTodo);
        if (nbRowUpdated == 0) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }
}
