package com.example.testing.advanced.service;

import com.example.testing.advanced.model.User;
import com.example.testing.advanced.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    @BeforeEach
    public void setUp()
    {
        MockitoAnnotations.openMocks(this);
    }

//    @Test
    @ParameterizedTest
    @ValueSource(strings = {"daniel@yahoo.com", "email@yahoo.com", "email2@yahoo.com"})
    @DisplayName("Get user by id when User Exists")
    public void getUserById_whenUserExists_thenReturnUser(String email)
    {
        //given
        User user = new User(1L, "Daniel", email);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        //when
        Optional<User> result = userService.getUserById(user.getId());
        //then
        assertTrue(result.isPresent());
        assertEquals("Daniel", result.get().getFullName());
        assertEquals(email, result.get().getEmail());
    }

    @Test
    @DisplayName("Get user by id when User Exists")
    public void getUserById_whenDoesNotExists_thenReturnUser()
    {

        assertThrows(RuntimeException.class, ()->userService.getUserById(1L), "User does Not exist");
    }

//    @Test
    @RepeatedTest(8)
    public void saveUser_thenReturnUser()
    {
        User newUser = new User(1L, "Daniel", "daniel@yahoo.com");
        when(userRepository.save(newUser)).thenReturn(newUser);

        User savedUser = userService.saveUser(newUser);

        verify(userRepository, times(1)).save(newUser);
        assertEquals("daniel@yahoo.com", savedUser.getEmail());
    }

    @Test
    public void deleteTest()
    {
       User newUser = new User(1L, "Daniel", "daniel@yahoo.com");
       when(userRepository.findById(newUser.getId())).thenReturn(Optional.of(newUser));

       userService.deleteUser(newUser.getId());

       verify(userRepository, times(1)).deleteById(newUser.getId());
    }

    @Test
    @DisplayName("Get user by full name when User does not exist")
    public void getUserByFullName_notExists()
    {
        assertThrows(RuntimeException.class, ()->userService.getUserById(1L), "User does Not exist");
    }

    @Test
    @DisplayName("Get user by full name when User Exists")
    public void getUserByFullName_exists()
    {
        User user = new User(1L, "Daniel", "daniel@yahoo.com");
        when(userRepository.findByFullName(user.getFullName())).thenReturn(Optional.of(user));

        Optional<User> result = userService.getUserByFullName(user.getFullName());

        assertTrue(result.isPresent());
        assertEquals("Daniel", result.get().getFullName());
    }
}
//tests: deleteUser - asemanator cu save User - se folosește doar verify
//getUserByFullName - când userul exista, când userul nu exista

