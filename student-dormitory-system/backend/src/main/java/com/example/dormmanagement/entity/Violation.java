package com.example.dormmanagement.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "violation")
public class Violation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @Lob
    private String description;
    private LocalDate occDate;
    private Boolean processed;
    private String handlerNote;
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() { this.createdAt = LocalDateTime.now(); if (processed==null) processed=false; }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public LocalDate getOccDate() { return occDate; }
    public void setOccDate(LocalDate occDate) { this.occDate = occDate; }
    public Boolean getProcessed() { return processed; }
    public void setProcessed(Boolean processed) { this.processed = processed; }
    public String getHandlerNote() { return handlerNote; }
    public void setHandlerNote(String handlerNote) { this.handlerNote = handlerNote; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
