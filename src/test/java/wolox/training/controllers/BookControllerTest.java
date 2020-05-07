package wolox.training.controllers;


import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;
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

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;


import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;


@WebMvcTest(BookController.class)
public class BookControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private BookRepository repo;

	@BeforeAll
	public static void init() {
	}
	
	@Test
	@Order(1)
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
	@Order(2)
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
	@Order(3)
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
	@Order(5)
	public void WhenPostBook_ThenReturnHttpStatusCREATED() throws Exception {
		Book bookTDVC = new Book("Thriller","Dan Brown","ImageRL2","The Da Vinci Code","","Scribner","2003",689,"9780307474278");		
		given(repo.save(any())).willReturn(bookTDVC);
		mockMvc.perform(post("/api/books")				
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.content(asJsonString(bookTDVC)))
					.andDo(print())				
					.andExpect(status().isCreated())
					.andExpect(MockMvcResultMatchers.jsonPath("$.isbn").exists());
	}
	
	@Test
	@Order(5)
	public void WhenDeleteBook_ThenReturnHttpStatusOK( ) throws Exception {
		Book bookTDVC = new Book("Thriller","Dan Brown","ImageRL2","The Da Vinci Code","","Scribner","2003",689,"9780307474278");		
		given(repo.findById(any())).willReturn(Optional.of(bookTDVC));
		mockMvc.perform(delete("/api/books/1"))				
					.andDo(print())				
					.andExpect(status().isOk())					
					.andExpect(jsonPath("$").doesNotExist());
	}	
	
	@Test
	@Order(6)
	public void WhenDeleteBook_ThenReturnHttpStatusNOT_FOUND( ) throws Exception {		
		given(repo.findById(any())).willReturn(Optional.empty());
		mockMvc.perform(delete("/api/books/1"))
			.andDo(print())
			.andExpect(status().isNotFound())
			.andExpect(content().string("No existe el libro de id=1"));
	}	
	
	@Test
	@Order(7)
	public void WhenPutBook_ThenReturnHttpStatusOK( ) throws Exception {		
		Book bookTDVC = new Book("Thriller","Dan Brown","ImageRL2","The Da Vinci Code","","Scribner","2003",689,"9780307474278");
		Book bookTDVCmod = new Book("Thriller","Dan Brown","ImageRL2","The Da Vinci Code","modified","Scribner","2003",689,"9780307474278");
		given(repo.findById(any())).willReturn(Optional.of(bookTDVC));
		given(repo.save(any())).willReturn(bookTDVCmod);
		mockMvc.perform(put("/api/books/0")
					.contentType(MediaType.APPLICATION_JSON)
					.characterEncoding("UTF-8")
					.content(asJsonString(bookTDVCmod)))
						.andDo(print())
						.andExpect(status().isOk())						
						.andExpect(MockMvcResultMatchers.jsonPath("$.subtitle").value("modified"));				
	}	

	@Test
	@Order(8)
	public void WhenPutBook_ThenReturnHttpStatusBAD_REQUEST( ) throws Exception {
		Book bookTDVC = new Book("Thriller","Dan Brown","ImageRL2","The Da Vinci Code","","Scribner","2003",689,"9780307474278");
		mockMvc.perform(put("/api/books/1")
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.content(asJsonString(bookTDVC)))
					.andDo(print())
					.andExpect(status().isBadRequest())
					.andExpect(content().string("Id invalido"));		
	}
	
	public static String asJsonString(final Object obj) {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}	
	
}
