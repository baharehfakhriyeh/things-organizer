package com.fkhr.thingsorganizer.thing.model;

import jakarta.persistence.*;

@Entity
public class Thing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    protected String title;
    protected Long weight;
    @ManyToOne
    protected Container container;

    public Thing(Long id, String title, Long weight, Container container) {
        this.id = id;
        this.title = title;
        this.weight = weight;
        this.container = container;
    }

    public Thing(Long id) {
        this.id = id;
    }

    public Thing() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getWeight() {
        return weight;
    }

    public void setWeight(Long weight) {
        this.weight = weight;
    }

    public Container getContainer() {
        return container;
    }

    public void setContainer(Container container) {
        this.container = container;
    }
}
