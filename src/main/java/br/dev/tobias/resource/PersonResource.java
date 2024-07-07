package br.dev.tobias.resource;


import br.dev.tobias.entity.Person;
import br.dev.tobias.service.PersonService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.net.URI;

@RequestScoped
@Path("/api/v1/person")
public class PersonResource {
    private final PersonService personService;

    public PersonResource(PersonService personService) {
        this.personService = personService;
    }

    @GET
    @Path("/")
    public Uni<Response> find() {
        return personService.find().onItem()
                .transform(persons -> Response.ok(persons).build());
    }

    @DELETE
    @Path("/{id}")
    public Uni<Response> delete(Long id) {
        return personService.delete(id).onItem()
                .transform(person -> Response.noContent().build());
    }

    @PUT
    @Path("/{id}")
    public Uni<Response> update(Long id, Person person) {
        return personService.update(id, person)
                .onItem()
                .transform(personSaved -> Response
                        .ok(personSaved)
                        .build()
                );
    }

    @POST
    @Path("/")
    public Uni<Response> create(Person person) {
        return personService.create(person)
                .onItem()
                .transform(personSaved -> Response
                        .created(URI.create("/v1/person" + personSaved.getId().toString()))
                        .entity(personSaved).build()
                );
    }
}
