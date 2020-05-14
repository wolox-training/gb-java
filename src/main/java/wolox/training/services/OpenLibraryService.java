package wolox.training.services;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jboss.logging.Logger;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import wolox.training.TrainingApplication;
import wolox.training.dtos.OpenLibraryBook;
import wolox.training.models.Book;

@Service
public class OpenLibraryService {

	Gson gson = new GsonBuilder().setPrettyPrinting().create();
	private Logger log = Logger.getLogger(OpenLibraryService.class);

	public Optional<Book> bookInfo(String isbn) {
		RestTemplate restTemplate = new RestTemplate();		
		String openlibraryResourceUrl = "https://openlibrary.org/api/books?bibkeys=ISBN:{isbn}&format=json&jscmd=data";
		HashMap<String, String> params = new HashMap<>();
		params.put("isbn", isbn);
		log.info("Try to do a GET request to Open Library");
		log.info(" URL -> " + openlibraryResourceUrl);
		log.info(" params : \n" + gson.toJson(params));
		Map<String, OpenLibraryBook> response = restTemplate.exchange(openlibraryResourceUrl, HttpMethod.GET, null,
				new ParameterizedTypeReference<Map<String, OpenLibraryBook>>() { }, params).getBody();
		Optional<OpenLibraryBook> optionalOpenLibraryBook = Optional.ofNullable(response.get("ISBN:" + isbn));		
		
		if (optionalOpenLibraryBook.isPresent() ) {			
			OpenLibraryBook openLibraryBook = optionalOpenLibraryBook.get();
			
			Book book = new Book();
			book.setIsbn(isbn);
			book.setTitle(openLibraryBook.getTitle());
			book.setSubtitle(Optional.ofNullable(openLibraryBook.getSubtitle()).orElse(""));			
			book.setAuthor(openLibraryBook.getAuthors().get(0).getName());
			book.setAuthor(optionalOpenLibraryBook
			        .map(openLibraryBookMapped -> openLibraryBookMapped.getAuthors())
			        .map(authors -> authors.get(0))
			        .map(author -> author.getName())
			        .orElse("Sin Autor"));			
			book.setGenre(optionalOpenLibraryBook
					.map(openLibraryBookMapped -> openLibraryBookMapped.getSubjects())
					.map(subjects -> subjects.get(0))
					.map(subject -> subject.getName())
					.orElse("No informado"));
			book.setPages(openLibraryBook.getNumberOfPages());
			book.setPublisher(optionalOpenLibraryBook
			        .map(openLibraryBookMapped -> openLibraryBookMapped.getPublishers())
			        .map(publishers -> publishers.get(0))
			        .map(publisher -> publisher.getName())
			        .orElse("No informado"));			
			book.setYear(Optional.ofNullable(openLibraryBook.getPublishDate()).orElse(""));
			book.setImage(Optional.ofNullable(openLibraryBook.getCover().getLarge()).orElse(""));
			return Optional.of(book);	
		} else {
			return Optional.empty();
		}
	}
}
