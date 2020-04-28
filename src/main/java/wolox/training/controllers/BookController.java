package wolox.training.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import net.bytebuddy.implementation.bytecode.Throw;
import wolox.training.exceptions.BookIdMismatchException;
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
	@RequestMapping(params = "title")
	public Book findByTitle(@RequestParam(name = "title", required = true) String title) {				
		return bookRepository.findByTitle(title)
				.orElseThrow(() -> new BookNotFoundException("No se encontro libro de titulo " + title));
	}
	
	@GetMapping("/{id}")	
	public Book findOne(@PathVariable(required = true) Long id) {
		return bookRepository.findById(id)
				.orElseThrow(() -> new BookNotFoundException("No se encontro libro " + id.toString()));
	}
	
	@GetMapping
	@RequestMapping(params = "author")
	public Book findByAuthor(@RequestParam(name = "author", required = true) String author) {
		System.out.println("En BookController -> findByAuthor");
		//Sorting with a Sort Parameter 
		/*
		Sort sortByYear = Sort.by("year").descending();
		Book book bookRepository.findByAuthorAndSort(author, sortByYear);
		 */
		//Sorting With the OrderBy Method Keyword				
		return bookRepository.findFirstByAuthorOrderByYear(author)
					.orElseThrow(() -> new BookNotFoundException("No se encontro el ultimo libro del autor " + author));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Book createBook(@RequestBody Book book) {
		System.out.println("En BookController -> createBook");
		return bookRepository.save(book);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void deleteBook(@PathVariable(required = true) Long id) {
		System.out.println("En BookController -> deleteBook (id=" + id.toString() + ")");
		bookRepository.findById(id)
			.orElseThrow(() -> new BookNotFoundException("No existe el libro de id=" + id.toString()));
		bookRepository.deleteById(id);
	}
	 
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Book updateBook(@RequestBody Book book, @PathVariable Long id) {
		System.out.println("En BookController -> updateBook (id=" + id.toString() + ")");
		if (!id.equals(book.getId())) {
			throw new BookIdMismatchException("Id invalido");
		}
		bookRepository.findById(id)
			.orElseThrow(() -> new BookNotFoundException("No existe el libro de id=" + id.toString()));
		return bookRepository.save(book);
	}	
	
}
