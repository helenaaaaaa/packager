package eu.unite.recruiting.packager.converters.impl;

import java.math.BigDecimal;
import java.util.Map;
import javax.annotation.Nonnull;

import eu.unite.recruiting.packager.converters.AbstractUnitConverter;
import eu.unite.recruiting.packager.models.WeightUnit;

public class WeightConverterImpl
		extends AbstractUnitConverter<WeightUnit> {

	@SuppressWarnings("unsed")
	private final Map<WeightUnit, Map<WeightUnit, BigDecimal>> weightUnitRates;
	private final WeightUnit defaultWeightUnit;

	public WeightConverterImpl(
			final Map<WeightUnit, Map<WeightUnit, BigDecimal>> weightUnitRates,
			final WeightUnit defaultWeightUnit
	) {
		super(weightUnitRates);
		this.weightUnitRates = weightUnitRates;
		this.defaultWeightUnit = defaultWeightUnit;
	}

	@Nonnull
	@Override
	public WeightUnit getDefaultUnit() {
		return defaultWeightUnit;
	}

}
