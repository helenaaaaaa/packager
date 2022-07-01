package eu.unite.recruiting.packager.validators.impl;

import java.math.BigDecimal;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import eu.unite.recruiting.packager.converters.Converter;
import eu.unite.recruiting.packager.dtos.ItemDto;
import eu.unite.recruiting.packager.dtos.PackageInDto;
import eu.unite.recruiting.packager.exceptions.DataValidationException;
import eu.unite.recruiting.packager.models.CurrencyUnit;
import eu.unite.recruiting.packager.models.WeightUnit;
import eu.unite.recruiting.packager.validators.EntityValidator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

public class PackageInDtoEntityValidatorImpl
		implements EntityValidator<PackageInDto> {

	private final Converter<WeightUnit> weightUnitConverter;
	private final Converter<CurrencyUnit> currencyUnitConverter;

	public PackageInDtoEntityValidatorImpl(
			final Converter<WeightUnit> weightUnitConverter,
			final Converter<CurrencyUnit> currencyUnitConverter
	) {
		this.weightUnitConverter = weightUnitConverter;
		this.currencyUnitConverter = currencyUnitConverter;
	}

	private static final int MIN_ITEMS_COUNT = 1;
	private static final int MAX_ITEMS_COUNT = 15;
	private static final BigDecimal MAX_ITEMS_WEIGHT_LIMIT = new BigDecimal(100);
	private static final int MIN_ITEM_ID_VALUE = 1;
	private static final BigDecimal MIN_ITEM_WEIGHT = BigDecimal.ZERO;
	private static final BigDecimal MAX_ITEM_WEIGHT = new BigDecimal(100);
	private static final BigDecimal MIN_ITEM_PRICE = BigDecimal.ZERO;
	private static final BigDecimal MAX_ITEM_PRICE = new BigDecimal(100);

	private static final String EMPTY_DATA_ERROR_MESSAGE = "Entity data can not be null";
	private static final String INVALID_ITEMS_COUNT_ERROR_MESSAGE =
			String.format("Items count should be greater than %s and less than %s", MIN_ITEMS_COUNT, MAX_ITEMS_COUNT);
	private static final String INVALID_ITEM_ID_ERROR_MESSAGE = String.format("Item id should be greater than %s", MIN_ITEM_ID_VALUE);
	private static final String EMPTY_WEIGHT_UNIT_ERROR_MESSAGE = "Weight unit value should be not empty";
	private static final String INVALID_WEIGHT_UNIT_VALUE_ERROR_MESSAGE = "Weight unit is not valid";
	private static final String INVALID_WEIGHT_VALUE_ERROR_MESSAGE = "Weight value should be greater than %s and less than %s (%s)";
	private static final String EMPTY_CURRENCY_UNIT_VALUE_ERROR_MESSAGE = "Currency value should be not empty";
	private static final String INVALID_CURRENCY_UNIT_VALUE_ERROR_MESSAGE = "Currency unit is not valid";
	private static final String INVALID_PRICE_VALUE_ERROR_MESSAGE = "Price value should be greater than %s and less than %s (%s)";

	@Override
	public void validate(final PackageInDto entity) {
		if ( entity == null ) {
			throw new DataValidationException(EMPTY_DATA_ERROR_MESSAGE);
		}

		validateMaxPackageWeight(entity.getMaxWeight(), entity.getWeightUnit());

		if ( CollectionUtils.isEmpty(entity.getItems()) || entity.getItems().size() > MAX_ITEMS_COUNT ) {
			throw new DataValidationException(INVALID_ITEMS_COUNT_ERROR_MESSAGE);
		}

		for ( final ItemDto item : entity.getItems() ) {
			if ( item.getId() < MIN_ITEM_ID_VALUE ) {
				throw new DataValidationException(INVALID_ITEM_ID_ERROR_MESSAGE);
			}
			validateItemWeight(item.getWeight(), item.getWeightUnit());
			validateItemPrice(item.getPrice(), item.getCurrency());
		}
	}

	private void validateMaxPackageWeight(final double weight, @Nullable final String weightUnitAsString) {
		validateWeight(MIN_ITEM_WEIGHT, MAX_ITEMS_WEIGHT_LIMIT, weight, weightUnitAsString);
	}

	private void validateItemWeight(final double weight, @Nullable final String weightUnitAsString) {
		validateWeight(MIN_ITEM_WEIGHT, MAX_ITEM_WEIGHT, weight, weightUnitAsString);
	}

	private void validateItemPrice(final double price, @Nullable final String currencyUnitAsString) {
		validatePrice(MIN_ITEM_PRICE, MAX_ITEM_PRICE, price, currencyUnitAsString);
	}

	private void validateWeight(@Nonnull final BigDecimal minWeight, @Nonnull final BigDecimal maxWeight, final double weight,
			@Nullable final String weightUnitAsString) {
		if ( StringUtils.isBlank(weightUnitAsString) ) {
			throw new DataValidationException(EMPTY_WEIGHT_UNIT_ERROR_MESSAGE);
		}

		final WeightUnit weightUnit;
		try {
			weightUnit = WeightUnit.valueOfCode(weightUnitAsString);
		} catch ( final IllegalArgumentException ignored ) {
			throw new DataValidationException(INVALID_WEIGHT_UNIT_VALUE_ERROR_MESSAGE);
		}

		final BigDecimal convertedWeight = weightUnitConverter.convertToDefaultUnit(BigDecimal.valueOf(weight), weightUnit);
		if ( minWeight.compareTo(convertedWeight) == 1 || maxWeight.compareTo(convertedWeight) == -1 ) {
			final BigDecimal convertedMinWeight = weightUnitConverter.convert(minWeight, weightUnitConverter.getDefaultUnit(), weightUnit);
			final BigDecimal convertedMaxWeight = weightUnitConverter.convert(maxWeight, weightUnitConverter.getDefaultUnit(), weightUnit);
			throw new DataValidationException(String.format(INVALID_WEIGHT_VALUE_ERROR_MESSAGE,
					convertedMinWeight.doubleValue(), convertedMaxWeight.doubleValue(), weightUnit.getCode()));
		}
	}

	private void validatePrice(@Nonnull final BigDecimal minPrice, @Nonnull BigDecimal maxPrice, final double price,
			@Nullable final String currencyUnitAsString) {
		if ( StringUtils.isBlank(currencyUnitAsString) ) {
			throw new DataValidationException(EMPTY_CURRENCY_UNIT_VALUE_ERROR_MESSAGE);
		}

		final CurrencyUnit currencyUnit;
		try {
			currencyUnit = CurrencyUnit.valueOfCode(currencyUnitAsString.toUpperCase());
		} catch ( final IllegalArgumentException ignored ) {
			throw new DataValidationException(INVALID_CURRENCY_UNIT_VALUE_ERROR_MESSAGE);
		}

		final BigDecimal convertedWeight = currencyUnitConverter.convertToDefaultUnit(BigDecimal.valueOf(price), currencyUnit);
		if ( minPrice.compareTo(convertedWeight) == 1 || maxPrice.compareTo(convertedWeight) == -1 ) {
			final BigDecimal convertedMinPrice = currencyUnitConverter.convert(minPrice, currencyUnitConverter.getDefaultUnit(), currencyUnit);
			final BigDecimal convertedMaxPrice = currencyUnitConverter.convert(maxPrice, currencyUnitConverter.getDefaultUnit(), currencyUnit);
			throw new DataValidationException(String.format(INVALID_PRICE_VALUE_ERROR_MESSAGE,
					convertedMinPrice.doubleValue(), convertedMaxPrice.doubleValue(), currencyUnit.getCode()));
		}
	}

}
