package com.example.LMS.Controller;

import com.example.LMS.Service.PublisherService;
import com.example.LMS.dto.PublisherDTO;
import com.example.LMS.entity.Publisher;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/publisher")
public class PublisherController {

     private final  PublisherService publisherService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<PublisherDTO> savePublisher(@RequestBody PublisherDTO publisherDTO){
        PublisherDTO savedPublisher=publisherService.savePublisher(publisherDTO);
        return ResponseEntity.ok(savedPublisher);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'STUDENT')")
    @GetMapping
    public ResponseEntity<List<PublisherDTO>> getAllPublishers(){
        return ResponseEntity.ok(publisherService.getAllPublishers());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'STUDENT')")
    @GetMapping("/{id}")
    public ResponseEntity<PublisherDTO> getAllPublishersById(@PathVariable Long id){
        return ResponseEntity.ok(publisherService.getPublisherById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PublisherDTO> updatePublisherById(@PathVariable Long id,@RequestBody PublisherDTO publisherDTO){
        return ResponseEntity.ok(publisherService.updatePublisherById(id,publisherDTO));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        publisherService.deletePublisherById(id);
        return ResponseEntity.noContent().build();
    }
}
