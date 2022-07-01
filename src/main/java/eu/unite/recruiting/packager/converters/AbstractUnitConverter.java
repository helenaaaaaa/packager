package eu.unite.recruiting.packager.converters;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Nonnull;

public abstract class AbstractUnitConverter<U>
		implements Converter<U> {

	private final Map<U, Map<U, BigDecimal>> unitsMatrix;

	public AbstractUnitConverter(
			final Map<U, Map<U, BigDecimal>> unitsMatrix
	) {
		this.unitsMatrix = unitsMatrix;
	}

	@Nonnull
	@Override
	public BigDecimal convert(@Nonnull final BigDecimal value, @Nonnull final U from, @Nonnull final U to) {
		if ( to.equals(from) ) {
			return value;
		}

		final Map<U, BigDecimal> conversionMatrix = Optional.ofNullable(unitsMatrix.get(from))
				.orElseThrow(UnsupportedOperationException::new);
		return Optional.ofNullable(conversionMatrix.get(to))
				.orElseThrow(UnsupportedOperationException::new)
				.multiply(value);
	}

	@Nonnull
	@Override
	public BigDecimal convertToDefaultUnit(@Nonnull final BigDecimal value, @Nonnull final U from) {
		final U defaultUnit = getDefaultUnit();
		if ( defaultUnit.equals(from) ) {
			return value;
		}

		return Optional.ofNullable(unitsMatrix.get(from))
				.orElseThrow(UnsupportedOperationException::new)
				.get(defaultUnit)
				.multiply(value);
	}

	@Nonnull
	@Override
	public abstract U getDefaultUnit();

}
