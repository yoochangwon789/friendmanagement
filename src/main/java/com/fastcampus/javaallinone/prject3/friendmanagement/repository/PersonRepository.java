package com.fastcampus.javaallinone.prject3.friendmanagement.repository;

import com.fastcampus.javaallinone.prject3.friendmanagement.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    List<Person> findByName(String name);

    List<Person> findByBlockIsNull();

    // ? 인자를 사용하면 파라미터의 순서대로 쿼리를 넣어주게 된다
    @Query(value = "select person from Person person where person.birthday.monthOfBirthday = :monthOfBirthday")
    List<Person> findByMonthOfBirthday(@Param("monthOfBirthday") int monthOfBirthday);

    // nativeQuery = true 를 이용한 내가 직접 지정한 쿼리 만 찍힌다
    @Query(value = "select * from Person person where person.deleted = true", nativeQuery = true)
    List<Person> findPeopleDeleted();
}
