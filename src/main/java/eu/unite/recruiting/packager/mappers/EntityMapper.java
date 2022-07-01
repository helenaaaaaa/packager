package eu.unite.recruiting.packager.mappers;

import java.util.List;
import javax.annotation.Nonnull;

public interface EntityMapper<E, T> {

	@Nonnull
	E to(@Nonnull T entity);

	@Nonnull
	List<E> to(@Nonnull List<T> entities);

	@Nonnull
	T from(@Nonnull E entity);

	@Nonnull
	List<T> from(@Nonnull List<E> entities);

}
