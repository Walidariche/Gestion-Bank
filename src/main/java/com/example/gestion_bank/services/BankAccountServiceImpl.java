package com.example.gestion_bank.services;

import com.example.gestion_bank.dtos.*;
import com.example.gestion_bank.entities.*;
import com.example.gestion_bank.enums.OperationType;
import com.example.gestion_bank.exceptions.BakaccountNotFoundException;
import com.example.gestion_bank.exceptions.BalanceNotSufficentException;
import com.example.gestion_bank.exceptions.CustomerNotFoundException;
import com.example.gestion_bank.mappers.BankAccountMapperImpl;
import com.example.gestion_bank.repositories.AccountOperationRepository;
import com.example.gestion_bank.repositories.BankAccountRepository;
import com.example.gestion_bank.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.NonUniqueResultException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
 class BankAccountServiceImpl implements BankAccountService{

    private CustomerRepository customerRepository;
    private BankAccountRepository bankAccountRepository;
    private AccountOperationRepository accountOperationRepository;
    private BankAccountMapperImpl dtoMapper;
    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        log.info("saving new customer");
        Customer customer=dtoMapper.fromCustomerDto(customerDTO);

           Customer savedCustomer = customerRepository.save(customer);
           return dtoMapper.fromCustomer(savedCustomer);
    }

    @Override
    public CurrentBankAccountDTO saveCurrentBankAccount(double initialBalance, Long customerID, double overDraft) throws CustomerNotFoundException {
        Customer customer= customerRepository.findById(customerID).orElse(null);
        if (customer ==null)
            throw new CustomerNotFoundException("the customer not found");
         CurrentAccount currentAccount=new CurrentAccount();
         currentAccount.setId(UUID.randomUUID().toString());
         currentAccount.setCreateAT(new Date());
         currentAccount.setBalance(initialBalance);
         currentAccount.setOverDraft(overDraft);
         currentAccount.setCustomer(customer);
         CurrentAccount savedBankAccount = bankAccountRepository.save(currentAccount);


        return dtoMapper.fromCurrentBankAccount(savedBankAccount);
    }

    @Override
    public SavingBankAccountDTO saveSavingBankAccount(double initialBalance, Long customerID, double interestRate) throws CustomerNotFoundException {
        Customer customer= customerRepository.findById(customerID).orElse(null);
        if (customer ==null)
            throw new CustomerNotFoundException("the customer not found");
        SavingAccount savingAccount=new SavingAccount();
        savingAccount.setId(UUID.randomUUID().toString());
        savingAccount.setCreateAT(new Date());
        savingAccount.setBalance(initialBalance);
        savingAccount.setInterestRate(interestRate);
        savingAccount.setCustomer(customer);
        SavingAccount savedBankAccount = bankAccountRepository.save(savingAccount);


        return dtoMapper.fromSavingBankAccount(savedBankAccount);

    }


    @Override
    public List<CustomerDTO> listCustomers() {
       List<Customer> customers= customerRepository.findAll();
       List<CustomerDTO> customerDTOS=new ArrayList<>();
       for (Customer customer:customers){

           CustomerDTO customerDTO=dtoMapper.fromCustomer(customer);
           customerDTOS.add(customerDTO);
       }
       return customerDTOS;
    }

    @Override
    public BankAccountDTO getBankAccount(String accountId) throws BakaccountNotFoundException {

        BankAccount bankAccount=bankAccountRepository.findById(accountId).orElse(null);
        if (bankAccount ==null)
            throw new BakaccountNotFoundException("the Account not found");
         if (bankAccount instanceof SavingAccount){

             SavingAccount savingAccount = (SavingAccount) bankAccount;
                return  dtoMapper.fromSavingBankAccount(savingAccount);
         }
         else {
                  CurrentAccount currentAccount= (CurrentAccount) bankAccount;
                  return dtoMapper.fromCurrentBankAccount(currentAccount);

         }

    }

    @Override
    public void debit(String AccountID, Double amount, String description) throws BakaccountNotFoundException, BalanceNotSufficentException {

        BankAccount bankAccount=bankAccountRepository.findById(AccountID).orElse(null);
        if (bankAccount ==null)
            throw new BakaccountNotFoundException("the Account not found");
        if (bankAccount.getBalance()<amount)
            throw new BalanceNotSufficentException("Balance Not sufficient");
        AccountOperation accountOperation=new AccountOperation();
        accountOperation.setType(OperationType.DEBIT);
        accountOperation.setAmount(amount);
        accountOperation.setDescription(description);
        accountOperation.setBankAccount(bankAccount);
        accountOperation.setOperationDate(new Date());

        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance()-amount);
        bankAccountRepository.save(bankAccount);

    }

    @Override
    public void credit(String AccountID, Double amount, String description) throws BakaccountNotFoundException, BalanceNotSufficentException {
        BankAccount bankAccount=bankAccountRepository.findById(AccountID).orElse(null);
        if (bankAccount ==null)
            throw new BakaccountNotFoundException("the Account not found");
        AccountOperation accountOperation=new AccountOperation();
        accountOperation.setType(OperationType.CREDIT);
        accountOperation.setAmount(amount);
        accountOperation.setDescription(description);
        accountOperation.setBankAccount(bankAccount);
        accountOperation.setOperationDate(new Date());
        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance()+amount);
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void transfer(String accountIdSource, String accountIdDestination, double amount) throws BakaccountNotFoundException, BalanceNotSufficentException {
        debit(accountIdSource,amount,"Transfer To "+accountIdDestination);
        credit(accountIdDestination,amount,"Transfer from"+accountIdSource);

    }
     @Override
     public List<BankAccountDTO> bankAccountList(){

      List<BankAccount> bankAccounts=bankAccountRepository.findAll();
      List<BankAccountDTO>  bankAccountDTOS= bankAccounts.stream().map(bankAccount -> {
      if (bankAccount instanceof SavingAccount){
           SavingAccount savingAccount= (SavingAccount) bankAccount;
           return dtoMapper.fromSavingBankAccount(savingAccount);


      }else {
          CurrentAccount currentAccount= (CurrentAccount) bankAccount;
          return dtoMapper.fromCurrentBankAccount(currentAccount);

      }

      }).collect(Collectors.toList());
           return bankAccountDTOS;
     }

    @Override
    public CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException {

       Customer customer= customerRepository.findById(customerId).orElseThrow(()->new CustomerNotFoundException("Customer Not Found "));
        return dtoMapper.fromCustomer(customer);


}
      @Override
           public  CustomerDTO updateCostumer(CustomerDTO customerDTO){
          log.info("saving new customer");
          Customer customer=dtoMapper.fromCustomerDto(customerDTO);

          Customer savedCustomer = customerRepository.save(customer);
          return dtoMapper.fromCustomer(savedCustomer);

      }
   @Override
    public  void deleteCustumer(Long customerId){
        customerRepository.deleteById(customerId);

      }

      @Override
    public List<AccountOperationDTO> accountHistory (String accountId){
       List<AccountOperation> accountOperations= accountOperationRepository.findByBankAccountId(accountId);

     return  accountOperations.stream().map(op->dtoMapper.fromAccountOperation(op)).collect(Collectors.toList());

      }

    @Override
    public AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BakaccountNotFoundException {
        BankAccount bankAccount =bankAccountRepository.findById(accountId).orElse(null);

        if ( bankAccount ==null )throw new BakaccountNotFoundException("Account Not Found");

          Page<AccountOperation> accountOperations= accountOperationRepository.findByBankAccountIdOrderByOperationDateDesc(accountId, PageRequest.of(page,size));
          AccountHistoryDTO accountHistoryDTO=new AccountHistoryDTO();
        List<AccountOperationDTO> accountOperationDTOS= accountOperations.getContent().stream().map(op->dtoMapper.fromAccountOperation(op)).collect(Collectors.toList());
          accountHistoryDTO.setAccountOperationDTOS(accountOperationDTOS);
          accountHistoryDTO.setAccountId(bankAccount.getId());
          accountHistoryDTO.setBalance(bankAccount.getBalance());
          accountHistoryDTO.setCurrentPage(page);
          accountHistoryDTO.setPageSize(size);
          accountHistoryDTO.setTotalPages(accountOperations.getTotalPages());
        return accountHistoryDTO;
    }

    @Override
    public List<CustomerDTO> searchCustomers(String keyword) {
        List<Customer>customers=customerRepository.findByName(keyword);
        List<CustomerDTO> customerDTOS = customers.stream().map(cust -> dtoMapper.fromCustomer(cust)).collect(Collectors.toList());
        return customerDTOS;
    }

    @Override
    public List<BankAccountDTO>  getBankAccountByCostumerId(Long Id){


        List<BankAccount> bankAccounts=bankAccountRepository.getBankAccountByCustomer_Id(Id);
        List<BankAccountDTO>  bankAccountDTOS= bankAccounts.stream().map(bankAccount -> {
            if (bankAccount instanceof SavingAccount){
                SavingAccount savingAccount= (SavingAccount) bankAccount;
                return dtoMapper.fromSavingBankAccount(savingAccount);


            }else {
                CurrentAccount currentAccount= (CurrentAccount) bankAccount;
                return dtoMapper.fromCurrentBankAccount(currentAccount);

            }

        }).collect(Collectors.toList());
        return bankAccountDTOS;




    }


}
