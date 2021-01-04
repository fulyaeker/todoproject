package com.todoproject.repository;

import com.todoproject.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

    @Query("SELECT t from Todo t where t.user_id =:id")
    List<Todo> findByUserId(@Param("id") Long id);

    @Query("SELECT t from Todo t where t.user_id =:id order by t.todo_date asc ")
    List<Todo> findByUserId_DateAsc(@Param("id") Long id);




}