package com.medical.medical.services.impl;

import com.medical.medical.models.dto.req.AdminReqDTO;
import com.medical.medical.models.dto.req.AdminReqDTO;
import com.medical.medical.models.dto.res.AdminResDTO;
import com.medical.medical.models.dto.res.AdminResDTO;
import com.medical.medical.models.entity.Admin;
import com.medical.medical.models.entity.Admin;
import com.medical.medical.models.entity.Medecin;
import com.medical.medical.models.mapper.AdminMapper;
import com.medical.medical.models.mapper.AdminMapper;
import com.medical.medical.repository.AdminRepo;
import com.medical.medical.repository.AdminRepo;
import com.medical.medical.security.UtulisateurDetail;
import com.medical.medical.services.interf.AdminService;
import com.medical.medical.services.interf.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService, UserDetailsService {

    private final AdminMapper mapper;

    private final AdminRepo repository;

    private PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();

//------------------------------------------------------------------------------------------------------------------save

    @Override
    public AdminResDTO saveAdmin(AdminReqDTO req) {
        Admin emp = mapper.toEntity(req);
        emp.setPassword(this.passwordEncoder.encode(emp.getPassword()));
        emp.setStatut(true);
        repository.save(emp);
        return mapper.toRespDTO(emp);
    }

    @Override
    public AdminResDTO updateAdmin(AdminReqDTO req) {
        // Convertir DTO en entité pour obtenir les nouvelles données
        Admin updatedAdmin = mapper.toEntity(req);

        // Trouver le médecin existant par son email
        Optional<Admin> existingAdminOptional = this.repository.findAdminByEmail(updatedAdmin.getEmail());

        if (existingAdminOptional.isPresent()) {
            // Obtenir le médecin existant
            Admin existingAdmin = existingAdminOptional.get();
            System.out.println(existingAdmin);

            // Mettre à jour les champs pertinents avec les nouvelles valeurs
            existingAdmin.setNom(updatedAdmin.getNom());
            existingAdmin.setPrenom(updatedAdmin.getPrenom());
            existingAdmin.setTel(updatedAdmin.getTel());
            existingAdmin.setEmail(updatedAdmin.getEmail());
            existingAdmin.setDateDeNaissance(updatedAdmin.getDateDeNaissance());

            // Mettre à jour la date de modification si nécessaire
            existingAdmin.setUpdatedAt(LocalDateTime.now()); // Assurez-vous que vous avez un champ 'updatedAt' dans votre entité

            // Réinitialiser 'deletedAt' si nécessaire
            existingAdmin.setDeletedAt(null);
            existingAdmin.setStatut(true);
            // Enregistrer les modifications dans la base de données
            Admin savedAdmin = repository.save(existingAdmin);

            // Convertir l'entité mise à jour en DTO de réponse
            return mapper.toRespDTO(savedAdmin);
        }

        // Retourner null si le médecin n'a pas été trouvé
        return null;
    }

//------------------------------------------------------------------------------------------------------------------find

    @Override
    public List<AdminResDTO> findAllAdmin() {

        List<Admin> users = this.repository.findAll();
        return mapper.toAllRespDTO(users);
    }


    @Override
    public Optional<AdminResDTO> findAdminById(int id) {
        Optional<Admin> optionalAdmin = this.repository.findById(id);
        if (optionalAdmin.isPresent()) {
            AdminResDTO AdminResDTO = mapper.toRespDTO(optionalAdmin.get());
            return Optional.of(AdminResDTO);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<AdminResDTO> findAdminByEmail(String email) {
        Optional<Admin> optionalAdmin = this.repository.findAdminByEmail(email);
        if (optionalAdmin.isPresent()) {
            AdminResDTO AdminResDTO = mapper.toRespDTO(optionalAdmin.get());
            return Optional.of(AdminResDTO);
        } else {
            return Optional.empty();
        }
    }




//----------------------------------------------------------------------------------------------------------------delete

    @Override
    public void deleteAdmin(AdminReqDTO req) {
        Admin emp = this.repository.findById(req.getIdAdmin()).get();
        emp.setDeletedAt(LocalDateTime.now());
        emp.setStatut(false);
        repository.save(emp);
    }

    @Override
    public void deleteAdminById(int id) {
        Admin emp = this.repository.findById(id).get();
        emp.setDeletedAt(LocalDateTime.now());
        emp.setStatut(false);
        repository.save(emp);
    }


//-----------------------------------------------------------------------------------------------------------------login

    @Override
    public Optional<String> findPasswordByEmail(String email) {
        return this.repository.findPasswordByEmail(email);
    }


//-----------------------------------------------------------------------------------------------------------implements
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        // Use Optional's orElseThrow to handle the case where no value is present
        Admin user = this.repository.findAdminByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé avec l'email: " + username));

        return new UtulisateurDetail(user);
    }



}