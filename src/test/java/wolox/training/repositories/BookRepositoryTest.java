package wolox.training.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import wolox.training.models.Book;

@DataJpaTest
public class BookRepositoryTest {

	@Autowired
	private BookRepository bookRepository;

	@BeforeAll
	public void init() {
		Book book1 = new Book("Crime", "Jeff Lindsay", "ImageDexter1", "Darkly Dreaming Dexter", "", "Umbriel", "2004",
		        304, "9780752866765");
		bookRepository.save(book1);
		Book book8 = new Book("Crime", "Jeff Lindsay", "ImageDexter8", "Dexter Is Dead", "", "Umbriel", "2016", 304,
		        "9780345802590");
		bookRepository.save(book8);
		Book bookTheAnalyst = new Book("Mystery", "John Katzenbach", "ImageAnalyst", "The Analyst", "", "Corgi", "2002",
		        491, "9780552150217");
		bookRepository.save(bookTheAnalyst);
	}

	@Test
	@Order(1)
	public void Given_loaded_repositoryWhen_search_all_booksThen_find_3_books() {
		Iterable<Book> books = bookRepository.findAll();
		int nOfBooks = 3;
		assertThat(books).hasSize(nOfBooks);
	}

	@Test
	@Order(2)
	public void Given_loaded_repositoryWhen_search_author_Jeff_LindsayThen_find_book_of_isbn_9780345802590() {
		String author = "Jeff Lindsay";
		Book foundedBook = bookRepository.findFirstByAuthorOrderByYearDesc(author).orElse(new Book());
		assertThat("9780345802590".equals(foundedBook.getIsbn()));
	}

	@Test
	@Order(3)
	public void Given_loaded_repositoryWhen_search_an_existent_authorThen_find_first_book_of_author_ordered_by_year() {
		String author = "Jeff Lindsay";
		Book foundedBook = bookRepository.findFirstByAuthorOrderByYear(author).orElse(new Book());
		assertThat("9780752866765".equals(foundedBook.getIsbn()));
	}

	@Test
	@Order(4)
	public void Given_loaded_repositoryWhen_search_a_non_existent_authorThen_not_find_books() {
		String author = "Dan Brown";
		assertThat(!bookRepository.findFirstByAuthorOrderByYearDesc(author).isPresent());
	}

	@Test
	@Order(5)
	public void Given_loaded_repositoryWhen_search_all_book_of_an_authorThen_find_2_books_ordered_by_year() {
		String author = "Jeff Lindsay";
		List<Book> bookList = bookRepository.findByAuthorOrderByYear(author);
		assertThat(bookList).hasSize(2);
	}

	@Test
	@Order(6)
	public void Given_loaded_repositoryWhen_search_by_title_Then_find_a_book() {
		Book foundedBook = bookRepository.findByTitle("The Analyst").orElse(new Book());
		assertThat("9780552150217".equals(foundedBook.getIsbn()));
	}

	@Test
	@Order(7)
	public void Given_loaded_repositoryWhen_search_a_non_existent_titleThen_not_find_a_book() {
		String searchedTitle = "IT";
		assertThat(!bookRepository.findByTitle(searchedTitle).isPresent());

	}

}
