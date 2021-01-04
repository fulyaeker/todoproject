package com.todoproject.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name ="todos")
public class Todo {
    @Id
    @Column(name = "todo_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long todo_id;
    private String todo_name;
    private String status;
    private Date todo_date;
    private Long user_id;


    public Long getTodo_id() {
        return todo_id;
    }

    public void setTodo_id(Long todo_id) {
        this.todo_id = todo_id;
    }

    public String getTodo_name() {
        return todo_name;
    }

    public void setTodo_name(String todo_name) {
        this.todo_name = todo_name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getTodo_date() {
        return todo_date;
    }

    public void setTodo_date(Date todo_date) {
        this.todo_date = todo_date;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id)
    {
        this.user_id = user_id;
    }
}
