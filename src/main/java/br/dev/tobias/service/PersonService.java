package br.dev.tobias.service;

import br.dev.tobias.entity.Person;
import br.dev.tobias.repository.PersonRepository;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.Dependent;

import java.util.List;

@Dependent
public class PersonService {
    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @WithTransaction
    public Uni<Person> create(Person person) {
        return personRepository.persist(person);
    }

    @WithTransaction
    public Uni<Person> update(Long id, Person person) {
        return personRepository.findById(id).onItem()
                .ifNotNull()
                .transform((p) -> {
                    p.setFirstName(person.getFirstName());
                    p.setLastName(person.getLastName());
                    p.setCpf(person.getCpf());
                    return p;
                }).call(personRepository::persist);
    }

    @WithTransaction
    public Uni<List<Person>> find() {
        return personRepository.listAll();
    }

    @WithTransaction
    public Uni<Boolean> delete(Long id) {
        return personRepository.deleteById(id);
    }
}
