package org.service;

import org.dto.AddressDto;
import org.dto.UserDto;
import org.entity.AddressEntity;
import org.entity.UserEntity;
import org.springframework.web.multipart.MultipartFile;

public class UserServiceImpl implements UserService{
    @Override
    public UserDto createUser(UserDto dto, String actor) {
        if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new IllegalArgumentException("username already exists");
        }
        UserEntity e = UserEntity.builder()
                .username(dto.getUsername())
                .fullName(dto.getFullName())
                .passwordHash(hashPassword(dto.getPassword()))
                .roles(dto.getRoles() == null ? Set.of("USER") : dto.getRoles())
                .build();
        if (dto.getAddresses() != null) {
            for (AddressDto ad : dto.getAddresses()) {
                AddressEntity ae = AddressEntity.builder()
                        .line1(ad.getLine1())
                        .line2(ad.getLine2())
                        .city(ad.getCity())
                        .state(ad.getState())
                        .postalCode(ad.getPostalCode())
                        .country(ad.getCountry())
                        .user(e).build();
                e.getAddresses().add(ae);
            }
            userRepository.save(e);
            auditRepository.save(AuditEntry.builder().actor(actor).action("CREATE_USER").details(e.getUsername()).build());
            log.info("User created: {} by {}", e.getUsername(), actor);
            return toDto(e);
    }

    @Override
    public UserDto getUser(Long id) {
            return userRepository.findById(id).map(this::toDto).orElseThrow(() -> new NoSuchElementException("user not found"));
    }

    @Override
    public Page<UserDto> search(String q, Pageable pageable) {
        return null;
    }

    @Override
    public UserDto updateUser(Long id, UserDto dto, String actor) {
            UserEntity e = userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("user not found"));
            e.setFullName(dto.getFullName());
            if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
                e.setPasswordHash(hashPassword(dto.getPassword()));
            }
            if (dto.getRoles() != null) e.setRoles(dto.getRoles());

            e.getAddresses().clear();
            if (dto.getAddresses() != null) {
                for (AddressDto ad : dto.getAddresses()) {
                    AddressEntity ae = AddressEntity.builder()
                            .line1(ad.getLine1())
                            .line2(ad.getLine2())
                            .city(ad.getCity())
                            .state(ad.getState())
                            .postalCode(ad.getPostalCode())
                            .country(ad.getCountry())
                            .user(e).build();
                    e.getAddresses().add(ae);
                }
    }

    @Override
    public void deleteUser(Long id, String actor) {
            UserEntity e = userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("user not found"));
            userRepository.delete(e);
            auditRepository.save(AuditEntry.builder().actor(actor).action("DELETE_USER").details(e.getUsername()).build());
        }
    }

    @Override
    public ImportResult importCsv(MultipartFile csvFile, String actor) {
        return null;
    }
}
