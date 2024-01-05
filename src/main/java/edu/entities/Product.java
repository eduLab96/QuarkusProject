package edu.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.io.Serializable;

@Entity
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    private String name;
    private String description;
    private boolean isAvaliable;

    public Product() {};

    public Product(int id, String name, String description) {
        Id = id;
        this.name = name;
        this.description = description;
    }
    
    public Product(int id, String name, String description, boolean isAvaliable) {
        Id = id;
        this.name = name;
        this.description = description;
        this.isAvaliable = isAvaliable;
    }

    public Product(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return Id;
    }
    public void setId(int id) {
        Id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

	public boolean isAvaliable() {
		return isAvaliable;
	}

	public void setAvaliable(boolean isAvaliable) {
		this.isAvaliable = isAvaliable;
	}

    
}
