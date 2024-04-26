package com.example.gestion_bank.repositories;

import com.example.gestion_bank.entities.Customer;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.yaml.snakeyaml.events.Event;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
    @Query (value = "SELECT * from Customer  WHERE name LIKE %?1%", nativeQuery = true)
    List<Customer> findByName (String keyword);



}
