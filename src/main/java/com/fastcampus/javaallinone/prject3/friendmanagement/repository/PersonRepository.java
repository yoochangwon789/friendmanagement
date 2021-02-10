package com.fastcampus.javaallinone.prject3.friendmanagement.repository;

import com.fastcampus.javaallinone.prject3.friendmanagement.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    List<Person> findByName(String name);
}
