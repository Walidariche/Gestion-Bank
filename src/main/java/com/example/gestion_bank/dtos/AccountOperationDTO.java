package com.example.gestion_bank.dtos;

import com.example.gestion_bank.entities.BankAccount;
import com.example.gestion_bank.enums.OperationType;
import lombok.Data;

import java.util.Date;
@Data
public class AccountOperationDTO {

    private  Long id;

    private Date operationDate;

    private double amount;

    private OperationType type;

    private String description;
}
