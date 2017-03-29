package botalibrium.persistence.repository.contract;

import org.mongodb.morphia.Key;


public interface Repository<T> {
	Iterable<Key<T>> save(T... items);

	Key<T> save(T item);
}
