package com.zikan.fintech_Bank_App.repository;

import com.zikan.fintech_Bank_App.entity.Role;
import com.zikan.fintech_Bank_App.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(null); // Allow JPA to auto-generate the ID
        user.setFirstName("Zikan");
        user.setLastName("Mike");
        user.setOtherName("Bless");
        user.setAddress("Lekki");
        user.setEmail("skytecomputer@gmail.com");
        user.setPassword("zikan");
        user.setGender("Male");
        user.setRole(Role.ROLE_USER);
        user.setStateOfOrigin("Anambra");
        user.setStatus("ACTIVE");
        user.setAccountBalance(BigDecimal.valueOf(1000.00));
        user.setPhoneNumber("08187422213");
        userRepository.save(user);
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
        user = null; // Clear reference to the user
    }

    @Test
    void testFindByEmail_Found() {
        Optional<User> foundUser = userRepository.findByEmail("skytecomputer@gmail.com");
        assertThat(foundUser).isPresent()
                .hasValueSatisfying(u -> assertThat(u.getEmail()).isEqualTo(user.getEmail()));
    }
}
