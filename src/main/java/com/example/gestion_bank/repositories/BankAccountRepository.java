package com.example.gestion_bank.repositories;

import com.example.gestion_bank.entities.BankAccount;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


public interface BankAccountRepository extends JpaRepository<BankAccount,String> {

    public List<BankAccount>  getBankAccountByCustomer_Id (Long Id);



}
