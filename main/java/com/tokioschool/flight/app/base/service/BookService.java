package com.tokioschool.flight.app.base.service;


import com.github.javafaker.Faker;
import com.tokioschool.flight.app.base.domain.Author;
import com.tokioschool.flight.app.base.domain.Book;
import com.tokioschool.flight.app.base.dto.BookDTO;
import com.tokioschool.flight.app.base.dto.BookSearchRequestDTO;
import com.tokioschool.flight.app.base.dto.PageDTO;
import com.tokioschool.flight.app.core.exception.NotFoundException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class BookService {
private List<Book> books;
private List<Author> authors;

@PostConstruct
    public void postConstructor(){
    Faker faker = new Faker();

    authors= new ArrayList<>(
            IntStream.range(0,10)
                    .mapToObj(i->Author.builder().id(i).name(faker.book().author()).build()).toList());

    books= new ArrayList<>(
            IntStream.range(0,101)
                    .mapToObj(i->Book.builder()
                            .id(i)
                            .title(faker.book().title())
                            .genre(faker.book().genre())
                            .authors(List.of(authors.get((int)(Math.random()*10))))
                            .build()).toList());

}

Author getAuthorById(int authorId){
    return authors.stream()
            .filter(a->a.getId()== authorId)
            .findFirst()
            .orElseThrow(()->new NotFoundException("Author id:% not found". formatted(authorId)));
}
Book getBookById(int bookId){
    return books.stream()
            .filter(b-> b.getId() == bookId)
            .findFirst()
            .orElseThrow(()->new NotFoundException("Author id:% not found". formatted(bookId)));
}

public BookDTO getBookByBookId(int bookId){
    return toBookDTO(getBookById(bookId));
}

public PageDTO<BookDTO> searchBooksByPageIdAndPageSize(BookSearchRequestDTO bookSearchRequestDTO){
    List<Book> filtererBooks= Optional.ofNullable(StringUtils.trimToNull(bookSearchRequestDTO.getGenre()))
            .map(String::toLowerCase)
            .map(genre->books.stream().filter(book->book.getGenre().toLowerCase().contains(genre))
                    .toList())
            .orElse(books);

    int start = bookSearchRequestDTO.getPage() * bookSearchRequestDTO.getPageSize();

    if(start >= filtererBooks.size()){
        return PageDTO.<BookDTO>builder()
                        .items(List.of())
                .page(bookSearchRequestDTO.getPage())
                .pageSize(bookSearchRequestDTO.getPageSize())
                        .total(filtererBooks.size())

                build()
    }
}

}
