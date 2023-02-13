package com.biglobby.filter;

import java.io.IOException;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.biglobby.entity.User;
import com.biglobby.repository.UserRepository;
import com.biglobby.utils.jwt.JwtTokenUtil;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserRepository userRepo;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header != null) {

            if (header.isEmpty() || !header.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }

            final String token = header.split(" ")[1].trim();
            String username = jwtTokenUtil.getUsernameFromToken(token);
            User user = userRepo.findByUsername(username).orElse(null);
            if (user == null) {
                filterChain.doFilter(request, response);
                return;
            }
            UserDetails userDetails = org.springframework.security.core.userdetails.User
                    .withUsername(user.getUsername()).password(user.getPassword()).roles(user.getAuthorities().stream()
                            .map(o -> o.getRole().getRole()).collect(Collectors.toList()).toArray(new String[0]))
                    .build();

            if (!jwtTokenUtil.validate(token, userDetails)) {
                filterChain.doFilter(request, response);
                return;
            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,
                    userDetails.getPassword(),
                    userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
            return;
        }
        filterChain.doFilter(request, response);
    }

}
