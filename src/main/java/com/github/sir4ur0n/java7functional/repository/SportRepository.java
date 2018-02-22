package com.github.sir4ur0n.java7functional.repository;

import com.github.sir4ur0n.java7functional.model.Sport;
import io.vavr.collection.List;
import org.springframework.data.repository.CrudRepository;

public interface SportRepository extends CrudRepository<Sport, Long> {

  List<Sport> findByNameLike(String name);

}
