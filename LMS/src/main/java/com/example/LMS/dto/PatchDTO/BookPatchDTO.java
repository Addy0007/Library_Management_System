package com.example.LMS.dto.PatchDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookPatchDTO {
    private String title;
    private String publisherName;
    private Integer totalBooks;
    private Integer availableBooks;

    private List<Long> authorIds;
    private List<Long> categoryIds;
    private Long publisherId;
    private List<Long> borrowerIds;
}
