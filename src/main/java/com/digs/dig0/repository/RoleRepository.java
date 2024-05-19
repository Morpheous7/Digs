package com.digs.dig0.repository;

import com.digs.dig0.model.Role;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.security.core.GrantedAuthority;
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
    private Role authorities;

    public RoleRepository(){
        authority = null;
    }

    public RoleRepository(Role authority) {
        this.authorities = authority;
    }


    public List<? extends GrantedAuthority> findByAuthority(String authority) {
        this.authority = authority;
        return (List<? extends GrantedAuthority>) Optional.empty().orElse(this.authority);
    }

    @Override
    public void flush() {
        
    }

    @Override
    public <S extends Role> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Role> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    /**
     * @param entities
     * @deprecated
     */
    @Override
    public void deleteInBatch(Iterable<Role> entities) {
        JpaRepository.super.deleteInBatch(entities);
    }

    @Override
    public void deleteAllInBatch(Iterable<Role> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> Long) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    /**
     * @param aLong
     * @deprecated
     */
    @Override
    public Role getOne(Long aLong) {
        return null;
    }

    /**
     * @param aLong
     * @deprecated
     */
    @Override
    public Role getById(Long aLong) {
        return null;
    }

    @Override
    public Role getReferenceById(Long aLong) {
        return null;
    }


    @Override
    public <S extends Role> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Role> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Role> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Role> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Role> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Role> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Role, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    public <S extends Role> Set<Role> save(Set<Role> entity) {
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
    public <S extends Role> S save(S entity) {
        return null;
    }

    @Override
    public <S extends Role> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Role> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }


    @Override
    public List<Role> findAll() {
        return null;
    }

    @Override
    public List<Role> findAllById(Iterable<Long> longs) {
        return List.of();
    }


    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }



    @Override
    public void delete(Role entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }


    @Override
    public void deleteAll(Iterable<? extends Role> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Role> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Role> findAll(Pageable pageable) {
        return null;
    }

    public String save(String authorities) {
        this.authority =authorities;
        return this.authority;
    }

}
