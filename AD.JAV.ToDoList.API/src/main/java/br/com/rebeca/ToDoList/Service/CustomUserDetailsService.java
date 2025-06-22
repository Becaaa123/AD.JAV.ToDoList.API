package br.com.rebeca.ToDoList.Service;

import br.com.rebeca.ToDoList.Model.UsuarioModel;
import br.com.rebeca.ToDoList.Repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
//        UsuarioModel usuarioModel = usuarioRepository.findByEmail(email)
//                .orElseThrow(() -> new UsernameNotFoundException("Usuário com email " + email + " não encontrado"));
//
//        return User.builder()
//                .username(usuarioModel.getEmail())
//                .password(usuarioModel.getSenha())
//                .authorities(Collections.emptyList())
//                .build();
//    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println(">>> Procurando usuário: " + email);
        return usuarioRepository.findByEmail(email)
                .map(usuario -> User.builder()
                        .username(usuario.getEmail())
                        .password(usuario.getSenha())
                        .authorities(Collections.emptyList())
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("Usuário com email " + email + " não encontrado"));
    }

}