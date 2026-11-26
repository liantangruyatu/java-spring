package com.example.labmanagement.model;

import jakarta.persistence.*;

@Entity
@Table(name = "grades")
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer reportId;
    private Integer graderId;
    private Double score;
    @Column(columnDefinition = "TEXT")
    private String comment;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getReportId() { return reportId; }
    public void setReportId(Integer reportId) { this.reportId = reportId; }
    public Integer getGraderId() { return graderId; }
    public void setGraderId(Integer graderId) { this.graderId = graderId; }
    public Double getScore() { return score; }
    public void setScore(Double score) { this.score = score; }
    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
}
