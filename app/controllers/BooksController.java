package controllers;

import java.util.Set;

import javax.inject.Inject;

import models.Book;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import play.data.Form;
import views.html.books.*;


public class BooksController extends Controller {

	@Inject
	FormFactory formFactory;
	
	public Result indexBooks() {
		Set<Book> books = Book.allBooks();
		return ok(index.render(books));
	}
	
	public Result createBook() {
		Form<Book> bookForm = formFactory.form(Book.class);
		return ok(create.render(bookForm));
	}
	
	public Result saveBook() {
		Form<Book> bookForm = formFactory.form(Book.class).bindFromRequest();
		Book book = bookForm.get();
		System.out.println(bookForm.allErrors() + " errors");
		book.price = 20;
		Book.addBook(book);
		return redirect(routes.BooksController.indexBooks());
	}
	
	public Result editBook(Integer id) {
		return TODO;
	}
	
	// Sends the edit to the data base
	public Result updateBook() {
		return TODO;
	}
	
	public Result destroyBook(Integer id) {
		return TODO;
	}
	
	public Result showBook(Integer id) {
		return TODO;
	}
	
}
