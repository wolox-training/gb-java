package wolox.training.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import wolox.training.models.User;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {
	
	@Autowired
	UserRepository userRepository;

	@Before
    public void init() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");		
		
		User user1 = new User("A000001", "Tony Stark", LocalDate.parse("1970-05-29", formatter));
		userRepository.save(user1);
		User user2 = new User("A000002", "Steve Rogers", LocalDate.parse("1918-06-04", formatter));
		userRepository.save(user2);
		User user3 = new User("A000003", "Clark Kent", LocalDate.parse("1977-04-18", formatter));
		userRepository.save(user3);
		User user4 = new User("A000004", "Bruce Wayne", LocalDate.parse("1963-02-19", formatter));
		userRepository.save(user4);
		
	}

	@Test
	public void Given_loaded_repositoryWhen_search_all_usersThen_find_4_users() {
		Iterable<User> users = userRepository.findAll();	
		int nOfUsers = 4;
		assertThat(users).hasSize(nOfUsers);
	}
	
	@Test
	public void Given_loaded_repositoryWhen_search_by_an_existent_userNameThen_find_1_user() {
		assertThat(userRepository.findFirstByUserName("A000001").isPresent());
	}

	@Test
	public void Given_loaded_repositoryWhen_search_by_a_non_existent_userNameThen_not_find_a_user() {
		assertThat(!userRepository.findFirstByUserName("A000005").isPresent());
	}

}
