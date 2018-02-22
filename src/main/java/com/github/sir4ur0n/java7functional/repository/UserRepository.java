package com.github.sir4ur0n.java7functional.repository;

import com.github.sir4ur0n.java7functional.model.User;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

  List<User> findAll();

  User findByLogin(String login);

}
