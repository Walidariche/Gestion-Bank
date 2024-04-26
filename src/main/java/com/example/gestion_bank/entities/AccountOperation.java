package com.example.gestion_bank.entities;

import com.example.gestion_bank.enums.OperationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountOperation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private Date operationDate;

    private double amount;
    @Enumerated(value = EnumType.STRING)
    private OperationType type;

     @ManyToOne
    private BankAccount bankAccount;

     private String description;
}
