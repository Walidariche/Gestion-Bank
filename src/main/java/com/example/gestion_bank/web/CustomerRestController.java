package com.example.gestion_bank.web;

import com.example.gestion_bank.dtos.*;
import com.example.gestion_bank.entities.AccountOperation;
import com.example.gestion_bank.entities.BankAccount;
import com.example.gestion_bank.exceptions.BakaccountNotFoundException;
import com.example.gestion_bank.exceptions.BalanceNotSufficentException;
import com.example.gestion_bank.exceptions.CustomerNotFoundException;
import com.example.gestion_bank.services.BankAccountService;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin("*")
public class CustomerRestController {

   private BankAccountService  bankAccountService;

   @GetMapping("/customers")
    public List<CustomerDTO> customers(){

       return bankAccountService.listCustomers();
   }
    @GetMapping("/customers/search")
    public List<CustomerDTO> searchCustomers(@RequestParam (name = "keyword",defaultValue = "") String keyword ){

        return bankAccountService.searchCustomers(keyword);
    }
    @GetMapping("/customers/{id}")
   public  CustomerDTO getCustomer(@PathVariable(name = "id") long customerId) throws CustomerNotFoundException {

       return bankAccountService. getCustomer(customerId);
   }

   @PostMapping("/customers")
  public  CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){


      return  bankAccountService.saveCustomer(customerDTO);

   }
   @PutMapping("/customers/{customerId}")

    public CustomerDTO updatedCustumer(@PathVariable Long customerId,@RequestBody CustomerDTO customerDTO){

        customerDTO.setId(customerId);
     return  bankAccountService.updateCostumer(customerDTO);


    }
    @DeleteMapping("/customers/{id}")
    public void deleteCustomer(@PathVariable Long id ){
       bankAccountService.deleteCustumer(id);

    }
    @GetMapping("accounts/{accountId}")
    public BankAccountDTO getBankAccount(@PathVariable String accountId) throws BakaccountNotFoundException {

       return bankAccountService.getBankAccount(accountId);

    }
 @GetMapping("/accounts")
    public List<BankAccountDTO> listAccounts(){

       return bankAccountService.bankAccountList();
    }

    @GetMapping("/accounts/{accountId}/operations")
    public List<AccountOperationDTO> getHistory (@PathVariable String accountId){

       return bankAccountService.accountHistory(accountId);
    }
    @GetMapping("/accounts/{accountId}/pageOperations")
    public AccountHistoryDTO getAccountHistory (@PathVariable String accountId,
                                                @RequestParam(name = "page" ,defaultValue = "0") int page ,
                                                @RequestParam(name = "size" ,defaultValue = "5")   int size) throws BakaccountNotFoundException {

        return bankAccountService.getAccountHistory(accountId,page,size);
    }

    @PostMapping("accounts/debit")
    public DebitDTO debit(@RequestBody DebitDTO debitDTO) throws BakaccountNotFoundException, BalanceNotSufficentException {
        this.bankAccountService.debit(debitDTO.getAccountId(), debitDTO.getAmount(), debitDTO.getDescription());
         return debitDTO;
    }
    @PostMapping("accounts/credit")
    public CreditDTO debit(@RequestBody CreditDTO creditDTO) throws BakaccountNotFoundException, BalanceNotSufficentException {
        this.bankAccountService.credit(creditDTO.getAccountId(), creditDTO.getAmount(), creditDTO.getDescription());
        return creditDTO ;
    }

    @PostMapping("accounts/transfer")
    public void   transfer(@RequestBody TransferRequestDTO transferRequestDTO) throws BakaccountNotFoundException, BalanceNotSufficentException {
          this.bankAccountService.transfer(
                  transferRequestDTO.getAccountSource(),
                  transferRequestDTO.getAccountDestination(),
                  transferRequestDTO.getAmount());


    }
    @GetMapping("/accounts/customer/{Id}")

    public List<BankAccountDTO> listAccountsbycustomer(@PathVariable long Id){

        return bankAccountService.getBankAccountByCostumerId(Id);
    }




}
