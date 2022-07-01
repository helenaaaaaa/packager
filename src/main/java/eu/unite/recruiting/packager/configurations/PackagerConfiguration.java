package eu.unite.recruiting.packager.configurations;

import java.util.Collection;
import java.util.List;

import eu.unite.recruiting.packager.converters.Converter;
import eu.unite.recruiting.packager.converters.impl.CurrencyConverterImpl;
import eu.unite.recruiting.packager.converters.impl.WeightConverterImpl;
import eu.unite.recruiting.packager.dtos.ItemDto;
import eu.unite.recruiting.packager.dtos.PackageInDto;
import eu.unite.recruiting.packager.mappers.EntityMapper;
import eu.unite.recruiting.packager.mappers.impl.ItemDataEntityMapperImpl;
import eu.unite.recruiting.packager.models.CurrencyConversionDefinition;
import eu.unite.recruiting.packager.models.CurrencyUnit;
import eu.unite.recruiting.packager.models.ItemData;
import eu.unite.recruiting.packager.models.WeightConversionDefinition;
import eu.unite.recruiting.packager.models.WeightUnit;
import eu.unite.recruiting.packager.services.PackagerService;
import eu.unite.recruiting.packager.services.impl.PackagerServiceImpl;
import eu.unite.recruiting.packager.validators.EntityValidator;
import eu.unite.recruiting.packager.validators.impl.ItemDataEntityValidatorImpl;
import eu.unite.recruiting.packager.validators.impl.PackageInDtoEntityValidatorImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PackagerConfiguration {

	@Bean
	PackagerService packagerService(
			final EntityValidator<Collection<ItemData>> entityValidator,
			final Converter<WeightUnit> weightConverter,
			final Converter<CurrencyUnit> currencyConverter
	) {
		return new PackagerServiceImpl(entityValidator, weightConverter, currencyConverter);
	}

	@Bean
	EntityValidator<List<ItemData>> itemDataEntityValidator() {
		return new ItemDataEntityValidatorImpl();
	}

	@Bean
	EntityValidator<PackageInDto> packageInDtoEntityValidator(
			final Converter<WeightUnit> weightUnitConverter,
			final Converter<CurrencyUnit> currencyUnitConverter
	) {
		return new PackageInDtoEntityValidatorImpl(weightUnitConverter, currencyUnitConverter);
	}

	@Bean
	Converter<WeightUnit> weightConverter() {
		return new WeightConverterImpl(WeightConversionDefinition.getWeightRates(), WeightUnit.KILOGRAM);
	}

	@Bean
	Converter<CurrencyUnit> currencyConverter() {
		return new CurrencyConverterImpl(CurrencyConversionDefinition.getCurrencyRates(), CurrencyUnit.EURO);
	}

	@Bean
	EntityMapper<ItemData, ItemDto> entityMapper() {
		return new ItemDataEntityMapperImpl();
	}

}
