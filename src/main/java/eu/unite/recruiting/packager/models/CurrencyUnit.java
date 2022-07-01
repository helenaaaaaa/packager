package eu.unite.recruiting.packager.models;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;

public enum CurrencyUnit {

	EURO("Euro", "EU", "EUR"),
	DOLLAR("Dollar", "USA", "USD"),
	POUND_STERLING("Pound Sterling", "UK", "GBP"),
	AUSTRALIAN_DOLLAR("Australian Dollar", "AU", "AUD"),
	CANADIAN_DOLLAR("Canadian Dollar", "CA", "CAD"),
	SWISS_FRANC("Swiss Franc", "", "CHF"),
	JAPANESE_YEN("Japanese Yen", "", "JPY"),
	CHINESE_RENMINBI("Chinese renminbi", "CN", "CNH"),
	NEW_ZEALAND_DOLLAR("New Zealand Dollar", "NZ", "NZD"),
	HONG_KONG_DOLLAR("Hong Kong Dollar", "HK", "HKD");

	private static final Map<String, CurrencyUnit> valuesMap = Arrays.stream(values())
			.collect(Collectors.toMap(CurrencyUnit::getCode, Function.identity()));

	private final String name;

	private final String country;

	private final String code;

	CurrencyUnit(final String name, final String country, final String code) {
		this.name = name;
		this.country = country;
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public String getCountry() {
		return country;
	}

	public String getCode() {
		return code;
	}

	@Nonnull
	public static CurrencyUnit valueOfCode(@Nonnull final String code) {
		@Nonnull
		final CurrencyUnit currencyUnit = valuesMap.get(code);
		if ( currencyUnit == null ) {
			throw new IllegalArgumentException("No currency unit code for " + code);
		}

		return currencyUnit;
	}

}
