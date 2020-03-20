package com.example.demo.entities;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name="Products")
public class Product extends BaseEntity {
    /*•	Products have an id, name (at least 3 characters), price, buyerId (optional) and sellerId as IDs of users.*/
    private String name;
    private BigDecimal price;
    private User seller;
    private User buyer;
    private Set<Category> categories;

    public Product() {
    }
    @Column(name = "name",nullable = false)
    @Length(min = 3,message = "Wrong length,at least 3.")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Column(name ="price",nullable = false)
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.PERSIST)
    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }
    @ManyToOne(cascade = CascadeType.ALL)
    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }
    @ManyToMany(cascade = CascadeType.ALL)
    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }
}
