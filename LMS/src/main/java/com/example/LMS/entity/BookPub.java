package com.example.LMS.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class BookPub {
    @EmbeddedId
    private BookPubID id;

    @ManyToOne
    @MapsId("publisherId")
    @JoinColumn(name = "publisher_Id")
    private Publisher publisher;

    @ManyToOne
    @MapsId("bookId")
    @JoinColumn(name = "book_Id")
    private Book book;
}

