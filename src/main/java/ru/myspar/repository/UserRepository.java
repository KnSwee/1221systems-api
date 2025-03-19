package ru.myspar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.myspar.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}