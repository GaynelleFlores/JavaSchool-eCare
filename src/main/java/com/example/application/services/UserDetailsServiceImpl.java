package com.example.application.services;

import com.example.application.UserDetailsImpl;
import com.example.application.dto.ClientDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;

@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final ClientsService clientsService;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        ClientDTO client = clientsService.getClientByLogin(username);
        if (client == null) {
            throw new UsernameNotFoundException("Could not find user");
        }
        return new UserDetailsImpl(client);
    }

}