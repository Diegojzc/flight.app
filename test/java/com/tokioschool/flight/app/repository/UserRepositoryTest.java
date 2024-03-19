package com.tokioschool.flight.app.repository;

import com.tokioschool.flight.app.domain.Role;
import com.tokioschool.flight.app.domain.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(
        properties = {
                "spring.datasource.url=jdbc:h2:mem:testdb;MODE=MYSQL;DATABASE_TO_LOWER=TRUE;",
                "spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect",
                "spring.jpa.hibernate.ddl-auto:create-drop"
        })
@Transactional(propagation = Propagation.NOT_SUPPORTED)
class UserRepositoryTest {

        @Autowired
        private UserRepository userDao;
        @Autowired
        private RoleRepository roleDAO;
        @Autowired
        private TransactionTemplate transactionTemplate;

        @BeforeEach
        void beforeEach(){

                Role role1 = Role.builder().name("role1").build();
                Role role2= Role.builder().name("role2").build();

                roleDAO.saveAll(List.of(role1,role2));
                User user1= User.builder()
                        .email("user1@gmail.com")
                        .roles(new HashSet<>(Set.of(role1,role2)))
                        .password("password")
                        .name("name1")
                        .surname("surname1")
                        .build();

                User user2 = User.builder()
                        .email("user2@gmail.com")
                        .roles(new HashSet<>())
                        .password("password")
                        .name("name2")
                        .surname("surname2")
                        .build();

                userDao.saveAll(List.of(user1,user2));

        }

        @AfterEach
        void afterEach(){
                userDao.deleteAll();;
                roleDAO.deleteAll();
        }

        @Test
        void givenTwoUsers_whenFindAll_thenReturnOk(){
                transactionTemplate.executeWithoutResult(
                        transactionStatus ->{
                        List<User> users= userDao.findAll();
                        assertThat(users).hasSize(2);

                        assertThat(
                                users.stream()
                                        .filter(user-> user.getEmail().equals("user1@gmail.com"))
                                        .findFirst()
                                        .get())
                                .returns("name1",User::getName)
                                .satisfies(user-> assertThat(user.getCreated()).isNotNull())
                                .satisfies(user-> assertThat(user.getId()).isNotNull())
                                .satisfies(user-> assertThat(user.getRoles().stream().map(Role:: getName).toList())
                                        .containsExactlyInAnyOrder("role1","role2"));
                });


        }

        @Test
        void givenUser_whenDeleted_thenManyToManyIsDeleted(){
                User user = userDao.findByEmail("user1@gmail.com").get();
                userDao.delete(user);

                List<User> users = userDao.findAll();
                assertThat(users).hasSize(1);
        }



}
