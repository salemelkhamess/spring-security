package org.example.springsecurityapp.entities;

import jakarta.persistence.Access;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Product {

    @Id
    @GeneratedValue
    private Long id;
    @NotBlank
    @Size(min = 1, max = 20)
    private String name;
    @Min(0)
    private double price;
    @Min(1)
    private int quantity;

}
