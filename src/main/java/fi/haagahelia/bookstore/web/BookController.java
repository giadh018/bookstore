package fi.haagahelia.bookstore.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import fi.haagahelia.bookstore.domain.Book;
import fi.haagahelia.bookstore.domain.BookRepository;

@Controller
public class BookController {

    private final BookRepository repository;

    public BookController(BookRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/index")
    @ResponseBody
    public String index() {
        return "Welcome to the Bookstore!";
    }

    @GetMapping("/booklist")
    public String bookList(Model model) {
        model.addAttribute("books", repository.findAll());
        return "booklist";
    }

    @GetMapping("/add")
    public String addBook(Model model) {
        model.addAttribute("book", new Book());
        return "addbook";
    }

    @PostMapping("/save")
    public String saveBook(@ModelAttribute Book book) {
        repository.save(book);
        return "redirect:/booklist";
    }

    @PostMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id") Long id) {
        repository.deleteById(id);
        return "redirect:/booklist";
    }
    @GetMapping("/edit/{id}")
    public String editBook(@PathVariable("id") Long id, Model model) {
        Book book = repository.findById(id).orElseThrow();
        model.addAttribute("book", book);
        return "editbook";
    }
}