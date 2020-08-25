package com.java.messenger.repository;

import com.java.messenger.dao.ConnectionDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface ConnectionsRepository extends JpaRepository<ConnectionDAO, BigInteger> {

    List<ConnectionDAO> findAllByOwnerId(BigInteger ownerId);
}
