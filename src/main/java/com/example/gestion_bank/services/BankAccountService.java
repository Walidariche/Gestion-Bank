package com.example.gestion_bank.services;

import com.example.gestion_bank.dtos.*;
import com.example.gestion_bank.entities.BankAccount;
import com.example.gestion_bank.entities.CurrentAccount;
import com.example.gestion_bank.entities.Customer;
import com.example.gestion_bank.entities.SavingAccount;
import com.example.gestion_bank.exceptions.BakaccountNotFoundException;
import com.example.gestion_bank.exceptions.BalanceNotSufficentException;
import com.example.gestion_bank.exceptions.CustomerNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


public interface BankAccountService {

    public CustomerDTO saveCustomer(CustomerDTO customerDTO);
    CurrentBankAccountDTO saveCurrentBankAccount(double initialBalance, Long customerID, double overDraft) throws CustomerNotFoundException;
    SavingBankAccountDTO saveSavingBankAccount(double initialBalance, Long customerID, double interestRate) throws CustomerNotFoundException;

    List<CustomerDTO> listCustomers();
    BankAccountDTO getBankAccount(String accountId) throws BakaccountNotFoundException;

    void debit(String AccountID,Double amount,String description) throws BakaccountNotFoundException, BalanceNotSufficentException;

    void credit(String AccountID,Double amount,String description) throws BakaccountNotFoundException, BalanceNotSufficentException;
    void transfer(String accountIdSource,String accountIdDestination,double amount) throws BakaccountNotFoundException, BalanceNotSufficentException;

    List<BankAccountDTO> bankAccountList();

    CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException;

    CustomerDTO updateCostumer(CustomerDTO customerDTO);

    void deleteCustumer(Long CustomerId);

    List<AccountOperationDTO> accountHistory(String accountId);


    AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BakaccountNotFoundException;

    List<CustomerDTO> searchCustomers(String keyword);

  List<BankAccountDTO>    getBankAccountByCostumerId(Long Id) ;

}

