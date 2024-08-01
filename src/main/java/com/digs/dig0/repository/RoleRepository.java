package com.digs.dig0.repository;

import com.digs.dig0.model.Role;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;


/**
 * Copyright to Digs LLC
 * @author Ike Kennedy
 */
@Repository
public class RoleRepository implements JpaRepository<Role, Long> {
    private String authority;

    public RoleRepository(){
        authority = null;
    }

    public RoleRepository(Role authority) {
        this.authority = authority.getAuthority();
    }


    @Override
    public void flush() {
        
    }

    @Override
    public <S extends Role> @NotNull S saveAndFlush(@NotNull S entity) {
        return null;
    }

    @Override
    public <S extends Role> @NotNull List<S> saveAllAndFlush(@NotNull Iterable<S> entities) {
        return null;
    }



    @Override
    public void deleteAllInBatch(@NotNull Iterable<Role> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> Long) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    /**
     * @deprecated
     */
    @Override
    public @NotNull Role getOne(@NotNull Long aLong) {
        return null;
    }

    /**
     * @deprecated
     */
    @Override
    public @NotNull Role getById(@NotNull Long aLong) {
        return null;
    }

    @Override
    public @NotNull Role getReferenceById(@NotNull Long aLong) {
        return null;
    }


    @Override
    public <S extends Role> @NotNull Optional<S> findOne(@NotNull Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Role> @NotNull List<S> findAll(@NotNull Example<S> example) {
        return null;
    }

    @Override
    public <S extends Role> @NotNull List<S> findAll(@NotNull Example<S> example, @NotNull Sort sort) {
        return null;
    }

    @Override
    public <S extends Role> @NotNull Page<S> findAll(@NotNull Example<S> example, @NotNull Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Role> long count(@NotNull Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Role> boolean exists(@NotNull Example<S> example) {
        return false;
    }

    @Override
    public <S extends Role, R> @NotNull R findBy(@NotNull Example<S> example, @NotNull Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    public Set<Role> save(Set<Role> entity) {
        return entity;
    }

    /**
     * Saves a given entity. Use the returned instance for further operations as the save operation might have changed the
     * entity instance completely.
     *
     * @param entity must not be {@literal null}.
     * @return the saved entity; will never be {@literal null}.
     * @throws IllegalArgumentException          in case the given {@literal entity} is {@literal null}.
     *                                           a different value from that found in the persistence store. Also thrown if the entity is assumed to be
     *                                           present but does not exist in the database.
     */
    @Override
    public <S extends Role> @NotNull S save(@NotNull S entity) {
        return null;
    }

    @Override
    public <S extends Role> @NotNull List<S> saveAll(@NotNull Iterable<S> entities) {
        return null;
    }

    @Override
    public @NotNull Optional<Role> findById(@NotNull Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(@NotNull Long aLong) {
        return false;
    }


    @Override
    public @NotNull List<Role> findAll() {
        return null;
    }

    @Override
    public @NotNull List<Role> findAllById(@NotNull Iterable<Long> longs) {
        return List.of();
    }


    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(@NotNull Long aLong) {

    }



    @Override
    public void delete(@NotNull Role entity) {

    }

    @Override
    public void deleteAllById(@NotNull Iterable<? extends Long> longs) {

    }


    @Override
    public void deleteAll(@NotNull Iterable<? extends Role> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public @NotNull List<Role> findAll(@NotNull Sort sort) {
        return null;
    }

    @Override
    public @NotNull Page<Role> findAll(@NotNull Pageable pageable) {
        return null;
    }

    public String save(String authorities) {
        this.authority =authorities;
        return this.authority;
    }

}
