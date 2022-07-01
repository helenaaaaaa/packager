package eu.unite.recruiting.packager.models;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public enum WeightConversionDefinition {

	GT_TO_MT(WeightUnit.GIGATONNE, WeightUnit.MEGATONNE, new BigDecimal(1000)),
	GT_TO_T(WeightUnit.GIGATONNE, WeightUnit.TONNE, new BigDecimal(1000000000)),
	GT_TO_KG(WeightUnit.GIGATONNE, WeightUnit.KILOGRAM, new BigDecimal(1000000000000L)),
	GT_TO_G(WeightUnit.GIGATONNE, WeightUnit.GRAM, new BigDecimal(1000000000000000L)),
	GT_TO_MG(WeightUnit.GIGATONNE, WeightUnit.MILLIGRAM, new BigDecimal(1000000000000000000L)),
	;

	private static final Map<WeightUnit, Map<WeightUnit, BigDecimal>> weightRates = Arrays.stream(values())
			.collect(Collectors.groupingBy(value -> value.from,
					Collectors.toMap(value -> value.to, value -> value.rate)));

	private final WeightUnit from;
	private final WeightUnit to;
	private final BigDecimal rate;

	WeightConversionDefinition(final WeightUnit from, final WeightUnit to, final BigDecimal rate) {
		this.from = from;
		this.to = to;
		this.rate = rate;
	}

	public static Map<WeightUnit, Map<WeightUnit, BigDecimal>> getWeightRates() {
		return Collections.unmodifiableMap(weightRates);
	}

}
