package wolox.training.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import wolox.training.exceptions.BookNotFoundException;
import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;

@RestController
@RequestMapping("/api/books")
public class BookController {

	@Autowired
	private BookRepository bookRepository;
	
	@GetMapping("/all")
	public Iterable<Book> findAll() {
		System.out.println("En BookController -> findAll");
		return bookRepository.findAll(); 
	}
	
	@GetMapping
	public Book findByAuthor(@RequestParam String author) {
		System.out.println("En BookController -> findByAuthor");
		//Sorting with a Sort Parameter 
		/*
		Sort sortByYear = Sort.by("year").descending();
		Book book bookRepository.findByAuthorAndSort(author, sortByYear);
		*/
		//Sorting With the OrderBy Method Keyword
		Optional<Book> optionalBook = bookRepository.findFirstByAuthorOrderByYear(author);		
		return optionalBook.orElseThrow(() -> new BookNotFoundException("No se encontro libro del autor " + author));
	}

}
