package ru.vanilock.bankapi.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import ru.vanilock.bankapi.exceptions.FinanceOperationNotFound;
import ru.vanilock.bankapi.exceptions.UserNotFoundException;
import ru.vanilock.bankapi.model.FinanceOperation;
import ru.vanilock.bankapi.model.ResponseJson;
import ru.vanilock.bankapi.model.User;
import ru.vanilock.bankapi.repository.FinanceOperationRepository;
import ru.vanilock.bankapi.repository.UserRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FinanceServiceTest {

    @InjectMocks
    private FinanceService financeService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private FinanceOperationRepository financeOperationRepository;

    @Test
    void getBalance() throws UserNotFoundException {
        User user = new User(2000l, "Евгений", "Петрошин", new BigDecimal(1000l));
        Mockito.doReturn(
                Optional.of(user))
                .when(userRepository).findById(2000l);
        Optional<BigDecimal> balance = financeService.getBalance(2000l);
        assertEquals(1000, balance.get().intValue());
    }

    @Test
    void takeMoney() throws UserNotFoundException {
        User user = new User(2001l, "Петр", "Иванов", new BigDecimal(1000l));
        Mockito.doReturn(
                        Optional.of(user))
                .when(userRepository).findById(2001l);
        ResponseJson result = financeService.takeMoney(2001l, BigDecimal.valueOf(1000));
        assertEquals(1, result.getValue());
    }

    @Test
    void takeMoneyNotEnoughMoney() throws UserNotFoundException {
        User user = new User(2002l, "Петр", "Иванов", new BigDecimal(1l));
        Mockito.doReturn(
                        Optional.of(user))
                .when(userRepository).findById(2002l);
        ResponseJson result = financeService.takeMoney(2002l, BigDecimal.valueOf(1000));
        assertEquals(0, result.getValue());
        assertTrue("Недостаточно средств".equals(result.getObject()));
    }

    @Test
    void putMoney() throws UserNotFoundException {
        User user = new User(2003l, "Стеффен", "Карри", new BigDecimal(1l));
        Mockito.doReturn(
                        Optional.of(user))
                .when(userRepository).findById(2003l);
        ResponseJson result = financeService.putMoney(2003l, BigDecimal.valueOf(1000));
        assertEquals(1, result.getValue());
    }

    @Test
    void getOperationList() {
        LocalDate from = LocalDate.of(2023, 9, 17);
        LocalDate to = LocalDate.of(2023, 10, 18);
        User user = new User(2004l, "Боян", "Богданович", new BigDecimal(1l));
        FinanceOperation finBefore = new FinanceOperation(user, "withdraw", BigDecimal.valueOf(1000l),
                LocalDate.of(2023, 9, 17));
        FinanceOperation finAfter = new FinanceOperation(user, "withdraw", BigDecimal.valueOf(2000l),
                LocalDate.of(2023, 10, 18));
        List<FinanceOperation> listFromDB = new ArrayList<>();
        listFromDB.add(finBefore);
        listFromDB.add(finAfter);
        Mockito.doReturn(
                        listFromDB)
                .when(financeOperationRepository).findOperationsByUserId(2004l);
        List<FinanceOperation> result = financeService.getOperationList(2004l, from, to);
        assertTrue(result.contains(finBefore));
        assertTrue(result.contains(finAfter));
    }

    @Test
    void transferMoney() throws UserNotFoundException {
        User sender = new User(2005l, "Дмитрий", "Каспер", new BigDecimal(10000l));
        User recipient = new User(2006l, "Федр", "Капустин", new BigDecimal(1l));
        Mockito.doReturn(
                        Optional.of(sender))
                .when(userRepository).findById(2005l);
        Mockito.doReturn(
                        Optional.of(recipient))
                .when(userRepository).findById(2006l);
        ResponseJson result = financeService.transferMoney(2005l, 2006l, BigDecimal.valueOf(9999l));
        assertEquals(1, result.getValue());

    }

    @Test
    void getUser() throws UserNotFoundException {
        User user = new User(2007l, "Павел", "Визиткин", new BigDecimal(1l));
        Mockito.doReturn(
                        Optional.of(user))
                .when(userRepository).findById(2007l);
        User result = financeService.getUser(2007l);
        assertEquals("Павел", result.getFirstName());
        assertEquals("Визиткин", result.getLastName());
    }

    @Test
    void getFinanceOperation() throws FinanceOperationNotFound {
        User user = new User(2008l, "Крисс", "Бертон", new BigDecimal(1l));
        FinanceOperation financeOperation = new FinanceOperation(2008l, user, "withdraw",
                BigDecimal.valueOf(1000l), LocalDate.of(2023, 10, 18));
        Mockito.doReturn(
                        Optional.of(financeOperation))
                .when(financeOperationRepository).findById(2008l);
        FinanceOperation result = financeService.getFinanceOperation(2008l);
        assertEquals("withdraw", result.getOperation());
    }
}