package com.example.gestion_bank.repositories;

import com.example.gestion_bank.entities.AccountOperation;
import jakarta.persistence.Id;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountOperationRepository extends JpaRepository<AccountOperation, Long> {

    public List<AccountOperation> findByBankAccountId (String accountId);
    public Page<AccountOperation> findByBankAccountIdOrderByOperationDateDesc (String accountId, Pageable pageable);
}
