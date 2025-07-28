package com.example.LMS.Service;

import com.example.LMS.dto.BookDTO;
import com.example.LMS.dto.PublisherDTO;

import java.util.List;

public interface PublisherService {
    PublisherDTO savePublisher(PublisherDTO publisherDTO);
    List<PublisherDTO> getAllPublishers();
    PublisherDTO getPublisherById(Long id);
    void deletePublisherById(Long id);
    PublisherDTO updatePublisherById(Long id,PublisherDTO updatedPublisherDTO);
    List<BookDTO> getBooksByPublisherId(Long publisherId);
    List<BookDTO> getBooksByPublisherName(String name);

}
