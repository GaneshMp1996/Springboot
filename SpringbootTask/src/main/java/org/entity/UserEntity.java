package org.entity;

import lombok.Data;

@Data
@Entity
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false, unique = true)
    private String username;


    @Column(nullable = false)
    private String fullName;


    @Column(nullable = false)
    private String passwordHash; // store a bcrypt hash in real app


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AddressEntity> addresses = new ArrayList<>();


    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private Set<String> roles = new HashSet<>();


    private Instant createdAt;
    private Instant updatedAt;


    @PrePersist
    public void prePersist() {
        createdAt = updatedAt = Instant.now();
    }


    @PreUpdate
    public void preUpdate() {
        updatedAt = Instant.now();
    }
}

}
