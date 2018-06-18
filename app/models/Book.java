package models;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Book {
	public Integer id;
	public String title;
	public Integer price;
	public String author;
	
	public Book(Integer id, String title, Integer price, String author) {
		this.id = id;
		this.title = title;
		this.price = price;
		this.author = author;
	}
	
	public Book() {}
	
	
	private static Set<Book> books;
	
	static {
		books = new HashSet<>();
		books.add(new Book(1, "Java book", 250, "Batman"));
		books.add(new Book(2, "C# book", 56, "Obama"));
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
	
	
	
}
