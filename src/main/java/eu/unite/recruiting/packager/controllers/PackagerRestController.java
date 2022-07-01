package eu.unite.recruiting.packager.controllers;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;

import eu.unite.recruiting.packager.dtos.ItemDto;
import eu.unite.recruiting.packager.dtos.PackageInDto;
import eu.unite.recruiting.packager.dtos.PackageOutDto;
import eu.unite.recruiting.packager.mappers.EntityMapper;
import eu.unite.recruiting.packager.models.ItemData;
import eu.unite.recruiting.packager.models.WeightUnit;
import eu.unite.recruiting.packager.services.PackagerService;
import eu.unite.recruiting.packager.validators.EntityValidator;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1")
@RestController
public class PackagerRestController {

	private final PackagerService packagerService;
	private final EntityValidator<PackageInDto> packageInDtoEntityValidator;
	private final EntityMapper<ItemData, ItemDto> itemDataEntityMapper;

	public PackagerRestController(
			final PackagerService packagerService,
			final EntityValidator<PackageInDto> packageInDtoEntityValidator,
			final EntityMapper<ItemData, ItemDto> itemDataEntityMapper
	) {
		this.packagerService = packagerService;
		this.packageInDtoEntityValidator = packageInDtoEntityValidator;
		this.itemDataEntityMapper = itemDataEntityMapper;
	}

	@PostMapping(value = "/packages/estimates", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<PackageOutDto> getPackageSubset(@Nonnull @RequestBody final PackageInDto packageInDto) {
		packageInDtoEntityValidator.validate(packageInDto);

		final List<ItemData> items = itemDataEntityMapper.to(packageInDto.getItems());
		final WeightUnit weightUnit = WeightUnit.valueOfCode(packageInDto.getWeightUnit());

		final Collection<ItemData> result = packagerService.findMaxPriceSumCombination(items, packageInDto.getMaxWeight(), weightUnit);

		final List<Integer> resultList = result.stream()
				.map(ItemData::getId)
				.collect(Collectors.toList());
		final BigDecimal sum = result.stream()
				.map(ItemData::getPrice)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		final PackageOutDto packageOutDto = new PackageOutDto();
		packageOutDto.setTotalPrice(sum.doubleValue());
		packageOutDto.setIds(resultList);

		return ResponseEntity.ok(packageOutDto);
	}

}