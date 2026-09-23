package com.library.controller;

import com.library.model.Book;
import com.library.service.BookService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/books")
public class BookServlet extends HttpServlet {

    private BookService bookService;

    @Override
    public void init() {
        bookService = new BookService();
    }

    // GET → display books
    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        List<Book> books = bookService.getAllBooks();

        request.setAttribute("books", books);

        request.getRequestDispatcher(
                "/books.jsp"
        ).forward(request, response);
    }

    // POST → add book
    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String title = request.getParameter("title");
        String isbn = request.getParameter("isbn");
        String publisher = request.getParameter("publisher");
        int publicationYear =
                Integer.parseInt(
                        request.getParameter("publicationYear")
                );
        int totalCopies =
                Integer.parseInt(
                        request.getParameter("totalCopies")
                );

        Book book = new Book();

        book.setTitle(title);
        book.setIsbn(isbn);
        book.setPublisher(publisher);
        book.setPublicationYear(publicationYear);
        book.setTotalCopies(totalCopies);
        book.setAvailableCopies(totalCopies);

        boolean result = bookService.addBook(book);

        if (result) {
            response.sendRedirect(
                    request.getContextPath() + "/books"
            );
        } else {
            response.sendRedirect(
                    request.getContextPath() + "/books?error=true"
            );
        }
    }
}