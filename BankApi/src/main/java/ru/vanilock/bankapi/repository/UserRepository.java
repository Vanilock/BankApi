package ru.vanilock.bankapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vanilock.bankapi.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
