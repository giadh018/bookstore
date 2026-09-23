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
import org.springframework.web.bind.annotation.RequestParam;
import fi.haagahelia.bookstore.domain.CategoryRepository;

@Controller
public class BookController {

    private final BookRepository repository;
    private final CategoryRepository categoryRepository;

    public BookController(BookRepository repository, CategoryRepository categoryRepository) {
        this.repository = repository;
        this.categoryRepository = categoryRepository;
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
        model.addAttribute("categories", categoryRepository.findAll());
        return "addbook";
    }

    @PostMapping("/save")
    public String saveBook(@ModelAttribute Book book,
                        @RequestParam Long categoryId) {
        book.setCategory(categoryRepository.findById(categoryId).orElseThrow());
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
        model.addAttribute("categories", categoryRepository.findAll());
        return "editbook";
    }
}