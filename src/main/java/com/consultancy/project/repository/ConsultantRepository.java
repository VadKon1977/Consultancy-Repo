package com.consultancy.project.repository;

import com.consultancy.project.DAO.ConsultantEntity;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsultantRepository extends JpaRepository<ConsultantEntity, Long> {

    @Cacheable(value = "phones", key = "#phone")
    boolean existsByPhone(String phone);

    @Cacheable(value ="emails", key = "#email")
    boolean existsByEmail(String email);
}
