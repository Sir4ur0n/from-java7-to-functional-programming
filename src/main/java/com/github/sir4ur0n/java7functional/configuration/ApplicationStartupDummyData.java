package com.github.sir4ur0n.java7functional.configuration;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

import com.github.sir4ur0n.java7functional.model.Sport;
import com.github.sir4ur0n.java7functional.model.User;
import com.github.sir4ur0n.java7functional.repository.SportRepository;
import com.github.sir4ur0n.java7functional.repository.UserRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

  private static final Map<String, Sport> SPORTS = new HashMap<String, Sport>() {{
    String[] sports = new String[]{"athletics", "hunting", "climbing", "snowboarding", "badminton", "volley-ball",
        "rugby", "boxing", "swimming", "mountain biking", "yoga"};

    for (String sport : sports) {
      put(sport, new Sport().withName(sport));
    }
  }};

  private SportRepository sportRepository;
  private UserRepository userRepository;

  @Override
  public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
    log.info("Saving all sports in database");
    sportRepository.save(SPORTS.values());
    log.info("All sports in database: " + sportRepository.findAll());

    log.info("Saving all users in database");
    User user1 = userRepository
        .save(new User().withLogin("user1").withSports(sportRepository.findByNameLike("badminton")));
    User user2 = userRepository.save(
        new User().withLogin("user2").withSports(sportRepository.findByNameLike("%ing"))
            .withFriends(singletonList(user1)));
    User user3 = userRepository.save(
        new User().withLogin("user3").withSports(sportRepository.findByNameLike("%m%"))
            .withFriends(singletonList(user1)));
    userRepository.save(user1.withFriends(asList(user2, user3)));

    List<User> allUsers = userRepository.findAll();
    List<String> logins = new ArrayList<>();
    for (User user : allUsers) {
      logins.add(user.getLogin());
    }
    log.info("All users in database: " + logins);
  }
}
