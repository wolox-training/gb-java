package wolox.training.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
		System.out.println("En BookController -> findAll");
		return bookRepository.findAll(); 
	}
	
	@GetMapping("/{author}")
	public ResponseEntity<Object> findByAuthor(@PathVariable String author) {
		System.out.println("En BookController -> findByAuthor");
		/*SpringData: Sorting with a Sort Parameter */
		/*
		Sort sortByYear = Sort.by("year").descending();
		Book book bookRepository.findByAuthorAndSort(author, sortByYear);
		*/
		/*SpringData: Sorting With the OrderBy Method Keyword  */		
		Book book = bookRepository.findFirstByAuthorOrderByYearDesc(author);
		if (book == null) {
			return ResponseEntity.notFound().build();	
		} else {			
			return ResponseEntity.ok(book);
		}		
	}
}
