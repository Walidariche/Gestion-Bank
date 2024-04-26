package com.example.gestion_bank.dtos;

import com.example.gestion_bank.enums.AccountStatus;
import lombok.Data;

import java.util.Date;
@Data
public class CurrentBankAccountDTO extends  BankAccountDTO {
    private String id;
    private double balance;
    private Date createAT;
    private AccountStatus status;
    private  CustomerDTO customerDTO;
    private  double overDraft;
}
