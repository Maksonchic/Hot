package ru.probe.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.probe.dbmodels.User;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findByActivationCode(String code);
}
