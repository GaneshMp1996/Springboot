package org.service;

import org.dto.UserDto;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    UserDto createUser(UserDto dto, String actor);
    UserDto getUser(Long id);
    Page<UserDto> search(String q, Pageable pageable);
    UserDto updateUser(Long id, UserDto dto, String actor);
    void deleteUser(Long id, String actor);
    ImportResult importCsv(MultipartFile csvFile, String actor);

}
