package com.github.sir4ur0n.java7functional.repository;

import com.github.sir4ur0n.java7functional.model.User;
import io.vavr.collection.List;
import io.vavr.control.Option;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

  List<User> findAll();

  Option<User> findByLogin(String login);

}
