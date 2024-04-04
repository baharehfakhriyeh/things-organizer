package com.fkhr.thingsorganizer.thing.model;

import jakarta.persistence.*;

@Entity
public class Container{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String title;
    @ManyToOne
    Container parent;
   /* @OneToMany(mappedBy = "container")
    List<Thing> things;
    @OneToMany(mappedBy = "parent")
    List<Container> containers;
*/
    public Container(Long id, String title, Container parent) {
        this.id = id;
        this.title = title;
        this.parent = parent;
    }

    public Container(String title, Container parent) {
        this.title = title;
        this.parent = parent;
    }

    public Container() {
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

    public Container getParent() {
        return parent;
    }

    public void setParent(Container parent) {
        this.parent = parent;
    }

 /*   public List<Thing> getThings() {
        return things;
    }

    public void setThings(List<Thing> things) {
        this.things = things;
    }

    public List<Container> getContainers() {
        return containers;
    }

    public void setContainers(List<Container> containers) {
        this.containers = containers;
    }*/
}
