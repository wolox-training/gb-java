package wolox.training.controllers;

import static org.mockito.Mockito.when;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;

@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
public class BookControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private BookRepository repo;

	@Test
	public void Given_books_When_get_books_Then_return_return_json_array() throws Throwable {
		Book book1 = new Book("Crime", "Jeff Lindsay", "ImageDexter1", "Darkly Dreaming Dexter", "", "Umbriel", "2004",
		        304, "9780752866765");
		Book book2 = new Book("Crime", "Jeff Lindsay", "ImageDexter8", "Dexter Is Dead", "", "Umbriel", "2016", 304,
		        "9780345802590");
		Book book3 = new Book("Mystery", "John Katzenbach", "ImageAnalyst", "The Analyst", "", "Corgi", "2002", 491,
		        "9780552150217");
		List<Book> allBooks = Arrays.asList(book1, book2, book3);	
		
		//when(repo.findAll()).thenReturn(allBooks);
		given(repo.findAll()).willReturn(allBooks);		
		/*
		mockMvc.perform(get("/api/books").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
		        .andExpect(jsonPath("$", allBooks.size())).andExpect(jsonPath("$[0].isbn", is(book1.getIsbn())));
		 */
		mockMvc.perform(get("/api/books/all"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		        .andExpect(jsonPath("$[0].isbn").value("9780752866765"));

	}
}
