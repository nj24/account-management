package com.project.account.management.repository;

import com.project.account.management.domain.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
   Optional<Account> findOptionalByRef(UUID accountRef);
}
