package ru.vanilock.bankapi.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.vanilock.bankapi.exceptions.FinanceOperationNotFound;
import ru.vanilock.bankapi.exceptions.UserNotFoundException;
import ru.vanilock.bankapi.model.FinanceOperation;
import ru.vanilock.bankapi.model.ResponseJson;
import ru.vanilock.bankapi.model.User;
import ru.vanilock.bankapi.repository.FinanceOperationRepository;
import ru.vanilock.bankapi.repository.UserRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class FinanceService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final FinanceOperationRepository financeOperationRepository;


    public Optional<BigDecimal> getBalance(Long userId) throws UserNotFoundException {
        BigDecimal balance = getUser(userId).getBalance();
        Optional<BigDecimal> result = Optional.of(balance);
        return result;
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ResponseJson takeMoney(Long userId, BigDecimal amount) throws UserNotFoundException {
        User target = getUser(userId);
        if(target.getBalance().compareTo(amount) < 0){
            return new ResponseJson(0, "Недостаточно средств");
        } else {
            target.setBalance(target.getBalance().subtract(amount));
            FinanceOperation operation = new FinanceOperation(target,"withdraw", amount, LocalDate.now());
            userRepository.save(target);
            financeOperationRepository.save(operation);
            return new ResponseJson(1);
        }
    }
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ResponseJson putMoney(Long userId, BigDecimal amount) throws UserNotFoundException {
        User target = getUser(userId);
        target.setBalance(target.getBalance().add(amount));
        FinanceOperation operation = new FinanceOperation(target, "deposit", amount, LocalDate.now());
        userRepository.save(target);
        financeOperationRepository.save(operation);
        return new ResponseJson(1);
    }

    @Transactional(readOnly = true)
    public List<FinanceOperation> getOperationList(Long userId, @Nullable LocalDate from, @Nullable LocalDate to){
        List<FinanceOperation> operationsList = financeOperationRepository.findFinanceOperationsBySenderId(userId);
        if (from != null && to != null){
            operationsList = operationsList
                    .stream()
                    .filter(f -> f.getDate().isAfter(from) || f.getDate().isEqual(from))
                    .filter(f -> f.getDate().isBefore(to) || f.getDate().isEqual(to))
                    .collect(Collectors.toList());
        } else if (from == null && to != null){
            operationsList = operationsList
                    .stream()
                    .filter(f -> f.getDate().isBefore(to) || f.getDate().isEqual(to))
                    .collect(Collectors.toList());
            } else if (from != null && to == null){
            operationsList = operationsList
                    .stream()
                    .filter(f -> f.getDate().isAfter(from) || f.getDate().isEqual(from))
                    .collect(Collectors.toList());
                }
            return operationsList;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ResponseJson transferMoney(Long senderId, Long recipientId, BigDecimal amount) throws UserNotFoundException{
        User sender = getUser(senderId);
        User recipient = getUser(recipientId);
        if (sender.getBalance().subtract(amount).intValue() >= 0){
            sender.setBalance(sender.getBalance().subtract(amount));
            recipient.setBalance(recipient.getBalance().add(amount));
            userRepository.save(sender);
            userRepository.save(recipient);
            FinanceOperation operation =
                    new FinanceOperation(sender, recipient, "transfer", amount, LocalDate.now());
            financeOperationRepository.save(operation);
            return new ResponseJson(1, "Операция успешна");
        } else {
            return new ResponseJson(0, "Недостаточно средств");
        }
    }

    public User getUser(Long userId) throws UserNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        return user;
    }

    public FinanceOperation getFinanceOperation(Long operationId) throws FinanceOperationNotFound{
        FinanceOperation financeOperation = financeOperationRepository.findById(operationId)
                .orElseThrow(FinanceOperationNotFound::new);
        return financeOperation;
    }

}
