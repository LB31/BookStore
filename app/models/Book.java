package models;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Book {
	public Integer id;
	public String title;
	public Integer price;
	public String author;
	
	private static Set<Book> books;
	
	static {
		books = new HashSet<>();
		books.add(new Book(1, "Java book", 250, "Batman"));
		books.add(new Book(2, "C# book", 56, "Obama"));
	}
	
	public Book() {}
	
	public Book(Integer id, String title, Integer price, String author) {
		setId(id);
		setTitle(title);
		setPrice(price);
		setAuthor(author);
	}
	
	
	public void updateAllValues(Book book) {
		setId(book.id);
		setTitle(book.title);
		setPrice(book.price);
		setAuthor(book.author);
	}
	
	
	
	public static Set<Book> allBooks(){
		return books;
	}
	
	public static Book findById(Integer id) {
		for(Iterator<Book> it = books.iterator(); it.hasNext();) {
			Book nextBook = it.next();
			if(nextBook.id.equals(id)){
				return nextBook;
			}
		}
		return null;
	}
	
	public static void addBook(Book book) {
		books.add(book);		
	}
	
	public static boolean remove(Book book) {
		return books.remove(book);
	}
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public static Set<Book> getBooks() {
		return books;
	}

	public static void setBooks(Set<Book> books) {
		Book.books = books;
	}
}
