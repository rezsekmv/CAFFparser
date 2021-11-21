package hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.service.implementation;

import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.dto.UserDto;
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
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.util.ContextUtil.ADMIN;
import static hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.util.ContextUtil.ANONYMOUS_USER;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

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
        if (username.length() <= 3) {
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
        return userMapper.toDto(findById(id));
    }

    private User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(CbNotFoundException::new);
    }

    @Override
    public List<UserDto> getAll() {
        return userMapper.toDtoList(userRepository.findAll());
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(CbNotFoundException::new);
    }

    @Override
    public UserDto getMe() {
        log.trace("UserService : getMe");
        return userMapper.toDto(getCurrentUser());
    }

    private User getCurrentUser() {
        return findByUsername(ContextUtil.getCurrentUsername());
    }

    @Override
    public UserDto updateMe(UserDto userDto) {
        log.trace("UserService : updateMe, userDto=[{}]", userDto);
        User updatedUser = userMapper.update(getCurrentUser(), userDto);
        return userMapper.toDto(updatedUser);
    }
}
