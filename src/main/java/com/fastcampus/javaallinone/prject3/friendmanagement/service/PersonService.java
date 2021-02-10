package com.fastcampus.javaallinone.prject3.friendmanagement.service;

import com.fastcampus.javaallinone.prject3.friendmanagement.domain.Person;
import com.fastcampus.javaallinone.prject3.friendmanagement.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public List<Person> getPeopleExcludeBlocks() {
        List<Person> people = personRepository.findAll();

        return people.stream().filter(person -> person.getBlock() == null).collect(Collectors.toList());
    }

    public List<Person> getPeopleByName(String name) {
//        List<Person> people = personRepository.findByName(name);

//        return people.stream().filter(person -> person.getName().equals(name)).collect(Collectors.toList());
        return personRepository.findByName(name);
    }

    @Transactional(readOnly = true)
    public Person getPerson(Long id) {
        Person person = personRepository.findById(id).get();

        log.info("person : {}", person);

        return person;
    }
}
