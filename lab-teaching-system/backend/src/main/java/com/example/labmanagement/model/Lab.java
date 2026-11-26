package com.example.labmanagement.model;

import jakarta.persistence.*;

@Entity
@Table(name = "labs")
public class Lab {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String location;
    private Integer capacity;

    @Enumerated(EnumType.STRING)
    private LabStatus status;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public Integer getCapacity() { return capacity; }
    public void setCapacity(Integer capacity) { this.capacity = capacity; }
    public LabStatus getStatus() { return status; }
    public void setStatus(LabStatus status) { this.status = status; }
}
