package com.example.LMS.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class BookAuth {
    @EmbeddedId
    private BookAuthId id;

    @ManyToOne
    @MapsId("authId")
    @JoinColumn(name = "auth_Id")
    private Author author;

    @ManyToOne
    @MapsId("bookId")
    @JoinColumn(name = "book_Id")
    private Book book;
}
