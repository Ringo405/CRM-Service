package com.agilemonkeys.crm.api.application.mapper;

import com.agilemonkeys.crm.api.application.dto.user.create.CreateUserCommand;
import com.agilemonkeys.crm.api.application.dto.user.create.CreateUserResponse;
import com.agilemonkeys.crm.api.application.dto.user.query.UserQueryResponse;
import com.agilemonkeys.crm.api.application.dto.user.update.UpdateUserResponse;
import com.agilemonkeys.crm.api.domain.user.User;
import com.agilemonkeys.crm.api.domain.valueobject.Password;
import com.agilemonkeys.crm.api.domain.valueobject.Role;
import com.agilemonkeys.crm.api.domain.valueobject.UserId;
import com.agilemonkeys.crm.api.domain.valueobject.Username;
import com.agilemonkeys.crm.api.infrastructure.persistance.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserMapperTest {

    private UserMapper userMapper;

    @BeforeEach
    public void setUp() {
        userMapper = new UserMapper();
    }

    @Test
    void testToEntity() {
        User mockUser = mock(User.class);

        when(mockUser.getId()).thenReturn(new UserId(1L));
        when(mockUser.getUsername()).thenReturn(new Username("pepe23"));
        when(mockUser.getPassword()).thenReturn(new Password("password"));
        when(mockUser.getRole()).thenReturn(Role.ADMIN);
        when(mockUser.getCreatedBy()).thenReturn(1L);
        when(mockUser.getLastModifiedBy()).thenReturn(1L);
        when(mockUser.getCreatedAt()).thenReturn(null);
        when(mockUser.getUpdatedAt()).thenReturn(null);

        UserEntity result = userMapper.toEntity(mockUser);

        assertEquals(1L, result.getId());
        assertEquals("pepe23", result.getUsername());
        assertEquals("password", result.getPassword());
        assertEquals("ADMIN", result.getRole());
        assertEquals(1L, result.getCreatedBy());
        assertEquals(1L, result.getLastModifiedBy());
        assertNull(result.getCreatedAt());
        assertNull(result.getUpdatedAt());
    }

    @Test
    void testToDomain() {
        UserEntity mockEntity = new UserEntity();
        mockEntity.setId(1L);
        mockEntity.setUsername("pepe23");
        mockEntity.setPassword("password");
        mockEntity.setRole("ADMIN");
        mockEntity.setCreatedBy(1L);
        mockEntity.setLastModifiedBy(1L);
        mockEntity.setCreatedAt(null);
        mockEntity.setUpdatedAt(null);

        User result = userMapper.toDomain(mockEntity);

        assertEquals(new UserId(1L), result.getId());
        assertEquals(new Username("pepe23"), result.getUsername());
        assertEquals(new Password("password"), result.getPassword());
        assertEquals(Role.ADMIN, result.getRole());
        assertEquals(1L, result.getCreatedBy());
        assertEquals(1L, result.getLastModifiedBy());
        assertNull(result.getCreatedAt());
        assertNull(result.getUpdatedAt());
    }

    @Test
    void testToUpdateResponse() {
        User mockUser = mock(User.class);

        when(mockUser.getId()).thenReturn(new UserId(1L));
        when(mockUser.getUsername()).thenReturn(new Username("pepe23"));
        when(mockUser.getRole()).thenReturn(Role.ADMIN);
        when(mockUser.getCreatedBy()).thenReturn(1L);
        when(mockUser.getLastModifiedBy()).thenReturn(1L);
        when(mockUser.getCreatedAt()).thenReturn(null);
        when(mockUser.getUpdatedAt()).thenReturn(null);

        UpdateUserResponse result = userMapper.toUpdateResponse(mockUser);

        assertEquals(1L, result.getId());
        assertEquals("pepe23", result.getUsername());
        assertEquals(Role.ADMIN, result.getRole());
        assertEquals(1L, result.getCreatedBy());
        assertEquals(1L, result.getLastModifiedBy());
        assertNull(result.getCreatedAt());
        assertNull(result.getUpdatedAt());
    }

    @Test
    void testToCreateResponse() {
        User mockUser = mock(User.class);

        when(mockUser.getId()).thenReturn(new UserId(1L));
        when(mockUser.getUsername()).thenReturn(new Username("pepe23"));
        when(mockUser.getRole()).thenReturn(Role.ADMIN);
        when(mockUser.getCreatedBy()).thenReturn(1L);
        when(mockUser.getLastModifiedBy()).thenReturn(1L);
        when(mockUser.getCreatedAt()).thenReturn(null);
        when(mockUser.getUpdatedAt()).thenReturn(null);

        CreateUserResponse result = userMapper.toCreateResponse(mockUser);

        assertEquals(1L, result.getId());
        assertEquals("pepe23", result.getUsername());
        assertEquals(Role.ADMIN, result.getRole());
        assertEquals(1L, result.getCreatedBy());
        assertEquals(1L, result.getLastModifiedBy());
        assertNull(result.getCreatedAt());
        assertNull(result.getUpdatedAt());
    }

    @Test
    void testToQueryResponse() {
        User mockUser = mock(User.class);

        when(mockUser.getId()).thenReturn(new UserId(1L));
        when(mockUser.getUsername()).thenReturn(new Username("pepe23"));
        when(mockUser.getRole()).thenReturn(Role.ADMIN);
        when(mockUser.getCreatedBy()).thenReturn(1L);
        when(mockUser.getLastModifiedBy()).thenReturn(1L);
        when(mockUser.getCreatedAt()).thenReturn(null);
        when(mockUser.getUpdatedAt()).thenReturn(null);

        UserQueryResponse result = userMapper.toQueryResponse(mockUser);

        assertEquals(1L, result.getId());
        assertEquals("pepe23", result.getUsername());
        assertEquals(Role.ADMIN, result.getRole());
        assertEquals(1L, result.getCreatedBy());
        assertEquals(1L, result.getLastModifiedBy());
        assertNull(result.getCreatedAt());
        assertNull(result.getUpdatedAt());
    }

    @Test
    void testToDomainWithCommand() {
        CreateUserCommand mockCommand = mock(CreateUserCommand.class);

        when(mockCommand.getUsername()).thenReturn("pepe23");
        when(mockCommand.getPassword()).thenReturn("password");
        when(mockCommand.getRole()).thenReturn(Role.ADMIN);

        User result = userMapper.toDomain(mockCommand);

        assertEquals(new Username("pepe23"), result.getUsername());
        assertEquals(new Password("password"), result.getPassword());
        assertEquals(Role.ADMIN, result.getRole());
    }
}
