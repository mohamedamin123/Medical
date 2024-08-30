package com.medical.medical.security;

import com.medical.medical.ennum.Utilisateurs;
import com.medical.medical.models.entity.Admin;
import com.medical.medical.models.entity.Medecin;
import com.medical.medical.models.entity.Secretaire;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UtulisateurDetail implements UserDetails {

    private final Medecin medecin;
    private final Secretaire secretaire;

    private final Admin admin;

    // Constructeur pour Medecin
    public UtulisateurDetail(Medecin medecin) {
        this.medecin = medecin;
        this.secretaire = null;
        this.admin=null;
    }

    // Constructeur pour Secretaire
    public UtulisateurDetail(Secretaire secretaire) {
        this.secretaire = secretaire;
        this.medecin = null;
        this.admin=null;
    }

    public UtulisateurDetail(Admin admin) {
        this.secretaire = null;
        this.medecin = null;
        this.admin=admin;
    }

    private Utilisateurs getRole() {
        if (medecin != null) {
            return medecin.getRole();
        } else if (secretaire != null) {
            return secretaire.getRole();
        }
        else if (admin != null) {
            return admin.getRole();
        }
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        Utilisateurs roleEnum = getRole();
        if (roleEnum != null) {
            String role = "ROLE_" + roleEnum.name();
            GrantedAuthority authority = new SimpleGrantedAuthority(role);
            authorities.add(authority);
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        if (medecin != null) {
            return medecin.getPassword();
        } else if (secretaire != null) {
            return secretaire.getPassword();
        } else if (admin != null) {
            return admin.getPassword();
        }
        return null;
    }

    @Override
    public String getUsername() {
        if (medecin != null) {
            return medecin.getEmail();
        } else if (secretaire != null) {
            return secretaire.getEmail();
        }
        else if (admin != null) {
            return admin.getEmail();
        }
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
