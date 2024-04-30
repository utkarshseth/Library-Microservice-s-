package com.books.bookservice.model;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document(value = "book")
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Data
public class Book {

    @Id
    private String id;
    private String name;
    private String description;
    private BigDecimal price;
}
