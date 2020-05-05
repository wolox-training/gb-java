package wolox.training.controllers;

import static org.mockito.Mockito.when;
import static org.mockito.BDDMockito.given;
import static org.hamcrest.Matchers.hasSize;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;


import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;

@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
public class BookControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private BookRepository repo;

	@Before
	public void init() {
	}
	
	@Test
	public void GivenBooks_WhenGetBooks_ThenReturnJsonArray() throws Exception {
		Book book1 = new Book("Crime", "Jeff Lindsay", "ImageDexter1", "Darkly Dreaming Dexter", "", "Umbriel", "2004",
		        304, "9780752866765");
		Book book2 = new Book("Crime", "Jeff Lindsay", "ImageDexter8", "Dexter Is Dead", "", "Umbriel", "2016", 304,
		        "9780345802590");
		Book book3 = new Book("Mystery", "John Katzenbach", "ImageAnalyst", "The Analyst", "", "Corgi", "2002", 491,
		        "9780552150217");
		List<Book> allBooks = Arrays.asList(book1, book2, book3);		
		//when(repo.findAll()).thenReturn(allBooks);
		given(repo.findAll()).willReturn(allBooks);
		mockMvc.perform(get("/api/books/all"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		        .andExpect(jsonPath("$[0].isbn").value("9780752866765"))
				.andExpect(jsonPath("$", hasSize(3)));
		
	}

	@Test
	public void GivenBooks_WhenGetBookByTitle_ThenReturnJsonBook() throws Throwable {
		Book book = new Book("Crime", "Jeff Lindsay", "ImageDexter1", "Darkly Dreaming Dexter", "", "Umbriel", "2004",
		        304, "9780752866765");
		String title = "Darkly Dreaming Dexter";
		given(repo.findByTitle(title) ).willReturn(Optional.of(book));		
		mockMvc.perform(get("/api/books")
			.param("title", title))	
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.isbn").value("9780752866765"));
	}

	@Test
	public void GivenBooks_WhenGetBookById_ThenReturnJsonBook() throws Exception {
		Book book = new Book("Crime", "Jeff Lindsay", "ImageDexter1", "Darkly Dreaming Dexter", "", "Umbriel", "2004",
		        304, "9780752866765");
		given(repo.findById(Long.valueOf("1"))).willReturn(Optional.of(book));
		mockMvc.perform( MockMvcRequestBuilders
	      .get("/api/books/{id}", 1)
	      .accept(MediaType.APPLICATION_JSON))
	      .andDo(print())
	      .andExpect(status().isOk())
	      .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value("9780752866765"));
	}
	
	@Test
	public void WhenPostBook_ThenReturnHttpStatusCREATED() throws Exception {		
		Book book = new Book("Crime", "Jeff Lindsay", "ImageDexter1", "Darkly Dreaming Dexter", "", "Umbriel", "2004",
		        304, "9780752866765");		
		given(repo.save(book)).willReturn(book);
		mockMvc.perform(post("/api/books")
				.content(asJsonString(book))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.isbn").exists());
				
	}

	public static String asJsonString(final Object obj) {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}	
	
}
