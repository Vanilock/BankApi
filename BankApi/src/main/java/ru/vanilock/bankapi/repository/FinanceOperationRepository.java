package ru.vanilock.bankapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.vanilock.bankapi.model.FinanceOperation;
import ru.vanilock.bankapi.model.User;

import java.util.List;

public interface FinanceOperationRepository extends JpaRepository<FinanceOperation, Long> {


    List<FinanceOperation> findFinanceOperationsBySenderId(Long userId);
}
