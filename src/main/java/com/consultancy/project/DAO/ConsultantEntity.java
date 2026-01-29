package com.consultancy.project.DAO;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "consultants")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConsultantEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "second_name", nullable = false)
    private String secondName;

    @Column(name = "specialization", length = 100)
    private String specialization;

    @Column(name = "experience_years")
    private Integer experienceYears;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, length = 20, unique = true)
    private String phone;
}
