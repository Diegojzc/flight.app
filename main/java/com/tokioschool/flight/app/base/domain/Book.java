package com.tokioschool.flight.app.base.domain;

import lombok.*;

import java.util.List;

@Builder
@Value
@Setter

public class Book {
    Integer id;
    String title;
    String genre;
    List<Author> authors;


    public void setTitle(String title) {
    }

    public void setGenre(String genre) {
    }

    public void setAuthors(List<Integer> authorId) {
    }
}
