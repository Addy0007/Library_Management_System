package com.example.LMS.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {
    @Schema(hidden = true)
    private Long bookId;
    private String title;
    private String publisherName;
    private Integer totalBooks;
    private Integer availableBooks;

    private List<String> authorNames;
    private List<String> categoryNames;
    private List<String> borrowerEmails;
}
