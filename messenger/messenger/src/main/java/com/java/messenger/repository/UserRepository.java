package com.java.messenger.repository;

import com.java.messenger.dao.UserDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserDAO, BigInteger> {

    Optional<UserDAO> findByUserNameAndPassword(String username, String pass);

    Optional<UserDAO> findUserDAOByUserName(String username);
}
