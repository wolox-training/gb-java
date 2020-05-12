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
     * Devuelve usuarios cuya fecha de nacimiento se encuentre entre {@code fromDate} y {@code toDate},
     * y que su nombre sea igual o similar a lo que indica {@code name}. En caso de que uno de los parámetros sea null
     * no se tiene en cuenta en la comparación.
     * 
     * @param fromDate {@link LocalDate} que representa <b>desde</b> en la comparación de fecha de
     *                 nacimiento
     * @param toDate {@link LocalDate} que representa <b>hasta</b> en la comparación de fecha de
     *                 nacimiento
     * @param name El {@link String} que indica la cadena a buscar como parte del nombre
     * @return La lista de usuarios que cumple con las condiciones
     */
	@Query("SELECT u FROM User u WHERE (:fromDate is null or :toDate is null or u.birthDate between :fromDate and:toDate)"
			+ " and (:namePart is null or lower(u.name) like lower(concat('%', :namePart,'%')))")
	public List<User> findByBirthDateBetweenAndNameLikeSomething(@Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate, @Param("namePart") String name);

	/**
     * Devuelve usuarios cuya fecha de nacimiento se encuentre entre {@code fromDate} y {@code toDate},
     * y que su nombre contenga lo que indica {@code name}. En caso de que uno de los parámetros sea null
     * no se tiene en cuenta en la comparación. 
	 * 
	 * @param fromDate {@link LocalDate} que representa <b>desde</b> en la comparación de fecha de
     *                 nacimiento
	 * @param toDate   {@link LocalDate} que representa <b>hasta</b> en la comparación de fecha de
     *                 nacimiento
	 * @param name     El {@link String} que indica la cadena a buscar como parte del nombre
	 * @return
	 */
	List<User> findByBirthDateBetweenAndNameContainingIgnoreCase(LocalDate fromDate, LocalDate toDate, String name);
}
