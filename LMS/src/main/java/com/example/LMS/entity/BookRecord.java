package com.example.LMS.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class BookRecord {
    @EmbeddedId
    private BookRecordID id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_Id")
    private Users users;

    @ManyToOne
    @MapsId("bookId")
    @JoinColumn(name = "book_Id")
    private Book book;
}
