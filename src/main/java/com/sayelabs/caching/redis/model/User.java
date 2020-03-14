package com.sayelabs.caching.redis.model;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
public class User implements Serializable {

    private static final long serialVersionUID = 6640471942473143799L;

    @Id
    @SequenceGenerator(allocationSize = 1,name = "seq_gen", sequenceName = "user_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "seq_gen")
    private Long id;

    private String name;

    private int followers;

    public User(){
    }

    public User(String name, int followers){
        this.name = name;
        this.followers =followers;
    }
}
