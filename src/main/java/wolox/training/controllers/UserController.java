package wolox.training.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import wolox.training.TrainingApplication;
import wolox.training.exceptions.BookNotFoundException;
import wolox.training.exceptions.UserIdMismatchException;
import wolox.training.exceptions.UserNotFoundException;
import wolox.training.models.Book;
import wolox.training.models.User;
import wolox.training.repositories.BookRepository;
import wolox.training.repositories.UserRepository;

@RestController
@RequestMapping("/api/users")
public class UserController {

	Gson gson = new GsonBuilder().setPrettyPrinting().create();
	private Logger log = Logger.getLogger(UserController.class);

	@Autowired
	private UserRepository userRepository;	
	
	@Autowired
	private BookRepository bookRepository;
	
	@GetMapping
	public Iterable<User> findAll() {
		System.out.println("En UserController -> findAll");
		return userRepository.findAll(); 
	}

	@GetMapping("/{id}")
	public User findOne(@PathVariable(required = true) Long id) {
		log.info("Receive a GET request to find User  with id " + id );
		return userRepository.findById(id)
				.orElseThrow(() -> {
					log.error("No se encontro el usuario de id = " + id.toString());
					return new UserNotFoundException("No se encontro el usuario de id=" + id.toString());
				});
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public User createUser(@RequestBody User user) {
		log.info("Receive a POST request to create User ");
		log.info("Body :\n" + gson.toJson(user));
		return userRepository.save(user);
	}

	@GetMapping
	@RequestMapping(params = "userName")
	public User findByUserName(@RequestParam(name = "userName", required = true) String userName) {
		return userRepository.findFirstByUserName(userName)
				.orElseThrow(() -> new UserNotFoundException("No existe el usuario con userName=" + userName));
	}


	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void deleteUser(@PathVariable(required = true) Long id) {
		System.out.println("En UserController -> deleteUser (id=" + id.toString() + ")");
		userRepository.findById(id)
			.orElseThrow(() -> new UserNotFoundException("No existe el usuarios de id=" + id.toString()));
		userRepository.deleteById(id);
	}
	 
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public User updateUser(@RequestBody User user, @PathVariable Long id) {
		System.out.println("En UserController -> updateUser (id=" + id.toString() + ")");
		if (!id.equals(user.getId())) {
			throw new UserIdMismatchException("Id invalido");
		}
		userRepository.findById(id)
			.orElseThrow(() -> new UserNotFoundException("No existe el libro de id=" + id.toString()));
		return userRepository.save(user);
	}
	
	@PatchMapping("/{userId}/book/{bookId}")
	@ResponseStatus(HttpStatus.OK)
	public User agregarLibro(@PathVariable(required = true) Long userId, @PathVariable(required = true) Long bookId) {
		User user = userRepository.findById(userId)
					.orElseThrow(() -> new UserNotFoundException("No existe el usuarios de id=" + userId.toString()));
		Book book = bookRepository.findById(bookId)
				.orElseThrow(() -> new BookNotFoundException("No existe el libro de id=" + bookId.toString()));
		user.addBook(book);		
		return userRepository.save(user);
	}

	@DeleteMapping("/{userId}/book/{bookId}")
	@ResponseStatus(HttpStatus.OK)
	public User eliminarLibro(@PathVariable(required = true) Long userId, @PathVariable(required = true) Long bookId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("No existe el usuarios de id=" + userId.toString()));
		Book book = bookRepository.findById(bookId)
				.orElseThrow(() -> new BookNotFoundException("No existe el libro de id=" + bookId.toString()));		
		user.removeBook(book);
		return userRepository.save(user);
	}
	
}
