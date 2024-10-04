package com.example.repo;

import com.example.entity.Author;
import com.example.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepo extends JpaRepository<Author, Long> {

    @Query("SELECT b FROM Book b WHERE b.author.id = :authorId")
    List<Book> findAllBooksByAuthorId(@Param("authorId") Long authorId);

}
