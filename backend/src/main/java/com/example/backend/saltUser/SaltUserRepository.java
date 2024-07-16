package com.example.backend.saltUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SaltUserRepository extends JpaRepository<SaltUser, UUID> {
}
