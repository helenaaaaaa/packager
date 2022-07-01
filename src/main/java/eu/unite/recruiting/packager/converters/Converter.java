package eu.unite.recruiting.packager.converters;

import java.math.BigDecimal;
import javax.annotation.Nonnull;

public interface Converter<U> {

	@Nonnull
	BigDecimal convert(@Nonnull BigDecimal value, @Nonnull U from, @Nonnull U to);

	@Nonnull
	BigDecimal convertToDefaultUnit(@Nonnull BigDecimal value, @Nonnull U from);

	@Nonnull
	U getDefaultUnit();

}
