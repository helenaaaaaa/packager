package eu.unite.recruiting.packager.services.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import javax.annotation.Nonnull;

import eu.unite.recruiting.packager.converters.Converter;
import eu.unite.recruiting.packager.exceptions.DataValidationException;
import eu.unite.recruiting.packager.models.CurrencyUnit;
import eu.unite.recruiting.packager.models.ItemData;
import eu.unite.recruiting.packager.models.WeightUnit;
import eu.unite.recruiting.packager.services.PackagerService;
import eu.unite.recruiting.packager.validators.EntityValidator;

public class PackagerServiceImpl
		implements PackagerService {

	private static int MAX_WEIGHT_LIMIT = 100;

	private static final String WEIGHT_LIMIT_EXCEEDED_ERROR_MESSAGE = "Weight limit should be less than %s";

	private final EntityValidator<Collection<ItemData>> entityValidator;
	private final Converter<WeightUnit> weightConverter;
	private final Converter<CurrencyUnit> currencyConverter;

	public PackagerServiceImpl(
			final EntityValidator<Collection<ItemData>> entityValidator,
			final Converter<WeightUnit> weightConverter,
			final Converter<CurrencyUnit> currencyConverter
	) {
		this.entityValidator = entityValidator;
		this.weightConverter = weightConverter;
		this.currencyConverter = currencyConverter;
	}

	@Override
	public List<ItemData> findMaxPriceSumCombination(@Nonnull final List<ItemData> items, final double weightLimit,
			@Nonnull final WeightUnit weightLimitUnit) {
		if ( weightLimit > MAX_WEIGHT_LIMIT ) {
			throw new DataValidationException(String.format(WEIGHT_LIMIT_EXCEEDED_ERROR_MESSAGE, MAX_WEIGHT_LIMIT));
		}

		entityValidator.validate(items);

		for ( final ItemData item : items ) {
			item.setPrice(weightConverter.convertToDefaultUnit(item.getPrice(), item.getWeightUnit()));
			item.setWeightUnit(weightConverter.getDefaultUnit());

			item.setPrice(currencyConverter.convertToDefaultUnit(item.getPrice(), item.getCurrencyUnit()));
			item.setCurrencyUnit(currencyConverter.getDefaultUnit());
		}

		final BigDecimal convertedWeightLimit = weightConverter.convertToDefaultUnit(new BigDecimal(weightLimit), weightLimitUnit);
		return generateAllSubsets(items, convertedWeightLimit, ItemData::getWeight).stream()
				.max(Comparator.comparing(subset -> calculateSum(subset, ItemData::getPrice)))
				.orElse(Collections.emptyList());
	}

	@Nonnull
	private List<List<ItemData>> generateAllSubsets(@Nonnull final List<ItemData> items, @Nonnull final BigDecimal valueLimit,
			@Nonnull final Function<ItemData, BigDecimal> valueMapperFunction) {
		final List<List<ItemData>> allSubsets = new ArrayList<>();
		final int max = 1 << items.size();

		for ( int i = 0; i < max; i++ ) {
			final List<ItemData> subset = new ArrayList<>();
			for ( int j = 0; j < items.size(); j++ ) {
				if ( ((i >> j) & 1) == 1 ) {
					subset.add(items.get(j));
				}
			}

			if ( calculateSum(subset, valueMapperFunction).compareTo(valueLimit) == -1 ) {
				allSubsets.add(subset);
			}
		}

		return allSubsets;
	}

	@Nonnull
	private BigDecimal calculateSum(@Nonnull final List<ItemData> items, @Nonnull final Function<ItemData, BigDecimal> mapperFunction) {
		return items.stream()
				.map(mapperFunction)
				.reduce(BigDecimal.ZERO, BigDecimal::add);

	}

}
