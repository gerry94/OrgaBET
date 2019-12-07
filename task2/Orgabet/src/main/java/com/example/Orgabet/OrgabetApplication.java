/*package com.example.Orgabet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OrgabetApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrgabetApplication.class, args);
	}

}*/

package com.example.Orgabet;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = PersonRepository.class)
public class OrgabetApplication implements CommandLineRunner {

  @Autowired private MatchRepository matchRepository;

  public static void main(final String args[]) {
    SpringApplication.run(OrgabetApplication.class, args);
  }

  @Override
  public void run(String... strings) throws Exception {
    matchRepository.deleteAll();

    final Quote quote = new Quote("B365", 2.56);
    final Quote quote2 = new Quote("B365", 1.58);
    
    final List<Quote> quotes = Arrays.asList(quote);
    final List<Quote> quotes2 = Arrays.asList(quote2);

    final Odd home = new Odd("H", quotes);
    final Odd under = new Odd("U", quotes2);
    final List<Odd> odds = Arrays.asList(home, under);

    final Match match = new Match("Football", "I1", "Juventus", "Fiorentina", odds);
    matchRepository.save(match);

    System.out.println("Find by home team");
    matchRepository.findByHomeTeam("Juventus").forEach(System.out::println);

    System.out.println("Find by Odd's Type: Home");
    matchRepository.findByOddsType("H").forEach(System.out::println);

    System.out.println("Find by Odd's Type: Under");
    matchRepository.findByOddsType("U").forEach(System.out::println);
  }
}

