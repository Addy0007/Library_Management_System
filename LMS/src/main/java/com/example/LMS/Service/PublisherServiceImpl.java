package com.example.LMS.Service;

import com.example.LMS.Repository.PublisherRepository;
import com.example.LMS.dto.BookDTO;
import com.example.LMS.dto.PublisherDTO;
import com.example.LMS.entity.Publisher;
import com.example.LMS.mapper.BookMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PublisherServiceImpl implements PublisherService {

    private final PublisherRepository publisherRepository;

    public PublisherServiceImpl(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    private PublisherDTO convertToDTO(Publisher publisher) {
        return new PublisherDTO(publisher.getPublisherId(), publisher.getName());
    }

    private Publisher convertToEntity(PublisherDTO dto) {
        Publisher publisher = new Publisher();
        publisher.setPublisherId(dto.getPublisherId());
        publisher.setName(dto.getName());
        return publisher;
    }

    @Override
    public PublisherDTO savePublisher(PublisherDTO publisherDTO) {
        Publisher publisher = convertToEntity(publisherDTO);
        Publisher saved = publisherRepository.save(publisher);
        return convertToDTO(saved);
    }

    @Override
    public List<PublisherDTO> getAllPublishers() {
        return publisherRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PublisherDTO getPublisherById(Long id) {
        return publisherRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    @Override
    public void deletePublisherById(Long id) {
        publisherRepository.deleteById(id);
    }

    @Override
    public PublisherDTO updatePublisherById(Long id, PublisherDTO updatedPublisherDTO) {
        return publisherRepository.findById(id)
                .map(existing -> {
                    existing.setName(updatedPublisherDTO.getName());
                    Publisher updated = publisherRepository.save(existing);
                    return convertToDTO(updated);
                })
                .orElse(null);
    }

    @Override
    public List<BookDTO> getBooksByPublisherId(Long publisherId) {
        return publisherRepository.findById(publisherId)
                .map(pub -> pub.getPublishedBooks().stream()
                        .map(BookMapper::toDTO)
                        .collect(Collectors.toList()))
                .orElse(List.of());
    }


    @Override
    public List<BookDTO> getBooksByPublisherName(String name) {
        return publisherRepository.findByNameIgnoreCase(name).stream()
                .flatMap(pub -> pub.getPublishedBooks().stream())
                .map(BookMapper::toDTO)
                .collect(Collectors.toList());
    }

}
