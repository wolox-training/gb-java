package wolox.training.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;

@RestController
@RequestMapping("/api/books")
public class BookController {

	@Autowired
	private BookRepository bookRepository;
	
	@GetMapping
	public Iterable<Book> findAll() {
        return bookRepository.findAll(); 
	}
	
}
