package com.github.sir4ur0n.java7functional.controller;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Patterns.$None;
import static io.vavr.Patterns.$Some;

import com.github.sir4ur0n.java7functional.model.Sport;
import com.github.sir4ur0n.java7functional.model.User;
import com.github.sir4ur0n.java7functional.repository.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.vavr.collection.List;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
@AllArgsConstructor(onConstructor = @__({@Autowired}))
@Api(tags = "User")
public class UserController {

  private UserRepository userRepository;

  @GetMapping
  public List<UserTO> findAll() {
    return userRepository.findAll()
        .map(UserTO::fromUser);
  }

  @ApiOperation(value = "Get friends sports",
      notes = "The distinct list of sports of the friends of the given user, ordered by name.")
  @GetMapping("{login}/friends_sports")
  public ResponseEntity<List<Sport>> friendsSportsOrderByName(@PathVariable("login") String login) {
    return Match(userRepository.findByLogin(login)).of(
        Case($None(), () -> new ResponseEntity<>(HttpStatus.NOT_FOUND)),
        Case($Some($()), user -> user.getFriends()
            .flatMap(User::getSports)
            .distinct()
            .sortBy(Sport::getName)
            .transform(sports -> new ResponseEntity<>(sports, HttpStatus.OK)))
    );

  }

}
