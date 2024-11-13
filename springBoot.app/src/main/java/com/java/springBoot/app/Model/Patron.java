package com.java.springBoot.app.Model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "Patron")
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@NoArgsConstructor
@ToString
public class Patron {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "contactInfo")
    private String contactInfo;
    @Column(name = "email")
    private String email;
    @Column(name = "PhoneNumber")
    private String PhoneNumber;





    // Getters and Setters
}
