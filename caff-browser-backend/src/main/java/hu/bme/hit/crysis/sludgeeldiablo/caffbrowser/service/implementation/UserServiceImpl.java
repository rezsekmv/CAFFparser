package hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.service.implementation;

import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.dto.PasswordDto;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.dto.UserDto;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.enums.RoleName;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.exception.CbException;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.exception.CbNotFoundException;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.mapper.UserMapper;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.model.User;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.repository.UserRepository;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.service.declaration.UserService;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.util.ContextUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.util.ContextUtil.ADMIN;
import static hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.util.ContextUtil.ANONYMOUS_USER;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.trace("UserService : loadUserByUsername, username=[{}]", username);

        User user = findByUsername(username);
        validateUserExists(user);

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                getAuthorities(user));
    }

    private void validateUserExists(User user) {
        if (user == null) {
            log.error("User not found");
            throw new UsernameNotFoundException("User not found");
        }
    }

    private Set<SimpleGrantedAuthority> getAuthorities(User user) {
        return user.getRoles().stream()
                .map(r -> new SimpleGrantedAuthority(r.getName().toString()))
                .collect(Collectors.toSet());
    }

    @Override
    public UserDto save(UserDto userDto) {
        log.trace("UserService : save, username=[{}]", userDto.getUsername());
        validateUsername(userDto.getUsername());
        validateUsernameDoesNotExist(userDto.getUsername());
        User createdUser = userRepository.save(userMapper.toEntity(userDto));
        return userMapper.toDto(createdUser);
    }

    private void validateUsername(String username) {
        validateUsernameAlphanumeric(username);
        validateUsernameLength(username);
        validateUsernameInvalid(username);
    }

    private void validateUsernameAlphanumeric(String username) {
        if (!StringUtils.isAlphanumeric(username)) {
            throw new CbException("error.user.username.alphanumeric");
        }
    }

    private void validateUsernameLength(String username) {
        if (username.length() < 3) {
            throw new CbException("error.user.username.short");
        }
    }

    private void validateUsernameInvalid(String username) {
        if (ANONYMOUS_USER.equals(username) || username.contains(ADMIN)) {
            throw new CbException("error.user.username.invalid");
        }
    }

    private void validateUsernameDoesNotExist(String username) {
        if (userRepository.existsByUsernameIgnoreCase(username)) {
            throw new CbException("error.user.username.taken");
        }
    }

    @Override
    public UserDto get(Long id) {
        log.trace("UserService : get, id=[{}]", id);
        return userMapper.toDto(findById(id));
    }

    @Override
    public User findById(Long id) {
        log.trace("UserService : findById, id=[{}]", id);
        return userRepository.findById(id)
                .orElseThrow(CbNotFoundException::new);
    }

    @Override
    public Page<UserDto> getAll(Pageable pageable) {
        log.trace("UserService : getAll");
        return userRepository.findAll(pageable).map(userMapper::toDto);
    }

    @Override
    public User findByUsername(String username) {
        log.trace("UserService : updateMe, username=[{}]", username);
        return userRepository.findByUsername(username)
                .orElseThrow(CbNotFoundException::new);
    }

    @Override
    public UserDto getMe() {
        log.trace("UserService : getMe");
        return userMapper.toDto(getCurrentUser());
    }

    @Override
    public User getCurrentUser() {
        log.trace("UserService : getCurrentUser");
        return findByUsername(ContextUtil.getCurrentUsername());
    }

    @Override
    public UserDto updateMe(UserDto userDto) {
        log.trace("UserService : updateMe, userDto=[{}]", userDto);
        User updatedUser = userMapper.update(getCurrentUser(), userDto);
        return userMapper.toDto(updatedUser);
    }

    @Override
    public UserDto update(Long id, UserDto userDto) {
        log.trace("UserService : update, userDto=[{}]", userDto);
        User updatedUser = userMapper.update(findById(id), userDto);
        return userMapper.toDto(updatedUser);
    }

    @Override
    public void delete(Long id) {
        log.trace("UserService : delete, id=[{}]", id);
        userRepository.delete(findById(id));
    }

    @Override
    public void deleteMe() {
        log.trace("UserService : delete");
        userRepository.delete(getCurrentUser());
    }

    @Override
    public void password(PasswordDto passwordDto) {
        log.trace("UserService : password, passwordDto=[{}]", passwordDto);
        validateOldPassword(passwordDto.getOldPassword());
        setNewPassword(passwordDto.getNewPassword());
    }

    private void validateOldPassword(String password) {
        if (!passwordEncoder.matches(password, getCurrentUser().getPassword())) {
            throw new CbException("error.password.invalid");
        }
    }

    private void setNewPassword(String password) {
        getCurrentUser().setPassword(passwordEncoder.encode(password));
    }

    @Override
    public Boolean isCurrentUserAdmin() {
        return getCurrentUser().getRoles().stream()
                .anyMatch(r -> r.getName() == RoleName.ADMINISTRATOR);
    }
}
