package com.tokioschool.flight.app.base.domain;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Builder
@Value

public class Book {
    private Integer id;
    private String title;
    private String genre;
    private List<Author> authors;
}
