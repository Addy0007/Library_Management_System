package com.example.LMS.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class BookCat {

    @EmbeddedId
    private BookCatId id;

    @ManyToOne
    @MapsId("bookId") // maps to id.bookId
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne
    @MapsId("catId") // maps to id.catId
    @JoinColumn(name = "cat_id")
    private Category category;
}
