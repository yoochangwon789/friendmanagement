package com.fastcampus.javaallinone.prject3.friendmanagement.controller;

import com.fastcampus.javaallinone.prject3.friendmanagement.controller.dto.PersonDto;
import com.fastcampus.javaallinone.prject3.friendmanagement.domain.Person;
import com.fastcampus.javaallinone.prject3.friendmanagement.exception.PersonNotFoundException;
import com.fastcampus.javaallinone.prject3.friendmanagement.exception.RenameIsNotPermittedException;
import com.fastcampus.javaallinone.prject3.friendmanagement.exception.dto.ErrorResponse;
import com.fastcampus.javaallinone.prject3.friendmanagement.repository.PersonRepository;
import com.fastcampus.javaallinone.prject3.friendmanagement.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/api/person")
@RestController
@Slf4j
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping("/{id}")
    public Person getPerson(@PathVariable Long id) {
        return personService.getPerson(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void postPerson(@RequestBody PersonDto personDto) {
        personService.put(personDto);
    }

    @PutMapping("/{id}")
    public void modifyPerson(@PathVariable Long id, @RequestBody PersonDto personDto) {
        personService.modify(id, personDto);
    }

    // Patch 일부 리소스만 업데이트 한다는 의미
    @PatchMapping("/{id}")
    public void modifyPerson(@PathVariable Long id, String name) {
        personService.modify(id, name);
    }

    @DeleteMapping("/{id}")
    public void deletePerson(@PathVariable Long id) {
        personService.delete(id);
    }

    @ExceptionHandler(value = RenameIsNotPermittedException.class)
    public ResponseEntity<ErrorResponse> handleRenameNoPermittedException(RenameIsNotPermittedException ex) {
        return new ResponseEntity<>(ErrorResponse.of(HttpStatus.BAD_REQUEST, ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = PersonNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePersonNotFoundException(PersonNotFoundException ex) {
        return new ResponseEntity<>(ErrorResponse.of(HttpStatus.BAD_REQUEST, ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex) {
        return new ResponseEntity<>(ErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
