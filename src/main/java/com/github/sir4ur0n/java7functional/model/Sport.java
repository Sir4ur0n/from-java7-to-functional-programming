package com.github.sir4ur0n.java7functional.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Entity
public class Sport {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @JsonIgnore
  private long id;
  private String name = "";

}
