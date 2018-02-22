package com.github.sir4ur0n.java7functional.model;

import io.vavr.collection.List;
import java.util.ArrayList;
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
  private java.util.List<Sport> sports = new ArrayList<>();
  @ManyToMany
  private java.util.List<User> friends = new ArrayList<>();

  public List<Sport> getSports() {
    return List.ofAll(sports);
  }

  public List<User> getFriends() {
    return List.ofAll(friends);
  }

  public User withSports(List<Sport> newSports) {
    return new User(id, login, newSports.toJavaList(), friends);
  }

  public User withFriends(List<User> newFriends) {
    return new User(id, login, sports, newFriends.toJavaList());
  }

}
