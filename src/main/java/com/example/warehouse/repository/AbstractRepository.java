package com.example.warehouse.repository;

import com.example.warehouse.criteria.BaseCriteria;
import com.example.warehouse.entity.BaseEntity;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface AbstractRepository<
        C extends BaseCriteria,
        E extends BaseEntity,
        K extends Serializable> extends BaseRepository {

    default E save(E entity) {
        return null;
    }

    default List<E> findAll(C criteria) {
        return null;
    }

    default List<E> findAll() {
        return null;
    }

    default List<E> findAllNotDeleted(C criteria) {
        return null;
    }

    default E findAllNotDeleted() {
        return null;
    }

    default E findById(K id) {
        return null;
    }

    default E findByIdNotDeleted(K id) {
        return null;
    }

    default void hardDelete(K id) {

    }

    default Long allDataCount() {
        return null;
    }

    default void softDelete(K id) {

    }

}
