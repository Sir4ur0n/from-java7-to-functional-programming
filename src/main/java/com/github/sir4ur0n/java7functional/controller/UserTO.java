package com.github.sir4ur0n.java7functional.controller;

import com.github.sir4ur0n.java7functional.model.Sport;
import com.github.sir4ur0n.java7functional.model.User;
import java.util.ArrayList;
import java.util.List;
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
  private List<Sport> sports = new ArrayList<>();
  private List<String> friends = new ArrayList<>();

  public static UserTO fromUser(User user) {
    UserTO userTO = new UserTO()
        .withLogin(user.getLogin())
        .withSports(user.getSports());

    for (User friend : user.getFriends()) {
      userTO.getFriends().add(friend.getLogin());
    }

    return userTO;
  }

}
