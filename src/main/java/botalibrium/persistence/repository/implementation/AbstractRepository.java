package botalibrium.persistence.repository.implementation;

import java.util.Arrays;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.springframework.beans.factory.annotation.Autowired;

import botalibrium.persistence.repository.contract.Repository;

public class AbstractRepository<T> implements Repository<T> {

    @Autowired
    private Datastore datastore;


    @Override
    public Iterable<Key<T>> save(T... plantMaterial) {
        return datastore.save(Arrays.asList(plantMaterial));
    }

    @Override
    public Key<T> save(T item) {
        return datastore.save(item);
    }

}
