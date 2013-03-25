package fr.blemale.dropwizard.todo.resources;

import com.google.common.base.Optional;
import com.yammer.dropwizard.auth.Auth;
import com.yammer.dropwizard.jersey.params.LongParam;
import com.yammer.metrics.annotation.Timed;
import fr.blemale.dropwizard.todo.api.todo.external.ExternalTodo;
import fr.blemale.dropwizard.todo.api.todo.external.ExternalTodoLight;
import fr.blemale.dropwizard.todo.api.todo.external.ExternalTodoList;
import fr.blemale.dropwizard.todo.api.todo.request.TodoCreationRequest;
import fr.blemale.dropwizard.todo.api.todo.request.TodoUpdateRequest;
import fr.blemale.dropwizard.todo.core.Todo;
import fr.blemale.dropwizard.todo.core.User;
import fr.blemale.dropwizard.todo.jdbi.TodoDAO;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
    public ExternalTodoList getTodos(@Auth User user) {
        return new ExternalTodoList.Mapper().fromTodoList(this.todoDAO.getTodos());
    }

    @Timed
    @POST
    public ExternalTodoLight createTodo(@Auth User user, @Valid TodoCreationRequest todoCreationRequest) {
        Todo createdTodo = this.todoDAO.createTodo(new TodoCreationRequest.Mapper().toTodo(todoCreationRequest));
        return new ExternalTodoLight.Mapper().fromTodo(createdTodo);
    }

    @Timed
    @Path("{id}")
    @GET
    public ExternalTodo getTodo(@Auth User user, @PathParam("id") LongParam id) {
        Optional<Todo> todo = this.todoDAO.getTodo(id.get());
        if (todo.isPresent()) {
            return new ExternalTodo.Mapper().fromTodo(todo.get());
        } else {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }

    @Timed
    @Path("{id}")
    @PUT
    public ExternalTodoLight updateTodo(@Auth User user, @PathParam("id") LongParam id, @Valid TodoUpdateRequest todoUpdateRequest) {
        Todo updatedTodo = new TodoUpdateRequest.Mapper().toTodo(id.get(), todoUpdateRequest);
        Optional<Todo> todo = this.todoDAO.updateTodo(updatedTodo);
        if (todo.isPresent()) {
            return new ExternalTodoLight.Mapper().fromTodo(todo.get());
        } else {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }
}
