package com.example.gestion_bank.web;


import com.example.gestion_bank.entities.BankAccount;
import com.example.gestion_bank.services.BankAccountService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j

public class BankAccountRestAPI {

    private BankAccountService bankAccountService;

}
