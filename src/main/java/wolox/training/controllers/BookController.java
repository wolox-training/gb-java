package wolox.training.controllers;

import java.util.Optional;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import wolox.training.TrainingApplication;
import wolox.training.dtos.OpenLibraryBook;
import wolox.training.exceptions.BookIdMismatchException;
import wolox.training.exceptions.BookNotFoundException;
import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;
import wolox.training.services.OpenLibraryService;

@RestController
@RequestMapping("/api/books")
public class BookController {
	private Logger log = Logger.getLogger(TrainingApplication.class);
	
	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private OpenLibraryService openLibraryService;

	@GetMapping("/all")
	public Iterable<Book> findAll() {		
		log.info("En BookController -> findAll");
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
		log.info("En BookController -> findByAuthor");
		// Sorting with a Sort Parameter
		/*
		  Sort sortByYear = Sort.by("year").descending(); Book book
		  bookRepository.findByAuthorAndSort(author, sortByYear);
		 */
		// Sorting With the OrderBy Method Keyword
		return bookRepository.findFirstByAuthorOrderByYear(author)
		        .orElseThrow(() -> new BookNotFoundException("No se encontro el ultimo libro del autor " + author));
	}

	@GetMapping
	@RequestMapping(params = "isbn")
	public ResponseEntity<Book> findByIsbn(@RequestParam(name = "isbn", required = true) String isbn) {
		Optional<Book> optionalBook = bookRepository.findFirstByIsbn(isbn);
		if (!optionalBook.isPresent()) {
			OpenLibraryBook openLibraryBook = openLibraryService.bookInfo(isbn)
					.orElseThrow(() -> new BookNotFoundException("No se encontro libro con isbn=" + isbn ));			
			Book book = new Book();
			book.setIsbn(isbn);
			book.setTitle(openLibraryBook.getTitle());			
			book.setSubtitle(Optional.ofNullable(openLibraryBook.getSubtitle()).orElse(""));				
			book.setGenre(Optional.ofNullable(openLibraryBook.getSubjects().get(0).getName()).orElse(""));
			book.setPages(openLibraryBook.getNumberOfPages()); 
			book.setPublisher(Optional.ofNullable(openLibraryBook.getPublishers().get(0).getName()).orElse(""));
			book.setYear(Optional.ofNullable(openLibraryBook.getPublishDate()).orElse(""));
			book.setImage(Optional.ofNullable(openLibraryBook.getCover().getLarge()).orElse(""));			
			book.setAuthor(openLibraryBook.getAuthors().get(0).getName());			
			return new ResponseEntity<Book>(bookRepository.save(book), HttpStatus.CREATED);

		} else {
			return new ResponseEntity<Book>(optionalBook.get(), HttpStatus.OK);
		}
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Book createBook(@RequestBody Book book) {
		log.info("En BookController -> createBook");
		return bookRepository.save(book);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void deleteBook(@PathVariable(required = true) Long id) {
		log.info("En BookController -> deleteBook (id=" + id.toString() + ")");
		bookRepository.findById(id)
		        .orElseThrow(() -> new BookNotFoundException("No existe el libro de id=" + id.toString()));
		bookRepository.deleteById(id);
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Book updateBook(@RequestBody Book book, @PathVariable Long id) {
		log.info("En BookController -> updateBook (id=" + id.toString() + ")");
		if (!id.equals(book.getId())) {
			throw new BookIdMismatchException("Id invalido");
		}
		bookRepository.findById(id)
		        .orElseThrow(() -> new BookNotFoundException("No existe el libro de id=" + id.toString()));
		return bookRepository.save(book);
	}

}
