package com.app.services.implementations.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.userdetails.User;

import com.app.domain.user.Person;
import com.app.domain.user.Role;
import com.app.controller.dto.LoginRequestDTO;
import com.app.controller.dto.SignupFieldsDTO;
import com.app.domain.user.ForoUser;
import com.app.repositories.user.UserRepositoryImp;
import com.app.resources.JwtUtil;
import com.app.services.interfaces.user.IPersonService;
import com.app.services.interfaces.user.IUserService;

@Service
public class UserService implements IUserService {

  private final UserRepositoryImp userRepository;
  private final IPersonService personService;
  private final PasswordEncoder passwordEncoder;
  private final JwtUtil jwtUtil;

  @Autowired
  public UserService(IPersonService personService, UserRepositoryImp userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
    this.userRepository = userRepository;
    this.personService = personService;
    this.passwordEncoder = passwordEncoder;
    this.jwtUtil = jwtUtil;
  }

  @Override
  @Transactional
  public ForoUser registerUser(SignupFieldsDTO fields) {
    try {
      Person personCreated = personService.createPerson(fields.firstName(), fields.lastName(), fields.email(), fields.birthDay());
      Role userRole = Role.builder().name("USER").build();
      Role adminRole = Role.builder().name("ADMIN").build();

      ForoUser userCreated = ForoUser.builder()
          .username(fields.username())
          .password(passwordEncoder.encode(fields.password()))  // Asegúrate de codificar la contraseña al crear el usuario
          .person(personCreated)
          .roles(Set.of(userRole, adminRole))
          .build();
      return userRepository.save(userCreated);
    } catch (Exception e) {
      throw new RuntimeException("Error al crear usuario");
    }
  }

  @Override
  @Transactional(readOnly = true)
  public ForoUser getUserbyId(Long id) {
    return userRepository.findById(id)
        .orElseThrow();
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    ForoUser userFound = userRepository.findUserByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("El usuario " + username + " no fue encontrado"));

    List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

    userFound.getRoles().forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getName()))));

    return new User(userFound.getUsername(),
        userFound.getPassword(),
        userFound.isEnabled(),
        userFound.isAccountNoExpired(),
        userFound.isCredentialNoExpired(),
        userFound.isAccountNoLocked(),
        authorityList);
  }

  public Authentication authenticate(String username, String password) {
    UserDetails userFound = this.loadUserByUsername(username);

    if (userFound == null) {
      throw new BadCredentialsException("Invalid username or password!");
    }

    if (!passwordEncoder.matches(password, userFound.getPassword())) {
      throw new BadCredentialsException("Invalid username or password!");
    }

    return new UsernamePasswordAuthenticationToken(username, userFound.getPassword(), userFound.getAuthorities());
  }

  public String loginUser(LoginRequestDTO loginRequest) {
    String username = loginRequest.username();
    String password = loginRequest.password();

    Authentication auth = this.authenticate(username, password);
    SecurityContextHolder.getContext().setAuthentication(auth);

    return this.jwtUtil.generateToken(auth);
  }
}
