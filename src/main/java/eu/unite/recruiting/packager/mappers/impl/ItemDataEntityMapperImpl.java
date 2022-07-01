package eu.unite.recruiting.packager.mappers.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;

import eu.unite.recruiting.packager.dtos.ItemDto;
import eu.unite.recruiting.packager.mappers.EntityMapper;
import eu.unite.recruiting.packager.models.CurrencyUnit;
import eu.unite.recruiting.packager.models.ItemData;
import eu.unite.recruiting.packager.models.WeightUnit;

public class ItemDataEntityMapperImpl
		implements EntityMapper<ItemData, ItemDto> {

	@Nonnull
	@Override
	public ItemData to(@Nonnull final ItemDto entity) {
		final ItemData itemData = new ItemData();
		itemData.setId(entity.getId());
		itemData.setWeight(BigDecimal.valueOf(entity.getWeight()));
		itemData.setWeightUnit(WeightUnit.valueOfCode(entity.getWeightUnit()));
		itemData.setPrice(BigDecimal.valueOf(entity.getPrice()));
		itemData.setCurrencyUnit(CurrencyUnit.valueOfCode(entity.getCurrency().toUpperCase()));
		return itemData;
	}

	@Nonnull
	@Override
	public List<ItemData> to(@Nonnull final List<ItemDto> entities) {
		return entities.stream()
				.map(this::to)
				.collect(Collectors.toList());
	}

	@Nonnull
	@Override
	public ItemDto from(@Nonnull final ItemData entity) {
		final ItemDto itemDto = new ItemDto();
		itemDto.setId(entity.getId());
		itemDto.setWeight(entity.getWeight().doubleValue());
		itemDto.setWeightUnit(entity.getWeightUnit().getCode());
		itemDto.setPrice(entity.getPrice().doubleValue());
		itemDto.setCurrency(entity.getCurrencyUnit().getCode());
		return itemDto;
	}

	@Nonnull
	@Override
	public List<ItemDto> from(@Nonnull final List<ItemData> entities) {
		return entities.stream()
				.map(this::from)
				.collect(Collectors.toList());
	}

}
