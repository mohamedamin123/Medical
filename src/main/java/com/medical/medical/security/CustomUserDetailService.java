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

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailService implements UserDetailsService {

    private final MedecinRepo medecinRepo;
    private final SecretaireRepo secretaireRepo;
    private final AdminRepo adminRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("email : {}", username);

        Optional<Medecin> medecin = medecinRepo.findMedecinByEmail(username);
        if (medecin.isPresent()) {
            return new UtulisateurDetail(medecin.get());
        }

        Optional<Secretaire> secretaire = secretaireRepo.findSecretaireByEmail(username);
        if (secretaire.isPresent()) {
            return new UtulisateurDetail(secretaire.get());
        }

        Optional<Admin> admin = adminRepo.findAdminByEmail(username);
        if (admin.isPresent()) {
            return new UtulisateurDetail(admin.get());
        }

        throw new UsernameNotFoundException("user not found");
    }
}
