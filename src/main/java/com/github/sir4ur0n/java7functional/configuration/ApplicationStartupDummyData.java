package com.github.sir4ur0n.java7functional.configuration;

import static io.vavr.API.List;
import static java.util.function.Function.identity;

import com.github.sir4ur0n.java7functional.model.Sport;
import com.github.sir4ur0n.java7functional.model.User;
import com.github.sir4ur0n.java7functional.repository.SportRepository;
import com.github.sir4ur0n.java7functional.repository.UserRepository;
import io.vavr.collection.Map;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor(onConstructor = @__({@Autowired}))
@Log4j2
public class ApplicationStartupDummyData implements ApplicationListener<ApplicationReadyEvent> {

  private static final Map<String, Sport> SPORTS = List(
      "athletics",
      "hunting",
      "climbing",
      "snowboarding",
      "badminton",
      "volley-ball",
      "rugby",
      "boxing",
      "swimming",
      "mountain biking",
      "yoga"
  ).toMap(identity(), new Sport()::withName);

  private SportRepository sportRepository;
  private UserRepository userRepository;

  @Override
  public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
    log.info(() -> "Saving all sports in database");
    sportRepository.save(SPORTS.values());
    log.info(() -> "All sports in database: " + sportRepository.findAll());

    log.info(() -> "Saving all users in database");
    User user1 = userRepository
        .save(new User().withLogin("user1").withSports(sportRepository.findByNameLike("badminton")));
    User user2 = userRepository.save(
        new User().withLogin("user2").withSports(sportRepository.findByNameLike("%ing")).withFriends(List(user1)));
    User user3 = userRepository.save(
        new User().withLogin("user3").withSports(sportRepository.findByNameLike("%m%")).withFriends(List(user1)));
    userRepository.save(user1.withFriends(List(user2, user3)));
    log.info(() -> "All users in database: " + userRepository.findAll().map(User::getLogin));
  }
}
