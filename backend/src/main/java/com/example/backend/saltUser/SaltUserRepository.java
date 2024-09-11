package com.example.backend.saltUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface SaltUserRepository extends JpaRepository<SaltUser, UUID> {

    @Query("SELECT t.id FROM SaltUser t")
    List<UUID> findAllId();

    @Query("SELECT t.fullName FROM SaltUser t")
    Set<String> findAllNames();

    SaltUser findByFullName(String ptName);
}
