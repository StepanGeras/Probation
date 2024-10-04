package com.example.controller;

import com.example.entity.Author;
import com.example.entity.Book;
import com.example.exception.book.BookFileNotFoundException;
import com.example.service.AuthorService;
import com.example.service.ImageService;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/author")
public class AuthorController {

    private final AuthorService authorService;
    private final GridFsTemplate gridFsTemplate;
    private final ImageService imageService;

    @Autowired
    public AuthorController(AuthorService authorService, GridFsTemplate gridFsTemplate, ImageService imageService) {
        this.authorService = authorService;
        this.gridFsTemplate = gridFsTemplate;
        this.imageService = imageService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Author>> getAllAuthors() {
        List<Author> authorList = authorService.findAll();
        return ResponseEntity.ok(authorList);
    }

    @PostMapping("/save")
    public ResponseEntity<Author> saveAuthor(@RequestBody Author author) {
        authorService.save(author);
        return ResponseEntity.ok(author);
    }

    @PutMapping("/update")
    public ResponseEntity<Author> updateAuthor(@RequestBody Author author) {
        authorService.update(author);
        return ResponseEntity.ok(author);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Author> deleteAuthorById(@RequestParam long id) {
        authorService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/all/books/author/{id}")
    public ResponseEntity<List<Book>> getAllBookByAuthorId(@PathVariable Long id) {
        List<Book> books = authorService.findAllBooksByAuthorId(id);
        return ResponseEntity.ok(books);
    }

    @PostMapping("/upload/{id}")
    public ResponseEntity<String> uploadAuthor(@PathVariable Long id, @RequestParam("file") MultipartFile file) {

        ObjectId fileId;
        try {
            fileId = gridFsTemplate.store(file.getInputStream(), file.getOriginalFilename(), file.getContentType());
            authorService.updateAuthorImage(id, fileId.toString());
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok("Image uploaded successfully with ID: " + fileId);

    }

    @GetMapping("/download/{id}")
    public ResponseEntity<InputStreamResource> downloadImage(@PathVariable Long id) throws RuntimeException {

        Author author = authorService.findById(id);
        if (author.getImageId() == null) {
            throw new BookFileNotFoundException("No image found for this book");
        }

        GridFSFile gridFSFile = imageService.fileFindGridFs(author.getImageId());

        GridFsResource resource = gridFsTemplate.getResource(gridFSFile);

        try {
            String fileName = URLEncoder.encode(Objects.requireNonNull(resource.getFilename()), StandardCharsets.UTF_8).replace("+", "%20");
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(Objects.requireNonNull(gridFSFile.getMetadata()).getString("_contentType")))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename*=UTF-8''" + fileName)
                    .body(new InputStreamResource(resource.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException("Could not download file", e);
        }

    }

}
