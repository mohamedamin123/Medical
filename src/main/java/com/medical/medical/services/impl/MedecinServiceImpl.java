package com.medical.medical.services.impl;

import com.medical.medical.exceptions.UserException;
import com.medical.medical.models.dto.req.MedecinReqDTO;
import com.medical.medical.models.dto.res.MedecinResDTO;
import com.medical.medical.models.entity.Medecin;
import com.medical.medical.models.mapper.MedecinMapper;
import com.medical.medical.repository.MedecinRepo;
import com.medical.medical.security.LoginViewModel;
import com.medical.medical.security.UtulisateurDetail;
import com.medical.medical.services.interf.MedecinService;
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
public class MedecinServiceImpl implements MedecinService , UserDetailsService {

    private final MedecinMapper mapper;

    private final MedecinRepo repository;

    private PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();

//------------------------------------------------------------------------------------------------------------------save

    @Override
    public MedecinResDTO saveMedecin(MedecinReqDTO req) {
        Medecin emp = mapper.toEntity(req);
        emp.setPassword(this.passwordEncoder.encode(emp.getPassword()));
        repository.save(emp);
        return mapper.toRespDTO(emp);
    }

    @Override
    public MedecinResDTO updateMedecin(MedecinReqDTO req) {
        // Convertir DTO en entité pour obtenir les nouvelles données
        Medecin updatedMedecin = mapper.toEntity(req);

        // Trouver le médecin existant par son email
        Optional<Medecin> existingMedecinOptional = this.repository.findMedecinByEmail(updatedMedecin.getEmail());

        if (existingMedecinOptional.isPresent()) {
            // Obtenir le médecin existant
            Medecin existingMedecin = existingMedecinOptional.get();
            System.out.println(existingMedecin);

            // Mettre à jour les champs pertinents avec les nouvelles valeurs
            existingMedecin.setNom(updatedMedecin.getNom());
            existingMedecin.setPrenom(updatedMedecin.getPrenom());
            existingMedecin.setTel(updatedMedecin.getTel());
            existingMedecin.setEmail(updatedMedecin.getEmail());
            existingMedecin.setDateDeNaissance(updatedMedecin.getDateDeNaissance());
            existingMedecin.setSpecialite(updatedMedecin.getSpecialite());

            // Mettre à jour la date de modification si nécessaire
            existingMedecin.setUpdatedAt(LocalDateTime.now()); // Assurez-vous que vous avez un champ 'updatedAt' dans votre entité

            // Réinitialiser 'deletedAt' si nécessaire
            existingMedecin.setDeletedAt(null);

            // Enregistrer les modifications dans la base de données
            Medecin savedMedecin = repository.save(existingMedecin);
            System.out.println(savedMedecin);

            // Convertir l'entité mise à jour en DTO de réponse
            return mapper.toRespDTO(savedMedecin);
        }

        // Retourner null si le médecin n'a pas été trouvé
        return null;
    }

//------------------------------------------------------------------------------------------------------------------find

    @Override
    public List<MedecinResDTO> findAllMedecin() {

        List<Medecin> users = this.repository.findAll();
        return mapper.toAllRespDTO(users);
    }


    @Override
    public Optional<MedecinResDTO> findMedecinById(int id) {
        Optional<Medecin> optionalMedecin = this.repository.findById(id);
        if (optionalMedecin.isPresent()) {
            MedecinResDTO MedecinResDTO = mapper.toRespDTO(optionalMedecin.get());
            return Optional.of(MedecinResDTO);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<MedecinResDTO> findMedecinByEmail(String email) {
        Optional<Medecin> optionalMedecin = this.repository.findMedecinByEmail(email);
        if (optionalMedecin.isPresent()) {
            MedecinResDTO MedecinResDTO = mapper.toRespDTO(optionalMedecin.get());
            return Optional.of(MedecinResDTO);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<MedecinResDTO> findMedecinByTel(String tel) {
        Optional<Medecin> optionalMedecin = this.repository.findMedecinByTel(tel);
        if (optionalMedecin.isPresent()) {
            MedecinResDTO MedecinResDTO = mapper.toRespDTO(optionalMedecin.get());
            return Optional.of(MedecinResDTO);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<MedecinResDTO> findMedecinsByDateDeNaissance(LocalDate dateDeNaissance) {
        List<Medecin> users = this.repository.findMedecinsByDateDeNaissance(dateDeNaissance);
        return mapper.toAllRespDTO(users);
    }

    @Override
    public List<MedecinResDTO> findMedecinsByPrenomOrNom(String prenom, String nom) {
        List<Medecin> users = this.repository.findMedecinsByPrenomOrNom(prenom, nom);
        return mapper.toAllRespDTO(users);
    }

    @Override
    public List<MedecinResDTO> findAllMedecinAfterDelete() {
        List<Medecin> medecins = this.repository.findAll();
        List<Medecin> filteredMedecins = medecins.stream()
                .filter(medecin -> medecin.getDeletedAt() == null)
                .collect(Collectors.toList());
        return mapper.toAllRespDTO(filteredMedecins);
    }

    @Override
    public Optional<MedecinResDTO> findMedecinByIdAfterDelete(int id) {
        Optional<Medecin> optionalMedecin = this.repository.findById(id);
        if (optionalMedecin.isPresent() && optionalMedecin.get().getDeletedAt() == null) {
            MedecinResDTO medecinResDTO = mapper.toRespDTO(optionalMedecin.get());
            return Optional.of(medecinResDTO);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<MedecinResDTO> findMedecinByEmailAfterDelete(String email) {
        Optional<Medecin> optionalMedecin = this.repository.findMedecinByEmail(email);
        if (optionalMedecin.isPresent() && optionalMedecin.get().getDeletedAt() == null) {
            MedecinResDTO medecinResDTO = mapper.toRespDTO(optionalMedecin.get());
            return Optional.of(medecinResDTO);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<MedecinResDTO> findMedecinByTelAfterDelete(String tel) {
        Optional<Medecin> optionalMedecin = this.repository.findMedecinByTel(tel);
        if (optionalMedecin.isPresent() && optionalMedecin.get().getDeletedAt() == null) {
            MedecinResDTO medecinResDTO = mapper.toRespDTO(optionalMedecin.get());
            return Optional.of(medecinResDTO);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<MedecinResDTO> findMedecinsByDateDeNaissanceAfterDelete(LocalDate dateDeNaissance) {
        List<Medecin> medecins = this.repository.findMedecinsByDateDeNaissance(dateDeNaissance);
        List<Medecin> filteredMedecins = medecins.stream()
                .filter(medecin -> medecin.getDeletedAt() == null)
                .collect(Collectors.toList());
        return mapper.toAllRespDTO(filteredMedecins);
    }

    @Override
    public List<MedecinResDTO> findMedecinsByPrenomOrNomAfterDelete(String prenom, String nom) {
        List<Medecin> medecins = this.repository.findMedecinsByPrenomOrNom(prenom, nom);
        List<Medecin> filteredMedecins = medecins.stream()
                .filter(medecin -> medecin.getDeletedAt() == null)
                .collect(Collectors.toList());
        return mapper.toAllRespDTO(filteredMedecins);
    }

//----------------------------------------------------------------------------------------------------------------delete

    @Override
    public void deleteMedecin(MedecinReqDTO req) {
        Medecin emp = this.repository.findById(req.getIdMedecin()).get();
        emp.setDeletedAt(LocalDateTime.now());
        emp.setStatut(false);
        repository.save(emp);
    }


    @Override
    public void deleteMedecinById(int id) {
        Medecin emp = this.repository.findById(id).get();
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
        Medecin user = this.repository.findMedecinByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé avec l'email: " + username));

        return new UtulisateurDetail(user);
    }



}