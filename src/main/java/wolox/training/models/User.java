package wolox.training.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;


import wolox.training.exceptions.BookAlreadyOwnedException;

@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@NotNull
	private String userName;
	
	@NotNull
	private String name;
	
	@NotNull
	private LocalDate birthDate;
	
	@NotNull
	@ManyToMany(cascade = CascadeType.ALL)
	/*
	@JoinTable(name = "book_user", joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"), 
	inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
	*/
	
	@JoinTable(name = "USER_BOOKS",
	joinColumns = @JoinColumn(name = "userid", referencedColumnName = "id"), 
				inverseJoinColumns = @JoinColumn(name = "bookId", referencedColumnName = "id"))
	private List<Book> booksList = new ArrayList<Book>();
	
	
	public User(){
		super();
	}
	
	public User(@NotNull String userName, @NotNull String name, @NotNull LocalDate birthDate) {
		super();
		this.userName = userName;
		this.name = name;
		this.birthDate = birthDate;		
	}
	
	public Long getId() {
		return id;
	}
	
	public String getUserName() {
		return userName;
	}

	public String getName() {
		return name;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public List<Book> getBooks() {
		return (List<Book>) Collections.unmodifiableList(booksList);
	}	

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}
	
	public void agregarLibro(Book book) {
		this.booksList.add(book);
	}
	
	public void eliminarLibro(Long bookId) {		
		for (Book book : this.booksList){
			if (bookId.equals(book.getId())) {
				this.booksList.remove(book);
			}
		}
	}
	
}
