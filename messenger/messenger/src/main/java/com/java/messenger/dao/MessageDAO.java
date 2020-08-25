package com.java.messenger.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import java.math.BigInteger;
import java.sql.Timestamp;

@Data
@Builder
@Entity(name = "messages")
@AllArgsConstructor
@NoArgsConstructor
public class MessageDAO {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "messages_ids_gen")
    @SequenceGenerator(name = "messages_ids_gen", sequenceName = "messages_id_seq", allocationSize = 1)
    private BigInteger id;

    private BigInteger userId;

    @Column
    private String message;
    @Column
    private Timestamp timestamp;
}
