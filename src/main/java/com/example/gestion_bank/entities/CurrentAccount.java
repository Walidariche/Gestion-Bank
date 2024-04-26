package com.example.gestion_bank.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@DiscriminatorValue("CA")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrentAccount extends BankAccount{

    private double overDraft;

}
