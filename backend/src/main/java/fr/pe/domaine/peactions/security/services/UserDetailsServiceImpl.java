package fr.pe.domaine.peactions.security.services;

import fr.pe.domaine.peactions.model.Etablissement;
import fr.pe.domaine.peactions.repository.EtablissementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    EtablissementRepository etablissementRepository;


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String codeEtablissementRepository) throws UsernameNotFoundException {
        Etablissement etablissement = etablissementRepository.findByCodeEtablissement(codeEtablissementRepository)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with identifiant: " + codeEtablissementRepository));

        return UserDetailsImpl.build(etablissement);
    }

}
