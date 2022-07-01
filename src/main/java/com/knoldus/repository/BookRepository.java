package com.knoldus.repository;

import com.knoldus.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * the type BookRepository interface
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

}
