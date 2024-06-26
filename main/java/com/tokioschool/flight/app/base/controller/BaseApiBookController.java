package com.tokioschool.flight.app.base.controller;

import com.tokioschool.flight.app.base.dto.BookDTO;
import com.tokioschool.flight.app.base.dto.BookSearchRequestDTO;
import com.tokioschool.flight.app.base.dto.PageDTO;
import com.tokioschool.flight.app.base.service.BookService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/base/api")
@Validated
public class BaseApiBookController {

  private final BookService bookService;

  @GetMapping("/books")
  public ResponseEntity<PageDTO<BookDTO>> searchBooks(
      @Valid @RequestParam(value = "genre", required = false) String genre,
      @Valid @RequestParam(value = "page", required = false, defaultValue = "0") int page,
      @Valid
          @Min(1)
          @Max(100)
          @RequestParam(value = "page_size", required = false, defaultValue = "10")
          int pageSize) {

    PageDTO<BookDTO> bookDTOPageDTO =
        bookService.searchBooksByPageIdAndPageSize(
            BookSearchRequestDTO.builder().genre(genre).page(page).pageSize(pageSize).build());

    return ResponseEntity.ok(bookDTOPageDTO);
  }
}
