package com.digs.dig0.service;

import com.digs.dig0.model.MyUserDetails;
import com.digs.dig0.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Copyright to Digs LLC
 * @author Ike Kennedy
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsServices implements UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;


    /**
     * Locates the user based on the username. In the actual implementation, the search
     * may possibly be case sensitive, or case insensitive depending on how the
     * implementation instance is configured. In this case, the <code>UserDetails</code>
     * object that comes back may have a username that is of a different case than what
     * was actually requested..
     *
     * @param username the username identifying the user whose data is required.
     * @return a fully populated user record (never <code>null</code>)
     * @throws UsernameNotFoundException if the user could not be found or the user has no
     *                                   GrantedAuthority
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("INSIDE AUTHENTICATION SERVICE: Loading by username....: "+ username);

        return userRepository.findByUsername(username)
                .map(MyUserDetails::new)
                .orElseThrow(()-> new UsernameNotFoundException("User not found"));
    }

}
