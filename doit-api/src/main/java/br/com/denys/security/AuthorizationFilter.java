package br.com.denys.security;

import br.com.denys.domain.User;
import br.com.denys.repository.UserRepository;
import br.com.denys.service.TokenService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.Optional;

public class AuthorizationFilter extends OncePerRequestFilter {

    private final TokenService tokenService;

    private final UserRepository userRepository;

    public AuthorizationFilter(TokenService tokenService, UserRepository userRepository) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = extractToken(request);
        boolean valid = tokenService.valid(token);
        if(valid) {
            authorize(token);
        }

        filterChain.doFilter(request, response);
    }

    private void authorize(String token) throws UserPrincipalNotFoundException {
        Long id = tokenService.getUserId(token);
        User user = userRepository.findById(id).get();


        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    private String extractToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");

        if(header == null || !header.startsWith("Bearer ")) return null;

        return header.substring(7, header.length());
    }
}
