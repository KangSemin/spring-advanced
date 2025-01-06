package org.example.expert.domain.todo.repository;

import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    @Query("SELECT t FROM Todo t " +
           "LEFT JOIN FETCH t.user " +
           "LEFT JOIN FETCH t.comments " +
           "LEFT JOIN FETCH t.managers " +
           "ORDER BY t.modifiedAt DESC")
    Page<Todo> findAllWithDetailsOrderByModifiedAtDesc(Pageable pageable);

}
