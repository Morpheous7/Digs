package com.digs.dig0.repository;

import com.digs.dig0.model.User;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * @author Ike Kennedy
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User>  findByUsername(String username);
    <S extends User> @NotNull S saveAndFlush(S entity);

    <S extends User> @NotNull S save(@NotNull S entity);

    Optional<User> save(Optional<User> user);


    @Modifying
    @Query(value = "UPDATE User u set u.username =:username,"+
            " u.name =:name," + "u.phn =:phn where u.id =:id")
    void update(String username, String name, String phn, Long id);
}
