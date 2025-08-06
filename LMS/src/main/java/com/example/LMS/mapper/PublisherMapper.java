package com.example.LMS.mapper;

import com.example.LMS.dto.PublisherDTO;
import com.example.LMS.entity.Publisher;

public class PublisherMapper {

    public static PublisherDTO toDTO(Publisher publisher) {
        return new PublisherDTO(publisher.getPublisherId(), publisher.getName());
    }

    public static Publisher toEntity(PublisherDTO dto) {
        Publisher publisher = new Publisher();
        publisher.setName(dto.getName());
        return publisher;
    }
}

