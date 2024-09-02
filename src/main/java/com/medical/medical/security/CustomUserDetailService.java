package com.medical.medical.security;

import com.medical.medical.models.entity.Admin;
import com.medical.medical.models.entity.Medecin;
import com.medical.medical.models.entity.Secretaire;
import com.medical.medical.repository.AdminRepo;
import com.medical.medical.repository.MedecinRepo;
import com.medical.medical.repository.SecretaireRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final MedecinRepo medecinRepo;
    private final SecretaireRepo secretaireRepo;
    private final AdminRepo adminRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Medecin> medecin = medecinRepo.findMedecinByEmail(username);
        if (medecin.isPresent()) {
            log.info("Found Medecin: {}", medecin.get());
            return new UtulisateurDetail(medecin.get());
        }

        Optional<Secretaire> secretaire = secretaireRepo.findSecretaireByEmail(username);
        if (secretaire.isPresent()) {
            log.info("Found Secretaire: {}", secretaire.get());
            return new UtulisateurDetail(secretaire.get());
        }

        Optional<Admin> admin = adminRepo.findAdminByEmail(username);
        if (admin.isPresent()) {
            log.info("Found Admin: {}", admin.get());
            return new UtulisateurDetail(admin.get());
        }

        log.warn("User not found: {}", username);
        throw new UsernameNotFoundException("User not found");
    }
}
