package com.example.dao;

import com.example.entity.Book;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository

public class BookDaoImpl implements BookDao{

    private final SessionFactory sessionFactory;

    @Autowired
    public BookDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public List<Book> getAllBooks() {
        CriteriaBuilder criteriaBuilder = sessionFactory.getCriteriaBuilder();
        CriteriaQuery<Book> criteriaQuery = criteriaBuilder.createQuery(Book.class);
        Root<Book> root = criteriaQuery.from(Book.class);
        criteriaQuery.select(root);

        Query<Book> query = sessionFactory.getCurrentSession().createQuery(criteriaQuery);
        return query.getResultList();
    }

    @Transactional
    public void addBook(Book book) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(book);
    }

    @Transactional
    public boolean existsBookById(int id) {
        Session session = sessionFactory.getCurrentSession();
        Book book = session.get(Book.class, id);
        return book != null;
    }

    @Transactional
    public void updateBookById(int id, Book book) {
        Session session = sessionFactory.getCurrentSession();
        Book newBook = session.get(Book.class, id);
        newBook.setAuthor(book.getAuthor());
        newBook.setTitle(book.getTitle());
        newBook.setDescription(book.getDescription());
    }

    @Transactional
    public void deleteBook(int id) {
        Session session = sessionFactory.getCurrentSession();
        Book book = session.get(Book.class, id);
        session.remove(book);
    }

}
