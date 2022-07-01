package eu.unite.recruiting.packager.converters.impl;

import java.math.BigDecimal;
import java.util.Map;
import javax.annotation.Nonnull;

import eu.unite.recruiting.packager.converters.AbstractUnitConverter;
import eu.unite.recruiting.packager.models.CurrencyUnit;

public class CurrencyConverterImpl
		extends AbstractUnitConverter<CurrencyUnit> {

	@SuppressWarnings("unused")
	private final Map<CurrencyUnit, Map<CurrencyUnit, BigDecimal>> currencyUnitRates;
	private final CurrencyUnit defaultCurrencyUnit;

	public CurrencyConverterImpl(
			final Map<CurrencyUnit, Map<CurrencyUnit, BigDecimal>> currencyUnitRates,
			final CurrencyUnit defaultCurrencyUnit
	) {
		super(currencyUnitRates);
		this.currencyUnitRates = currencyUnitRates;
		this.defaultCurrencyUnit = defaultCurrencyUnit;
	}

	@Nonnull
	@Override
	public CurrencyUnit getDefaultUnit() {
		return defaultCurrencyUnit;
	}

}
