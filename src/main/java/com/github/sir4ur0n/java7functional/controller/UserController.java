package com.github.sir4ur0n.java7functional.controller;

import com.github.sir4ur0n.java7functional.model.Sport;
import com.github.sir4ur0n.java7functional.model.User;
import com.github.sir4ur0n.java7functional.repository.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
    List<User> users = userRepository.findAll();
    List<UserTO> userTOs = new ArrayList<>();
    for (User user : users) {
      userTOs.add(UserTO.fromUser(user));
    }
    return userTOs;
  }

  @ApiOperation(value = "Get friends sports",
      notes = "The distinct list of sports of the friends of the given user, ordered by name.")
  @GetMapping("{login}/friends_sports")
  public SortedSet<Sport> friendsSportsOrderByName(@PathVariable("login") String login) {
    SortedSet<Sport> result = new TreeSet<>(new Comparator<Sport>() {
      @Override
      public int compare(Sport o1, Sport o2) {
        return o1.getName().compareTo(o2.getName());
      }
    });

    User user = userRepository.findByLogin(login);
    if (user == null) {
      return result;
    }

    List<User> friends = user.getFriends();
    for (User friend : friends) {
      result.addAll(friend.getSports());
    }

    return result;
  }

}
