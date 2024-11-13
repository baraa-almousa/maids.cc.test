package com.java.springBoot.app.Model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "Book")
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@NoArgsConstructor
@ToString
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "author")
    private String author;
    @Column(name = "publicationYear")
    private int publicationYear;
    @Column(name = "isbn")
    private String isbn;


}
