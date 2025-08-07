package io.github.murilo_sys.services;

import io.github.murilo_sys.exception.ResourceNotFoundExcepiton;
import io.github.murilo_sys.model.Person;
import io.github.murilo_sys.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class PersonServices {


    final PersonRepository repository;
    private final Logger logger = Logger.getLogger(PersonServices.class.getName());

    public PersonServices(PersonRepository repository) {
        this.repository = repository;
    }


    public List<Person> findAll() {
        logger.info("Finding all People!");

        return repository.findAll();

    }


    public Person findById(Long id) {
        logger.info("Finding one Person!");

        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundExcepiton("No records found for this id"));
    }


    public Person create(Person person) {

        if (person.getId() != null) {
            logger.info("Não é permitido setar ID ao person. Setando automaticamente!");
            person.setId(null);
        }

        Person entity = repository.save(person);

        logger.info("Creating one Person! ID: " + entity.getId());

        return entity;
    }


    public void delete(Long id) {
        logger.info("Deleting id: " + id);

        Person entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundExcepiton("No records found for this id"));

        repository.deleteById(entity.getId());
    }


    public Person update(Person person) {
        logger.info("Update one Person!");

        Person entity = repository.findById(person.getId()).orElseThrow(() -> new ResourceNotFoundExcepiton("No records found for this id"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        return repository.save(entity);
    }
}
