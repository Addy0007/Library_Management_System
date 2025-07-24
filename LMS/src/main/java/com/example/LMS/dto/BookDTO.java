package com.example.LMS.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {
    private Long bookId;
    private String title;
    private String publisherName;
    private Integer totalBooks;
    private Integer availableBooks;

    private List<Long> authorIds;
    private List<Long> categoryIds;
    private Long publisherId;
    private List<Long> borrowerIds;
}
