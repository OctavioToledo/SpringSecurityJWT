package com.app;

import com.app.persistence.entity.PermissionEntity;
import com.app.persistence.entity.RoleEnum;
import com.app.persistence.entity.RolesEntity;
import com.app.persistence.entity.UserEntity;
import com.app.persistence.entity.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Set;

@SpringBootApplication
public class SpringSecurityAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityAppApplication.class, args);
    }

    //ESTE BEAN ES PARA CREAR Y GUARDAR LOS PERMISOS Y LOS USERS, HARDCODE BASIC
    @Bean
    CommandLineRunner init(UserRepository userRepository) {
        return args -> {
            // CREAR PERMISOS
            PermissionEntity createPermission = PermissionEntity.builder()
                    .name("CREATE")
                    .build();
            PermissionEntity readPermission = PermissionEntity.builder()
                    .name("READ")
                    .build();
            PermissionEntity updatePermission = PermissionEntity.builder()
                    .name("UPDATE")
                    .build();
            PermissionEntity deletePermission = PermissionEntity.builder()
                    .name("DELETE")
                    .build();
            PermissionEntity refactorPermission = PermissionEntity.builder()
                    .name("REFACTOR")
                    .build();

            //CREAR ROLES AHORA
            RolesEntity roleAdmin = RolesEntity.builder()
                    .roleEnum(RoleEnum.ADMIN)
                    .permissionList(Set.of(createPermission, readPermission, updatePermission, deletePermission, refactorPermission))
                    .build();
            RolesEntity roleUser = RolesEntity.builder()
                    .roleEnum(RoleEnum.USER)
                    .permissionList(Set.of(createPermission, readPermission))
                    .build();
            RolesEntity roleGuest = RolesEntity.builder()
                    .roleEnum(RoleEnum.GUEST)
                    .permissionList(Set.of(readPermission))
                    .build();
            RolesEntity roleDeveloper = RolesEntity.builder()
                    .roleEnum(RoleEnum.DEVELOPER)
                    .permissionList(Set.of(createPermission, readPermission, updatePermission, refactorPermission))
                    .build();

            //CREAR USUARIOS
            UserEntity userOctavio = UserEntity.builder()
                    .username("octavio")
                    .password("$2a$10$j125WqnLIzfe3FKb/wYrv.m7nrdT1kuJSBWLxTiuVz8lqUeJWWkA6")
                    .isEnabled(true)
                    .accountNoExpired(true)
                    .accountNoLocked(true)
                    .credentialNoExpired(true)
                    .roles(Set.of(roleAdmin))
                    .build();
            UserEntity userJulia = UserEntity.builder()
                    .username("julia")
                    .password("$2a$10$j125WqnLIzfe3FKb/wYrv.m7nrdT1kuJSBWLxTiuVz8lqUeJWWkA6")
                    .isEnabled(true)
                    .accountNoExpired(true)
                    .accountNoLocked(true)
                    .credentialNoExpired(true)
                    .roles(Set.of(roleUser))
                    .build();
            UserEntity userZoro = UserEntity.builder()
                    .username("zoro")
                    .password("$2a$10$j125WqnLIzfe3FKb/wYrv.m7nrdT1kuJSBWLxTiuVz8lqUeJWWkA6")
                    .isEnabled(true)
                    .accountNoExpired(true)
                    .accountNoLocked(true)
                    .credentialNoExpired(true)
                    .roles(Set.of(roleGuest))
                    .build();
            UserEntity userSanji = UserEntity.builder()
                    .username("sanji")
                    .password("$2a$10$j125WqnLIzfe3FKb/wYrv.m7nrdT1kuJSBWLxTiuVz8lqUeJWWkA6")
                    .isEnabled(true)
                    .accountNoExpired(true)
                    .accountNoLocked(true)
                    .credentialNoExpired(true)
                    .roles(Set.of(roleDeveloper))
                    .build();
            UserEntity userLuffy = UserEntity.builder()
                    .username("luffy")
                    .password("$2a$10$j125WqnLIzfe3FKb/wYrv.m7nrdT1kuJSBWLxTiuVz8lqUeJWW")
                    .isEnabled(true)
                    .accountNoExpired(true)
                    .accountNoLocked(true)
                    .credentialNoExpired(false)
                    .roles(Set.of(roleGuest))
                    .build();

            userRepository.saveAll(List.of(userSanji, userOctavio, userJulia, userZoro, userLuffy));
        };
    }



}
