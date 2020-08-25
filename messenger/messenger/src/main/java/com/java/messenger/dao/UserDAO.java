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
@Entity(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class UserDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_ids_gen")
    @SequenceGenerator(name = "users_ids_gen", sequenceName = "users_id_seq", allocationSize = 1)
    private BigInteger id;
    @Column
    private String userName;
    @Column
    private String password;
    @Column
    private Timestamp registeredDate;
}
