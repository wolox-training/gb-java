package wolox.training.models;

import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import org.springframework.data.annotation.PersistenceConstructor;

@Entity
public class Book {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
	
	@Column(nullable = true)
	private String genre; 

	@Column(nullable = false)
    private String author;
	
	@Column(nullable = false, unique = true)
    private String image;
	
	@Column(nullable = false, unique = true)
    private String title;
	
	@Column(nullable = false)
	private String subtitle;

	@Column(nullable = false)
	private String publisher;
	
	@Column(nullable = false)
	private String year;
	
	@Column(nullable = false)
	private int pages = 0;
	
	@Column(nullable = false)
	private String isbn;

	@PersistenceConstructor
	public Book() {
		genre = "";
		author = "";
		image = "";
		title = "";
		subtitle = "";
		publisher = "";
		year = "";
		pages = 0;
		isbn = "";
	}
	
	public Book(String genre, String author, String image, String title, String subtitle, String publisher, String	year, int pages, String isbn) {		
		this.genre = genre;
		this.author = author;
		this.image = image;
		this.title = title;
		this.subtitle = subtitle;
		this.publisher = publisher;
		this.year = year;
		this.pages = pages;
		this.isbn = isbn;
	}
	
	public String getAuthor() {
		return author;
	}

	public String getGenre() {
		return genre;
	}

	public long getId() {
		return id;
	}

	public String getImage() {
		return image;
	}

	public String getIsbn() {
		return isbn;
	}

	public int getPages() {
		return pages;
	}

	public String getPublisher() {
		return publisher;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public String getTitle() {
		return title;
	}

	public String getYear() {
		return year;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setYear(String year) {
		this.year = year;
	}
	
}
