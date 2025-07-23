package com.example.LMS.Service;

import com.example.LMS.entity.Publisher;

import java.util.List;

public interface PublisherService {
    Publisher savePublisher(Publisher publisher);
    List<Publisher> getAllPublishers();
    Publisher getPublisherById(Long id);
    void deletePublisherById(Long id);
    Publisher updatePublisherById(Long id,Publisher updatedPublisher);
}
