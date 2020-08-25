package com.java.messenger.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import java.math.BigInteger;

@Data
@Builder
@Entity(name = "connections")
@AllArgsConstructor
@NoArgsConstructor
public class ConnectionDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_ids_gen")
    @SequenceGenerator(name = "users_ids_gen", sequenceName = "users_id_seq", allocationSize = 1)
    private BigInteger id;

    private BigInteger ownerId;
    private BigInteger friendId;
}
