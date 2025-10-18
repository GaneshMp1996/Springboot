package org.repository;

import org.entity.UserEntity;

import java.awt.print.Pageable;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);


    @Query("select u from UserEntity u where " +
            "(:q is null or lower(u.username) like lower(concat('%',:q,'%')) or lower(u.fullName) like lower(concat('%',:q,'%')))" )
    Page<UserEntity> search(@Param("q") String q, Pageable pageable);
}
