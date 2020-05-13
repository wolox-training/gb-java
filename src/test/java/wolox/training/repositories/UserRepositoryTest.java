package wolox.training.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;



import wolox.training.models.User;

@DataJpaTest
public class UserRepositoryTest {
	
	@Autowired
	UserRepository userRepository;
	
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	@BeforeEach
	public void init() {		
		
		User user1 = new User("A000001", "Tony Stark", LocalDate.parse("1970-05-29", formatter));
		userRepository.save(user1);
		User user2 = new User("A000002", "Steve Rogers", LocalDate.parse("1918-06-04", formatter));
		userRepository.save(user2);
		User user3 = new User("A000003", "Clark Kent", LocalDate.parse("1977-04-18", formatter));
		userRepository.save(user3);
		User user4 = new User("A000004", "Bruce Wayne", LocalDate.parse("1963-02-19", formatter));
		userRepository.save(user4);
		User user5 = new User("A000005", "Damian Wayne", LocalDate.parse("2007-08-09", formatter));
		userRepository.save(user5);		 
	}

	@Test
	@Order(1)
	public void Given_loaded_repositoryWhen_search_all_usersThen_find_5_users() {
		Iterable<User> users = userRepository.findAll();	
		int nOfUsers = 5;
		assertThat(users).hasSize(nOfUsers);
	}
	
	@Test
	@Order(2)
	public void Given_loaded_repositoryWhen_search_by_an_existent_userNameThen_find_1_user() {
		assertThat(userRepository.findFirstByUserName("A000001").isPresent());
	}

	@Test
	@Order(2)
	public void Given_loaded_repositoryWhen_search_by_a_non_existent_userNameThen_not_find_a_user() {
		assertThat(!userRepository.findFirstByUserName("A000005").isPresent());
	}

	@Test
	@Order(3)
	public void GivenAllParams_WhenAllUserWithNameLikeXAndBirthdateBetweenDates_ThenReturn2Users() {		
		LocalDate d1 = LocalDate.parse("1900-01-01", formatter);
		LocalDate d2 = LocalDate.parse("2020-12-31", formatter);
		String namePart = "wayne";		
		assertThat(userRepository.findByBirthDateBetweenAndNameLikeSomething(d1, d2, namePart)).hasSize(2);		
	}
	
	@Test
	@Order(4)
	public void GivenAllParams_When_AllUserWithNameContainsXAndBirthdateBetweenDates_ThenReturn2Users() {
		LocalDate d1 = LocalDate.parse("1900-01-01", formatter);
		LocalDate d2 = LocalDate.parse("2020-12-31", formatter);
		String namePart = "Wayne";		
		assertThat(userRepository.findByBirthDateBetweenAndNameContainingIgnoreCase(d1, d2, namePart)).hasSize(2);		
	}

	@Test
	@Order(5)
	public void GivenNullNameParam_WhenAllUserWithNameLikeXAndBirthdateBetweenDates_ThenReturn5Users() {        
	    LocalDate d1 = LocalDate.parse("1900-01-01", formatter);
	    LocalDate d2 = LocalDate.parse("2020-12-31", formatter);      
        assertThat(userRepository.findByBirthDateBetweenAndNameLikeSomething(d1, d2, null)).hasSize(5);     
    }

    @Test
    @Order(6)
    public void GivenNullDatesParams_WhenAllUserWithNameLikeXAndBirthdateBetweenDates_ThenReturn5Users() {
        String namePart = "wayne";
        assertThat(userRepository.findByBirthDateBetweenAndNameLikeSomething(null, null, namePart)).hasSize(2);     
    }	
}
