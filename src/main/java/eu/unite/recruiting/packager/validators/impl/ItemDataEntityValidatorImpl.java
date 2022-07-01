package eu.unite.recruiting.packager.validators.impl;

import java.math.BigDecimal;
import java.util.List;

import eu.unite.recruiting.packager.exceptions.DataValidationException;
import eu.unite.recruiting.packager.models.ItemData;
import eu.unite.recruiting.packager.validators.EntityValidator;

public class ItemDataEntityValidatorImpl
		implements EntityValidator<List<ItemData>> {

	private static final int MAX_ITEMS_SIZE = 15;
	private static final BigDecimal MAX_WEIGHT = new BigDecimal(100);
	private static final BigDecimal MAX_PRICE = new BigDecimal(100);

	private static final String ITEMS_SIZE_EXCEEDED_ERROR_MESSAGE = String.format("Items size should be less than %s", MAX_ITEMS_SIZE);
	private static final String ITEM_WEIGHT_EXCEEDED_ERROR_MESSAGE = String.format("Item weight should be less than %s", MAX_WEIGHT);
	private static final String ITEM_PRICE_EXCEEDED_ERROR_MESSAGE = String.format("Item price should be less than %s", MAX_PRICE);

	@Override
	public void validate(final List<ItemData> items) {
		if ( items.size() > MAX_ITEMS_SIZE ) {
			throw new DataValidationException(ITEMS_SIZE_EXCEEDED_ERROR_MESSAGE);
		}

		for ( final ItemData itemData : items ) {
			if ( MAX_WEIGHT.compareTo(itemData.getWeight()) == -1 ) {
				throw new DataValidationException(ITEM_WEIGHT_EXCEEDED_ERROR_MESSAGE);
			}
			if ( MAX_PRICE.compareTo(itemData.getPrice()) == -1 ) {
				throw new DataValidationException(ITEM_PRICE_EXCEEDED_ERROR_MESSAGE);
			}
		}

	}

}
