package com.java.messenger.repository;

import com.java.messenger.dao.MessageDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<MessageDAO, BigInteger> {

    List<MessageDAO> findAllByUserId(BigInteger userId);
}
