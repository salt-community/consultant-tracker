package com.example.backend.saltUser;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SaltUserService {
    private final SaltUserRepository saltUserRepository;

    public List<UUID> getAllPtsIds() {
        return saltUserRepository.findAllId();
    }

    public SaltUser getSaltUserById(UUID key) {
        return saltUserRepository.findById(key).orElse(null);
    }
}
