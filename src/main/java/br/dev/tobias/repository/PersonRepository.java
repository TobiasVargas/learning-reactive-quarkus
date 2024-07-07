package br.dev.tobias.repository;

import br.dev.tobias.entity.Person;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import jakarta.enterprise.context.Dependent;

@Dependent
public class PersonRepository implements PanacheRepository<Person> {

}
