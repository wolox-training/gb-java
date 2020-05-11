package wolox.training.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotBlank;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import wolox.training.models.User;

public interface UserRepository extends CrudRepository<User, Long> {
	Optional<User> findFirstByUserName(@NotBlank String userName);
	
	/**
	 * findByBirthDateBetweenAndNameLikeSomething
	 * 
	 * @param d1
	 * @param d2
	 * @param name
	 * @return
	 */	
	@Query("SELECT u FROM User u WHERE (:d1 is null or :d2 is null or u.birthDate between :d1 and:d2)"
			+ " and (:namePart is null or lower(u.name) like lower(concat('%', :namePart,'%')))")
	public List<User> findByBirthDateBetweenAndNameLikeSomething(@Param("d1") LocalDate d1, @Param("d2") LocalDate d2, @Param("namePart") String name);

	/**
	 * findByBirthDateBetweenAndNameContainingIgnoreCase	
	 * 
	 * @param date1
	 * @param date2
	 * @param name
	 * @return
	 */
	List<User> findByBirthDateBetweenAndNameContainingIgnoreCase(LocalDate date1, LocalDate date2, String name);
}
