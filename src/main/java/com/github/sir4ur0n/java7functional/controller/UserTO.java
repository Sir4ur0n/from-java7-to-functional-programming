package com.github.sir4ur0n.java7functional.controller;

import static io.vavr.API.List;

import com.github.sir4ur0n.java7functional.model.Sport;
import com.github.sir4ur0n.java7functional.model.User;
import io.vavr.collection.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Wither;

@Data
@Setter(AccessLevel.NONE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Wither
public class UserTO {

  private String login = "";
  private List<Sport> sports = List();
  private List<String> friends = List();

  public static UserTO fromUser(User user) {
    return new UserTO()
        .withLogin(user.getLogin())
        .withSports(user.getSports())
        .withFriends(user.getFriends().map(User::getLogin));
  }

}
