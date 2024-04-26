package com.example.gestion_bank.mappers;

import com.example.gestion_bank.dtos.*;
import com.example.gestion_bank.entities.*;
import com.fasterxml.jackson.databind.util.BeanUtil;
import jakarta.persistence.PersistenceUnit;
import jakarta.persistence.criteria.From;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class BankAccountMapperImpl {

    public CustomerDTO fromCustomer(Customer customer){
        CustomerDTO customerDTO=new CustomerDTO();

        BeanUtils.copyProperties(customer,customerDTO);
        //customerDTO.setId(customer.getId());
        //customerDTO.setName(customer.getName());
        //customerDTO.setEmail(customer.getEmail());

        return customerDTO;
    }
    public Customer fromCustomerDto(CustomerDTO customerDTO){

        Customer customer= new Customer();
        BeanUtils.copyProperties(customerDTO,customer);
        return customer;
    }

    public CurrentBankAccountDTO fromCurrentBankAccount(CurrentAccount currentAccount){

        CurrentBankAccountDTO currentBankAccountDTO=new CurrentBankAccountDTO();
        BeanUtils.copyProperties(currentAccount,currentBankAccountDTO);
        currentBankAccountDTO.setCustomerDTO(fromCustomer(currentAccount.getCustomer()));
        currentBankAccountDTO.setType(currentAccount.getClass().getSimpleName());

        return currentBankAccountDTO;

    }

    public CurrentAccount fromCurrentBankAAccountDTO(CurrentBankAccountDTO currentBankAccountDTO){

        CurrentAccount currentAccount=new CurrentAccount();
        BeanUtils.copyProperties(currentBankAccountDTO,currentAccount);
        currentAccount.setCustomer(fromCustomerDto(currentBankAccountDTO.getCustomerDTO()));
        return currentAccount;
    }
    public SavingBankAccountDTO fromSavingBankAccount(SavingAccount savingAccount){

        SavingBankAccountDTO savingBankAccountDTO=new SavingBankAccountDTO();
        BeanUtils.copyProperties(savingAccount,savingBankAccountDTO);
        savingBankAccountDTO.setCustomerDTO(fromCustomer(savingAccount.getCustomer()));
        savingBankAccountDTO.setType(savingAccount.getClass().  getSimpleName());
        return savingBankAccountDTO;

    }
    public  SavingAccount fromSavingBankAccountDTO(SavingBankAccountDTO savingBankAccountDTO){

        SavingAccount savingAccount=new SavingAccount();
        BeanUtils.copyProperties(savingBankAccountDTO,savingAccount);
        savingAccount.setCustomer(fromCustomerDto(savingBankAccountDTO.getCustomerDTO()));
        return savingAccount;

    }
    public AccountOperationDTO fromAccountOperation(AccountOperation accountOperation){
        AccountOperationDTO accountOperationDTO=new AccountOperationDTO();
        BeanUtils.copyProperties(accountOperation,accountOperationDTO);
        return  accountOperationDTO;

    }
    public AccountOperation fromAccountOperationDTO(AccountOperationDTO accountOperationDTO){
        AccountOperation accountOperation=new AccountOperation();
        BeanUtils.copyProperties(accountOperationDTO,accountOperation);
        return  accountOperation;
    }

}
