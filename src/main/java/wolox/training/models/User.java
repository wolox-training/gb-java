package wolox.training.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import wolox.training.exceptions.BookAlreadyOwnedException;

@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@NotNull
	@Size(min = 6, message="Debe ser de al menos 6 caracteres de longitud")
	private String userName;
	
	@NotNull
	private String name;
	
	@NotNull
	@Past(message = "No puede ser una fecha futura!")
	private LocalDate birthDate;
	
	@ManyToMany(cascade={CascadeType.REFRESH, CascadeType.MERGE})
	@JoinTable(name = "USER_BOOKS",
	joinColumns = @JoinColumn(name = "userId", referencedColumnName = "id"), 
				inverseJoinColumns = @JoinColumn(name = "bookId", referencedColumnName = "id"))
	private List<Book> books = new ArrayList<Book>();
	
	
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
		return (List<Book>) Collections.unmodifiableList(books);
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
	
	public List<Book> addBook(Book userBook) throws BookAlreadyOwnedException {
		if (books.contains(userBook)) {
			throw new BookAlreadyOwnedException();
		} else {
			this.books.add(userBook);
		}
		return (List<Book>) Collections.unmodifiableList(books);
	}
	
	public void removeBook(Book userBook) {
		this.books.remove(userBook);
	}
	
}