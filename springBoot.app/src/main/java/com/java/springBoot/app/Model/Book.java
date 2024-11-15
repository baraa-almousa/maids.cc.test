package com.java.springBoot.app.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;


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

    @NotBlank(message = "Title is required")
    @Column(name = "title")
    private String title;

    @NotBlank(message = "Author is required")
    @Column(name = "author")
    private String author;

    @Min(value = 1000, message = "Publication year must be a valid year")
    @Max(value = 2024, message = "Publication year cannot be in the future")
    @Column(name = "publicationYear")
    private int publicationYear;

    @Pattern(regexp = "\\d{3}-\\d{10}", message = "ISBN format should be 000-0000000000")
    @Column(name = "isbn")
    private String isbn;


    @OneToMany(mappedBy = "book", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<BorrowingRecord> borrowingRecords;

}
