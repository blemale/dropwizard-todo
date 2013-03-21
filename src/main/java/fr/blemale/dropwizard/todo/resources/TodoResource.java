package fr.blemale.dropwizard.todo.resources;

import com.google.common.base.Optional;
import com.yammer.dropwizard.jersey.params.LongParam;
import fr.blemale.dropwizard.todo.api.todo.external.ExternalTodo;
import fr.blemale.dropwizard.todo.api.todo.external.ExternalTodoLight;
import fr.blemale.dropwizard.todo.api.todo.external.ExternalTodoList;
import fr.blemale.dropwizard.todo.api.todo.request.TodoCreationRequest;
import fr.blemale.dropwizard.todo.api.todo.request.TodoUpdateRequest;
import fr.blemale.dropwizard.todo.core.TodoService;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/todos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TodoResource {

    private final TodoService todoService;

    public TodoResource(TodoService todoService) {
        this.todoService = todoService;
    }

    @GET
    public ExternalTodoList getTodos() {
        return this.todoService.getTodos();
    }

    @POST
    public ExternalTodoLight createTodo(@Valid TodoCreationRequest todoCreationRequest) {
        return this.todoService.createTodo(todoCreationRequest);
    }

    @Path("{id}")
    @GET
    public ExternalTodo getTodo(@PathParam("id") LongParam id) {
        Optional<ExternalTodo> todo = this.todoService.getTodo(id.get());
        if (todo.isPresent()) {
            return todo.get();
        } else {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }

    @Path("{id}")
    @PUT
    public ExternalTodoLight updateTodo(@PathParam("id") LongParam id, @Valid TodoUpdateRequest todoUpdateRequest) {
        return this.updateTodo(id, todoUpdateRequest);
    }
}
