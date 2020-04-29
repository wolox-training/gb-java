package wolox.training.models;

import javax.validation.constraints.NotNull;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import wolox.training.models.User;


@Entity
public class Book {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
	
	@NotNull
	private String genre; 

	@NotNull
    private String author;
	
	@NotNull
    private String image;
	
	@NotNull
    private String title;
	
	@NotNull
	private String subtitle;

	@NotNull
	private String publisher;
	
	@NotNull
	private String year;
	
	@NotNull
	private int pages = 0;
	
	@NotNull
	private String isbn;
	
	@ManyToMany(mappedBy = "booksList")
    private List<User> usersList;
	
	public Book() {
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

	public List<User> getUsersList() {
		return usersList;
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

	public void setUsersList(List<User> usersList) {
		this.usersList = usersList;
	}

	public void setYear(String year) {
		this.year = year;
	}

}
