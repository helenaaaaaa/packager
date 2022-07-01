package eu.unite.recruiting.packager.models;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public enum WeightUnit {

	GIGATONNE("Gt"),
	MEGATONNE("Mt"),
	TONNE("t"),
	KILOGRAM("kg"),
	GRAM("g"),
	MILLIGRAM("mg");

	private static final Map<String, WeightUnit> valuesMap = Arrays.stream(values())
			.collect(Collectors.toMap(WeightUnit::getCode, Function.identity()));

	private final String code;

	WeightUnit(final String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	@Nonnull
	public static WeightUnit valueOfCode(@Nonnull final String code) {
		@Nullable
		final WeightUnit weightUnit = valuesMap.get(code);
		if ( weightUnit == null ) {
			throw new IllegalArgumentException("No weight unit code for " + code);
		}

		return weightUnit;
	}

}
