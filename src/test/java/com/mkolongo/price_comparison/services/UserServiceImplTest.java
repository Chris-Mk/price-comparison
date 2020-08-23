package com.mkolongo.price_comparison.services;

import com.mkolongo.price_comparison.domain.entities.Role;
import com.mkolongo.price_comparison.domain.entities.User;
import com.mkolongo.price_comparison.domain.models.binding.UserEditModel;
import com.mkolongo.price_comparison.domain.models.service.UserServiceModel;
import com.mkolongo.price_comparison.domain.models.view.UserProfileModel;
import com.mkolongo.price_comparison.domain.models.view.UserViewModel;
import com.mkolongo.price_comparison.exception.EmailExistException;
import com.mkolongo.price_comparison.exception.InvalidPassword;
import com.mkolongo.price_comparison.exception.PasswordsDontMatchException;
import com.mkolongo.price_comparison.exception.UsernameExistException;
import com.mkolongo.price_comparison.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    UserRepository mockUserRepository;
    @Mock
    ModelMapper mockMapper;
    @Mock
    BCryptPasswordEncoder mockPasswordEncoder;

    @InjectMocks
    UserServiceImpl mockUserService;

    UserServiceModel mockServiceModel;
    User mockUser;

    @BeforeEach
    void setUp() {
        mockServiceModel = new UserServiceModel() {{
//            setUsername("test username");
            setEmail("test@test.com");
            setPassword("test password");
        }};

        mockUser = new User() {{
            setId("testId");
            setUsername("test username");
            setEmail("test@test.com");
            setPassword("test password");
            setRoles(new HashSet<>());
        }};
    }

    @Test
    void test_registeringUser_withCorrectInput_worksFine() {
        when(mockMapper.map(mockServiceModel, User.class)).thenReturn(mockUser);
        when(mockPasswordEncoder.encode(anyString())).thenReturn("test password123");
        when(mockUserRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        when(mockUserRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        mockUserService.register(mockServiceModel);

        verify(mockUserRepository).saveAndFlush(mockUser);
    }

    @Test
    void registerUser_withExistingUsername_throwsException() {
        when(mockUserRepository.findByUsername(anyString())).thenReturn(Optional.ofNullable(mockUser));

        assertThrows(UsernameExistException.class, () -> mockUserService.register(mockServiceModel));
    }

    @Test
    void registerUser_withExistingEmail_throwsException() {
        when(mockUserRepository.findByEmail(anyString())).thenReturn(Optional.ofNullable(mockUser));

        assertThrows(EmailExistException.class, () -> mockUserService.register(mockServiceModel));
    }

    @Test
    void registerUser_withNonMatchingPasswords_throwsException() {
        UserServiceModel serviceModel = new UserServiceModel() {{
            setPassword("test1");
        }};

        assertThrows(PasswordsDontMatchException.class, () -> mockUserService.register(serviceModel));
    }

    @Test
    void getAllUsers_whenPresent_returnsAll() {
        mockUser.setRoles(Set.of(Role.ROLE_USER));

        when(mockUserRepository.findAll()).thenReturn(List.of(mockUser));

        UserViewModel viewModel = new UserViewModel() {{
            setUsername(mockUser.getUsername());
            setEmail(mockUser.getEmail());
        }};

        when(mockMapper.map(mockUser, UserViewModel.class)).thenReturn(viewModel);

        final List<UserViewModel> allUsers = mockUserService.getAllUsers();

        assertEquals(1, allUsers.size());
        assertEquals(viewModel.getUsername(), allUsers.get(0).getUsername());
        assertEquals(viewModel.getEmail(), allUsers.get(0).getEmail());
    }

    @Test
    void getAllUsers_whenAbsent_returnsEmptyList() {
        when(mockUserRepository.findAll()).thenReturn(new ArrayList<>());

        final List<UserViewModel> allUsers = mockUserService.getAllUsers();

        assertEquals(0, allUsers.size());
    }

//    @Test
//    void editRole_withValidUserId_worksFine() {
//        mockUser.getRoles().add(Role.ROLE_MODERATOR);
//        when(mockUserRepository.findById(anyString())).thenReturn(Optional.ofNullable(mockUser));
//
//        mockUserService.editRole(mockUser.getId(), Role.ROLE_ADMIN);
//
//        assertEquals(2, mockUser.getRoles().size());
//        assertTrue(mockUser.getRoles().contains(Role.ROLE_ADMIN));
//        assertTrue(mockUser.getRoles().contains(Role.ROLE_MODERATOR));
//
//        verify(mockUserRepository).saveAndFlush(mockUser);
//    }

    @Test
    void editRole_withValidUserIdAndRoleUser_DeletesOtherRoles() {
        when(mockUserRepository.findById(anyString())).thenReturn(Optional.ofNullable(mockUser));

        mockUserService.editRole(mockUser.getId(), Role.ROLE_USER);

        assertEquals(1, mockUser.getRoles().size());
        assertTrue(mockUser.getRoles().contains(Role.ROLE_USER));

        verify(mockUserRepository).saveAndFlush(mockUser);
    }

//    @Test
//    void editRole_withInvalidUserId_throwsException() {
//        when(mockUserRepository.findById(anyString())).thenReturn(Optional.empty());
//
//        assertThrows(UsernameNotFoundException.class, () -> mockUserService.editRole(anyString(), Role.ROLE_MODERATOR));
//    }

    @Test
    void getUserById_withValidId_returnsUser() {
        when(mockUserRepository.findById(mockUser.getId())).thenReturn(Optional.ofNullable(mockUser));

        final UserEditModel editModel = new UserEditModel() {{
            setUsername(mockUser.getUsername());
            setEmail(mockUser.getEmail());
        }};

        when(mockMapper.map(mockUser, UserEditModel.class)).thenReturn(editModel);

        final UserEditModel user = mockUserService.getUserById(mockUser.getId());

        assertEquals(mockUser.getUsername(), user.getUsername());
        assertEquals(mockUser.getEmail(), user.getEmail());
    }

    @Test
    void getUserById_withInvalidId_throwsException() {
        when(mockUserRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> mockUserService.getUserById(anyString()));
    }

    @Test
    void getUserByUsername_withValidUsername_returnsUser() {
        when(mockUserRepository.findByUsername(mockUser.getUsername())).thenReturn(Optional.ofNullable(mockUser));

        final UserProfileModel profileModel = new UserProfileModel() {{
            setUsername(mockUser.getUsername());
            setEmail(mockUser.getEmail());
        }};

        when(mockMapper.map(mockUser, UserProfileModel.class)).thenReturn(profileModel);

        final UserProfileModel user = mockUserService.getUserByEmail(mockUser.getUsername());

        assertEquals(profileModel.getUsername(), user.getUsername());
        assertEquals(profileModel.getEmail(), user.getEmail());
    }

    @Test
    void getUserByUsername_withNonExistingUsername_throwsException() {
        when(mockUserRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> mockUserService.getUserByEmail(anyString()));
    }

    @Test
    void loadUserByUsername_ifPresent_returnsUser() {
        when(mockUserRepository.findByUsername(mockUser.getUsername())).thenReturn(Optional.ofNullable(mockUser));

        final UserDetails userDetails = mockUserService.loadUserByUsername(mockUser.getUsername());

        assertEquals(mockUser.getUsername(), userDetails.getUsername());
        assertEquals(mockUser.getPassword(), userDetails.getPassword());
        assertEquals(mockUser.getRoles().size(), userDetails.getAuthorities().size());

    }

    @Test
    void loadUserByUsername_ifAbsent_throwsException() {
        when(mockUserRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> mockUserService.loadUserByUsername(anyString()));
    }

    @Test
    void editUser_withNonMatchingOldPasswords_throwsException() {
        when(mockUserRepository.findById(mockUser.getId())).thenReturn(Optional.ofNullable(mockUser));

        final UserEditModel editModel = new UserEditModel() {{
            setId(mockUser.getId());
            setOldPassword("wrong old password");
            setUsername(mockUser.getUsername());
        }};

        when(mockPasswordEncoder.matches(editModel.getOldPassword(), mockUser.getPassword())).thenReturn(false);

        assertThrows(InvalidPassword.class, () -> mockUserService.editUser(editModel));
    }

    @Test
    void editUser_username_withExistingUsername_throwsException() {
        when(mockUserRepository.findById(mockUser.getId())).thenReturn(Optional.ofNullable(mockUser));

        final UserEditModel editModel = new UserEditModel() {{
            setId(mockUser.getId());
            setOldPassword(mockUser.getPassword());
            setUsername("new username");
        }};

        when(mockPasswordEncoder.matches(editModel.getOldPassword(), mockUser.getPassword())).thenReturn(true);
        when(mockUserRepository.findByUsername(editModel.getUsername())).thenReturn(Optional.of(new User()));

        assertThrows(UsernameExistException.class, () -> mockUserService.editUser(editModel));
    }

    @Test
    void editUser_password_withNonMatchingNewPasswords_throwsException() {
        when(mockUserRepository.findById(mockUser.getId())).thenReturn(Optional.ofNullable(mockUser));

        final UserEditModel editModel = new UserEditModel() {{
            setId(mockUser.getId());
            setOldPassword(mockUser.getPassword());
            setUsername(mockUser.getUsername());
            setNewPassword("new password");
            setConfirmNewPassword("confirm new password");
        }};

        when(mockPasswordEncoder.matches(editModel.getOldPassword(), mockUser.getPassword())).thenReturn(true);

        assertThrows(PasswordsDontMatchException.class, () -> mockUserService.editUser(editModel));
    }

    @Test
    void editUser_email_withExistingEmail_throwsException() {
        when(mockUserRepository.findById(mockUser.getId())).thenReturn(Optional.ofNullable(mockUser));

        final UserEditModel editModel = new UserEditModel() {{
            setId(mockUser.getId());
            setOldPassword(mockUser.getPassword());
            setUsername(mockUser.getUsername());
            setEmail("newTestEmail.testEmail.test");
        }};

        when(mockPasswordEncoder.matches(editModel.getOldPassword(), mockUser.getPassword())).thenReturn(true);
        when(mockUserRepository.findByEmail(editModel.getEmail())).thenReturn(Optional.of(new User()));

        assertThrows(EmailExistException.class, () -> mockUserService.editUser(editModel));
    }

    @Test
    @WithMockUser
    void editUser_withValidInputData_worksFine() {
        when(mockUserRepository.findById(mockUser.getId())).thenReturn(Optional.ofNullable(mockUser));

        final UserEditModel editModel = new UserEditModel() {{
            setId(mockUser.getId());
            setOldPassword(mockUser.getPassword());
            setUsername(mockUser.getUsername());
            setEmail("newTestEmail.testEmail.test");
            setNewPassword("new password");
            setConfirmNewPassword("new password");
        }};

        when(mockPasswordEncoder.matches(editModel.getOldPassword(), mockUser.getPassword())).thenReturn(true);
        when(mockUserRepository.findByEmail(editModel.getEmail())).thenReturn(Optional.empty());
        when(mockPasswordEncoder.encode(editModel.getNewPassword())).thenReturn(editModel.getNewPassword());

        mockUserService.editUser(editModel);

        assertEquals(editModel.getNewPassword(), mockUser.getPassword());
        assertEquals(editModel.getEmail(), mockUser.getEmail());
        assertEquals(editModel.getUsername(), mockUser.getUsername());

        verify(mockUserRepository).saveAndFlush(mockUser);
    }

    @Test
    void editUser_withInvalidUserId_throwsException() {
        when(mockUserRepository.findById(anyString())).thenReturn(Optional.empty());

        final UserEditModel editModel = new UserEditModel() {{
            setId("");
        }};

        assertThrows(UsernameNotFoundException.class, () -> mockUserService.editUser(editModel));
    }
}