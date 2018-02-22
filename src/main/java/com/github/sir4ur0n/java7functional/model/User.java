package com.github.sir4ur0n.java7functional.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
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
@Entity
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;
  @Wither
  private String login = "";

  @ManyToMany
  private List<Sport> sports = new ArrayList<>();

  @ManyToMany
  private List<User> friends = new ArrayList<>();

  public List<Sport> getSports() {
    return sports;
  }

  public List<User> getFriends() {
    return friends;
  }

  public User withSports(List<Sport> sports) {
    return new User(id, login, sports, friends);
  }

  public User withFriends(List<User> friends) {
    return new User(id, login, sports, friends);
  }
}
