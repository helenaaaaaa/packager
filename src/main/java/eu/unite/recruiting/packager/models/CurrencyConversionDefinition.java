package eu.unite.recruiting.packager.models;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public enum CurrencyConversionDefinition {

	EUR_TO_USD(CurrencyUnit.EURO, CurrencyUnit.DOLLAR, BigDecimal.valueOf(1.0451)),
	EUR_TO_GPB(CurrencyUnit.EURO, CurrencyUnit.POUND_STERLING, BigDecimal.valueOf(0.8669)),
	EUR_TO_AUD(CurrencyUnit.EURO, CurrencyUnit.AUSTRALIAN_DOLLAR, BigDecimal.valueOf(1.5380)),
	EUR_TO_CAD(CurrencyUnit.EURO, CurrencyUnit.CANADIAN_DOLLAR, BigDecimal.valueOf(1.3504)),
	EUR_TO_CHF(CurrencyUnit.EURO, CurrencyUnit.SWISS_FRANC, BigDecimal.valueOf(1.0037)),
	EUR_TO_JPY(CurrencyUnit.EURO, CurrencyUnit.JAPANESE_YEN, BigDecimal.valueOf(141.4564)),
	EUR_TO_CNH(CurrencyUnit.EURO, CurrencyUnit.CHINESE_RENMINBI, BigDecimal.valueOf(7.0013)),
	EUR_TO_NZD(CurrencyUnit.EURO, CurrencyUnit.NEW_ZEALAND_DOLLAR, BigDecimal.valueOf(1.6951)),
	EUR_TO_HKD(CurrencyUnit.EURO, CurrencyUnit.HONG_KONG_DOLLAR, BigDecimal.valueOf(8.2006)),

	USD_TO_EUR(CurrencyUnit.DOLLAR, CurrencyUnit.EURO, BigDecimal.valueOf(0.9569)),
	USD_TO_GPB(CurrencyUnit.DOLLAR, CurrencyUnit.POUND_STERLING, BigDecimal.valueOf(0.8295)),
	USD_TO_AUD(CurrencyUnit.DOLLAR, CurrencyUnit.AUSTRALIAN_DOLLAR, BigDecimal.valueOf(1.4717)),
	USD_TO_CAD(CurrencyUnit.DOLLAR, CurrencyUnit.CANADIAN_DOLLAR, BigDecimal.valueOf(1.2922)),
	USD_TO_CHF(CurrencyUnit.DOLLAR, CurrencyUnit.SWISS_FRANC, BigDecimal.valueOf(0.9605)),
	USD_TO_JPY(CurrencyUnit.DOLLAR, CurrencyUnit.JAPANESE_YEN, BigDecimal.valueOf(6.6994)),
	USD_TO_CNH(CurrencyUnit.DOLLAR, CurrencyUnit.CHINESE_RENMINBI, BigDecimal.valueOf(6.6994)),
	USD_TO_NZD(CurrencyUnit.DOLLAR, CurrencyUnit.NEW_ZEALAND_DOLLAR, BigDecimal.valueOf(1.6220)),
	USD_TO_HKD(CurrencyUnit.DOLLAR, CurrencyUnit.HONG_KONG_DOLLAR, BigDecimal.valueOf(7.8470)),

	GPB_TO_EUR(CurrencyUnit.POUND_STERLING, CurrencyUnit.EURO, BigDecimal.valueOf(1.1536)),
	GPB_TO_USD(CurrencyUnit.POUND_STERLING, CurrencyUnit.DOLLAR, BigDecimal.valueOf(1.2056)),
	GPB_TO_AUD(CurrencyUnit.POUND_STERLING, CurrencyUnit.AUSTRALIAN_DOLLAR, BigDecimal.valueOf(1.7743)),
	GPB_TO_CAD(CurrencyUnit.POUND_STERLING, CurrencyUnit.CANADIAN_DOLLAR, BigDecimal.valueOf(1.5578)),
	GPB_TO_CHF(CurrencyUnit.POUND_STERLING, CurrencyUnit.SWISS_FRANC, BigDecimal.valueOf(1.1579)),
	GPB_TO_JPY(CurrencyUnit.POUND_STERLING, CurrencyUnit.JAPANESE_YEN, BigDecimal.valueOf(163.1814)),
	GPB_TO_CHN(CurrencyUnit.POUND_STERLING, CurrencyUnit.CHINESE_RENMINBI, BigDecimal.valueOf(8.0766)),
	GPB_TO_NZD(CurrencyUnit.POUND_STERLING, CurrencyUnit.NEW_ZEALAND_DOLLAR, BigDecimal.valueOf(1.9555)),
	GPB_TO_HKD(CurrencyUnit.POUND_STERLING, CurrencyUnit.HONG_KONG_DOLLAR, BigDecimal.valueOf(9.4601)),
	;

	private static final Map<CurrencyUnit, Map<CurrencyUnit, BigDecimal>> weightRates = Arrays.stream(values())
			.collect(Collectors.groupingBy(value -> value.from,
					Collectors.toMap(value -> value.to, value -> value.rate)));

	private final CurrencyUnit from;
	private final CurrencyUnit to;
	private final BigDecimal rate;

	CurrencyConversionDefinition(final CurrencyUnit from, final CurrencyUnit to, final BigDecimal rate) {
		this.from = from;
		this.to = to;
		this.rate = rate;
	}

	public static Map<CurrencyUnit, Map<CurrencyUnit, BigDecimal>> getCurrencyRates() {
		return Collections.unmodifiableMap(weightRates);
	}

}
