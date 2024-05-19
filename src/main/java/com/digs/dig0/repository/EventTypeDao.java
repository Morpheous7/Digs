package com.digs.dig0.repository;


import com.digs.dig0.model.Event_type;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;


/**
 * Copyright to Digs LLC
 * @author Ike Kennedy
 */
@Repository
public class EventTypeDao implements JpaRepository<Event_type, Long> {
    @Override
    public void flush() {

    }

    @Override
    public <S extends Event_type> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Event_type> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(Iterable<Event_type> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    /**
     * @param aLong
     * @deprecated
     */
    @Override
    public Event_type getOne(Long aLong) {
        return null;
    }

    /**
     * @param aLong
     * @deprecated
     */
    @Override
    public Event_type getById(Long aLong) {
        return null;
    }

    @Override
    public Event_type getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends Event_type> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Event_type> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends Event_type> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends Event_type> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Event_type> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Event_type> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Event_type, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends Event_type> S save(S entity) {
        return null;
    }

    @Override
    public <S extends Event_type> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public Optional<Event_type> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public List<Event_type> findAll() {
        return List.of();
    }

    @Override
    public List<Event_type> findAllById(Iterable<Long> longs) {
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
    public void delete(Event_type entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Event_type> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Event_type> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<Event_type> findAll(Pageable pageable) {
        return null;
    }
}
