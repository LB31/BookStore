package controllers;

import java.io.File;
import java.util.Set;

import javax.inject.Inject;

import models.Book;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Http;
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
		// Get returns the object out of the form
		Book book = bookForm.get();
		Book.addBook(book);
		return redirect(routes.BooksController.indexBooks());
		// Also possible
		// return redirect("/books");
	}

	public Result editBook(Integer id) {
		Book book = Book.findById(id);
		if (book == null)
			return notFound("Book not found");
		Form<Book> bookForm = formFactory.form(Book.class).fill(book);
		return ok(edit.render(bookForm));
	}

	// Sends the edit to the data base
	public Result updateBook() {
		Form<Book> bookForm = formFactory.form(Book.class).bindFromRequest();
		Book book = bookForm.get();
		Book oldBook = Book.findById(book.id);
		if (oldBook == null)
			return notFound("Book not found");
		oldBook.updateAllValues(book);
		return redirect(routes.BooksController.indexBooks());
	}

	public Result showBook(Integer id) {
		Book book = Book.findById(id);
		if (book == null)
			return notFound("Book not found");
		return ok(show.render(book));

	}

	public Result destroyBook(Integer id) {
		Book book = Book.findById(id);
		if (book == null)
			return notFound("Book not found");
		Book.remove(book);
		return redirect(routes.BooksController.indexBooks());
	}

	public Result showTest(String page) {
		Book book = new Book();
		return ok(page).as("text/html");
	}
	
	public Result manageUpload() {
		return ok(upload.render());
	}
	
	public Result upload() {
	    Http.MultipartFormData<File> body = request().body().asMultipartFormData();
	    Http.MultipartFormData.FilePart<File> picture = body.getFile("picture");
	    if (picture != null) {
	        String fileName = picture.getFilename();
	        String contentType = picture.getContentType();
	        File file = picture.getFile();
	        
	        System.out.println(file.toString());
	        return ok("File uploaded");
	    } else {
	        flash("error", "Missing file");
	        return badRequest();
	    }
	}
}
