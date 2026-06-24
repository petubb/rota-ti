package com.rotati.security;

import com.rotati.model.Conta;
import com.rotati.repository.ContaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class ContaUserDetailsService implements UserDetailsService {

    private final ContaRepository contaRepository;

    public ContaUserDetailsService(ContaRepository contaRepository) {
        this.contaRepository = contaRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        String emailNormalizado = email == null ? "" : email.trim().toLowerCase(Locale.ROOT);
        if (emailNormalizado.length() > 150) {
            throw new UsernameNotFoundException("Credenciais invalidas.");
        }
        Conta conta = contaRepository.findByEmailIgnoreCase(emailNormalizado)
                .orElseThrow(() -> new UsernameNotFoundException("Credenciais invalidas."));
        return new ContaPrincipal(conta);
    }
}
