package com.example.demo.repositories;

import com.example.demo.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {
    @Query("SELECT c FROM Customer AS c ORDER BY c.dateOfBirth ASC,c.youngDriver ASC ")
    List<Customer> findAllByDateOfBirthAndYoungDriver();

    //Exercising "param".
    @Query("SELECT c FROM Customer AS c WHERE c.sales.size >:param")
    List<Customer>findAllBySalesGreaterThan(@Param("param") int greater);



}
