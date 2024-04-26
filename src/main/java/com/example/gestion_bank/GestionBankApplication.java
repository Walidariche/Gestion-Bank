package com.example.gestion_bank;

import com.example.gestion_bank.dtos.BankAccountDTO;
import com.example.gestion_bank.dtos.CurrentBankAccountDTO;
import com.example.gestion_bank.dtos.CustomerDTO;
import com.example.gestion_bank.dtos.SavingBankAccountDTO;
import com.example.gestion_bank.entities.*;
import com.example.gestion_bank.enums.AccountStatus;
import com.example.gestion_bank.enums.OperationType;
import com.example.gestion_bank.exceptions.BakaccountNotFoundException;
import com.example.gestion_bank.exceptions.BalanceNotSufficentException;
import com.example.gestion_bank.exceptions.CustomerNotFoundException;
import com.example.gestion_bank.repositories.AccountOperationRepository;
import com.example.gestion_bank.repositories.BankAccountRepository;
import com.example.gestion_bank.repositories.CustomerRepository;
import com.example.gestion_bank.services.BankAccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Stream;

import static com.example.gestion_bank.enums.OperationType.DEBIT;

@SpringBootApplication
public class GestionBankApplication  {

    public static void main(String[] args) {
        SpringApplication.run(GestionBankApplication.class, args);
    }

     @Bean
    CommandLineRunner commandLineRunner(BankAccountService bankAccountService){
        return  args -> {
            Stream.of("chaaiba","zahra","phoenix").forEach(name->{

                CustomerDTO customer=new CustomerDTO();
                customer.setName(name);
                customer.setEmail(name +"@gmail.com");
                bankAccountService.saveCustomer(customer);

            });
            bankAccountService.listCustomers().forEach(customer -> {

                try {
                    bankAccountService.saveCurrentBankAccount(Math.random()*700,customer.getId(),90000);
                    bankAccountService.saveSavingBankAccount(Math.random()*1200,customer.getId(),5.5);
                    bankAccountService.saveSavingBankAccount(Math.random()*120,customer.getId(),3.2);
                    bankAccountService.saveCurrentBankAccount(Math.random()*20,customer.getId(),300);
                    bankAccountService.saveSavingBankAccount(Math.random()*100,customer.getId(),2.1);
                    bankAccountService.saveCurrentBankAccount(Math.random()*900,customer.getId(),1000);
                    bankAccountService.saveSavingBankAccount(Math.random()*200,customer.getId(),2.2);
                    bankAccountService.saveCurrentBankAccount(Math.random()*90,customer.getId(),800);



                } catch (CustomerNotFoundException e) {
                    e.printStackTrace();
                }

            });
            List<BankAccountDTO> bankAccounts=bankAccountService.bankAccountList();
            for (BankAccountDTO bankAccount:bankAccounts){

                for(int i=0;i<10;i++){
                    String accountId;
                    if (bankAccount instanceof SavingBankAccountDTO){
                        accountId=((SavingBankAccountDTO) bankAccount).getId();

                    }else {

                        accountId=((CurrentBankAccountDTO) bankAccount).getId();
                    }

                    bankAccountService.credit(accountId,1000000+Math.random()*100,"Credit" );
                    bankAccountService.debit(accountId,1000+Math.random()*900,"debit");
                }
            }

        };

    }

   // @Bean
    CommandLineRunner start(CustomerRepository customerRepository,
                            AccountOperationRepository accountOperationRepository,
                            BankAccountRepository bankAccountRepository){

 return args -> {
     Stream.of("hassan","aicha","walid").forEach(name->{
         Customer customer=new Customer();
         customer.setName(name);
         customer.setEmail(name +"@gmail.com");
         customerRepository.save(customer);

     });

     customerRepository.findAll().forEach(cust ->{
         CurrentAccount currentAccount=new CurrentAccount();
         currentAccount.setBalance(Math.random()*600);
         currentAccount.setCreateAT(new Date());
         currentAccount.setStatus(AccountStatus.ACTIVATED);
         currentAccount.setOverDraft(9000);
         currentAccount.setId(UUID.randomUUID().toString());
         currentAccount.setCustomer(cust);
         bankAccountRepository.save(currentAccount);

         SavingAccount savingAccount=new SavingAccount();
         savingAccount.setBalance(Math.random()*500);
         savingAccount.setCreateAT(new Date());
         savingAccount.setStatus(AccountStatus.SUSPENDED);
         savingAccount.setInterestRate(5.5);
         savingAccount.setId(UUID.randomUUID().toString());
         savingAccount.setCustomer(cust);
         bankAccountRepository.save(savingAccount);






     });

     bankAccountRepository.findAll().forEach(Acc ->{
         for (int i = 0; i < 10; i++) {
             AccountOperation accountOperation=new AccountOperation();
             accountOperation.setOperationDate(new Date());
             accountOperation.setAmount(Math.random()*12000);
             accountOperation.setType(Math.random()>0.5? DEBIT:OperationType.CREDIT);
             accountOperation.setBankAccount(Acc);
             accountOperationRepository.save(accountOperation);
             
         }

         
     });





    };
}


}
