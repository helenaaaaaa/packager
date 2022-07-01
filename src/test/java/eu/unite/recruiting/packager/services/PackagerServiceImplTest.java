package eu.unite.recruiting.packager.services;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;

import eu.unite.recruiting.packager.converters.Converter;
import eu.unite.recruiting.packager.models.CurrencyUnit;
import eu.unite.recruiting.packager.models.ItemData;
import eu.unite.recruiting.packager.models.WeightUnit;
import eu.unite.recruiting.packager.services.impl.PackagerServiceImpl;
import eu.unite.recruiting.packager.validators.EntityValidator;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class PackagerServiceImplTest {

	private static final double WEIGHT_LIMIT_1 = 74.57;
	private static final double WEIGHT_LIMIT_2 = 80;
	private static final double WEIGHT_LIMIT_3 = 8;

	private PackagerServiceImpl packagerServiceImpl;

	@Mock
	private EntityValidator<Collection<ItemData>> entityValidator;

	@Mock
	private Converter<WeightUnit> weightConverter;

	@Mock
	private Converter<CurrencyUnit> currencyConverter;

	@BeforeMethod
	public void before() {
		MockitoAnnotations.openMocks(this);
		packagerServiceImpl = new PackagerServiceImpl(entityValidator, weightConverter, currencyConverter);
	}

	@Test(dataProvider = "findMaxPriceSumCombinationData")
	public void testFindMaxPriceSumCombination(final List<ItemData> items, final double weightLimit, final WeightUnit weightLimitUnit,
			final List<ItemData> expectedItems) {
		Mockito.when(weightConverter.convertToDefaultUnit(ArgumentMatchers.refEq(new BigDecimal(weightLimit)), weightLimitUnit))
				.thenReturn(new BigDecimal(weightLimit));

		final List<ItemData> actualResult = packagerServiceImpl.findMaxPriceSumCombination(items, weightLimit, weightLimitUnit);

		final List<Integer> expectedIds = expectedItems.stream()
				.map(ItemData::getId)
				.collect(Collectors.toList());
		final List<Integer> actualIds = actualResult.stream()
				.map(ItemData::getId)
				.collect(Collectors.toList());
		AssertJUnit.assertEquals(expectedItems.size(), actualResult.size());
		AssertJUnit.assertTrue(expectedIds.containsAll(actualIds) && actualIds.containsAll(expectedIds));

		Mockito.verify(entityValidator).validate(items);
		Mockito.verify(weightConverter, Mockito.times(items.size())).convertToDefaultUnit(ArgumentMatchers.any(BigDecimal.class), ArgumentMatchers.any(WeightUnit.class));
		Mockito.verify(weightConverter, Mockito.times(items.size())).getDefaultUnit();
		Mockito.verify(currencyConverter, Mockito.times(items.size())).convertToDefaultUnit(ArgumentMatchers.any(BigDecimal.class), ArgumentMatchers.any(CurrencyUnit.class));
		Mockito.verify(currencyConverter, Mockito.times(items.size())).getDefaultUnit();
		Mockito.verify(weightConverter).convertToDefaultUnit(ArgumentMatchers.refEq(new BigDecimal(weightLimit)), weightLimitUnit);
		Mockito.verifyNoMoreInteractions(entityValidator, weightConverter, currencyConverter);
	}

	@DataProvider
	private Object[][] findMaxPriceSumCombinationData() {
		return new Object[][]{
				{ null, WEIGHT_LIMIT_1, WeightUnit.KILOGRAM, Collections.emptyList() },
				{ Collections.emptyList(), WEIGHT_LIMIT_1, WeightUnit.KILOGRAM, Collections.emptyList() },
				{ generateGivenList1(), WEIGHT_LIMIT_1, WeightUnit.KILOGRAM, generateExpectedList1() },
				{ generateGivenList2(), WEIGHT_LIMIT_2, WeightUnit.KILOGRAM, generateExpectedList2() },
				{ generateGivenList3(), WEIGHT_LIMIT_3, WeightUnit.KILOGRAM, Collections.emptyList() },
		};
	}

	@Nonnull
	private List<ItemData> generateGivenList1() {
		final ItemData itemData1 = new ItemData();
		itemData1.setId(1);
		itemData1.setWeight(BigDecimal.valueOf(85.31));
		itemData1.setWeightUnit(WeightUnit.KILOGRAM);
		itemData1.setPrice(new BigDecimal(29));
		itemData1.setCurrencyUnit(CurrencyUnit.EURO);

		final ItemData itemData2 = new ItemData();
		itemData2.setId(2);
		itemData2.setWeight(BigDecimal.valueOf(14.55));
		itemData2.setWeightUnit(WeightUnit.KILOGRAM);
		itemData2.setPrice(new BigDecimal(74));
		itemData2.setCurrencyUnit(CurrencyUnit.EURO);

		final ItemData itemData3 = new ItemData();
		itemData3.setId(3);
		itemData3.setWeight(BigDecimal.valueOf(3.98));
		itemData3.setWeightUnit(WeightUnit.KILOGRAM);
		itemData3.setPrice(new BigDecimal(16));
		itemData3.setCurrencyUnit(CurrencyUnit.EURO);

		final ItemData itemData4 = new ItemData();
		itemData4.setId(4);
		itemData4.setWeight(BigDecimal.valueOf(26.24));
		itemData4.setWeightUnit(WeightUnit.KILOGRAM);
		itemData4.setPrice(new BigDecimal(55));
		itemData4.setCurrencyUnit(CurrencyUnit.EURO);

		final ItemData itemData5 = new ItemData();
		itemData5.setId(5);
		itemData5.setWeight(BigDecimal.valueOf(63.69));
		itemData5.setWeightUnit(WeightUnit.KILOGRAM);
		itemData5.setPrice(new BigDecimal(52));
		itemData5.setCurrencyUnit(CurrencyUnit.EURO);

		final ItemData itemData6 = new ItemData();
		itemData6.setId(6);
		itemData6.setWeight(BigDecimal.valueOf(76.25));
		itemData6.setWeightUnit(WeightUnit.KILOGRAM);
		itemData6.setPrice(new BigDecimal(75));
		itemData6.setCurrencyUnit(CurrencyUnit.EURO);

		final ItemData itemData7 = new ItemData();
		itemData7.setId(7);
		itemData7.setWeight(BigDecimal.valueOf(60.02));
		itemData7.setWeightUnit(WeightUnit.KILOGRAM);
		itemData7.setPrice(new BigDecimal(74));
		itemData7.setCurrencyUnit(CurrencyUnit.EURO);

		final ItemData itemData8 = new ItemData();
		itemData8.setId(8);
		itemData8.setWeight(BigDecimal.valueOf(93.18));
		itemData8.setWeightUnit(WeightUnit.KILOGRAM);
		itemData8.setPrice(new BigDecimal(35));
		itemData8.setCurrencyUnit(CurrencyUnit.EURO);

		final ItemData itemData9 = new ItemData();
		itemData9.setId(9);
		itemData9.setWeight(BigDecimal.valueOf(89.95));
		itemData9.setWeightUnit(WeightUnit.KILOGRAM);
		itemData9.setPrice(new BigDecimal(78));
		itemData9.setCurrencyUnit(CurrencyUnit.EURO);

		return List.of(itemData1, itemData2, itemData3, itemData4, itemData5, itemData6, itemData7, itemData8);
	}

	@Nonnull
	private List<ItemData> generateGivenList2() {
		final ItemData itemData1 = new ItemData();
		itemData1.setId(1);
		itemData1.setWeight(BigDecimal.valueOf(53.38));
		itemData1.setWeightUnit(WeightUnit.KILOGRAM);
		itemData1.setPrice(new BigDecimal(45));
		itemData1.setCurrencyUnit(CurrencyUnit.EURO);

		final ItemData itemData2 = new ItemData();
		itemData2.setId(2);
		itemData2.setWeight(BigDecimal.valueOf(88.62));
		itemData2.setWeightUnit(WeightUnit.KILOGRAM);
		itemData2.setPrice(new BigDecimal(98));
		itemData2.setCurrencyUnit(CurrencyUnit.EURO);

		final ItemData itemData3 = new ItemData();
		itemData3.setId(3);
		itemData3.setWeight(BigDecimal.valueOf(78.48));
		itemData3.setWeightUnit(WeightUnit.KILOGRAM);
		itemData3.setPrice(new BigDecimal(3));
		itemData3.setCurrencyUnit(CurrencyUnit.EURO);

		final ItemData itemData4 = new ItemData();
		itemData4.setId(4);
		itemData4.setWeight(BigDecimal.valueOf(72.30));
		itemData4.setWeightUnit(WeightUnit.KILOGRAM);
		itemData4.setPrice(new BigDecimal(76));
		itemData4.setCurrencyUnit(CurrencyUnit.EURO);

		final ItemData itemData5 = new ItemData();
		itemData5.setId(5);
		itemData5.setWeight(BigDecimal.valueOf(30.18));
		itemData5.setWeightUnit(WeightUnit.KILOGRAM);
		itemData5.setPrice(new BigDecimal(9));
		itemData5.setCurrencyUnit(CurrencyUnit.EURO);

		final ItemData itemData6 = new ItemData();
		itemData6.setId(6);
		itemData6.setWeight(BigDecimal.valueOf(46.34));
		itemData6.setWeightUnit(WeightUnit.KILOGRAM);
		itemData6.setPrice(new BigDecimal(48));
		itemData6.setCurrencyUnit(CurrencyUnit.EURO);

		return List.of(itemData1, itemData2, itemData3, itemData4, itemData5, itemData6);
	}

	@Nonnull
	private List<ItemData> generateGivenList3() {
		final ItemData itemData = new ItemData();
		itemData.setId(6);
		itemData.setWeight(BigDecimal.valueOf(15.3));
		itemData.setWeightUnit(WeightUnit.KILOGRAM);
		itemData.setPrice(new BigDecimal(34));
		itemData.setCurrencyUnit(CurrencyUnit.EURO);
		return List.of(itemData);
	}

	@Nonnull
	private List<ItemData> generateExpectedList1() {
		final ItemData itemData2 = new ItemData();
		itemData2.setId(2);
		itemData2.setWeight(BigDecimal.valueOf(14.55));
		itemData2.setWeightUnit(WeightUnit.KILOGRAM);
		itemData2.setPrice(new BigDecimal(74));
		itemData2.setCurrencyUnit(CurrencyUnit.EURO);

		final ItemData itemData7 = new ItemData();
		itemData7.setId(7);
		itemData7.setWeight(BigDecimal.valueOf(60.02));
		itemData7.setWeightUnit(WeightUnit.KILOGRAM);
		itemData7.setPrice(new BigDecimal(74));
		itemData7.setCurrencyUnit(CurrencyUnit.EURO);
		return List.of(itemData2, itemData7);
	}

	@Nonnull
	private List<ItemData> generateExpectedList2() {
		final ItemData itemData4 = new ItemData();
		itemData4.setId(4);
		itemData4.setWeight(BigDecimal.valueOf(72.30));
		itemData4.setWeightUnit(WeightUnit.KILOGRAM);
		itemData4.setPrice(new BigDecimal(76));
		itemData4.setCurrencyUnit(CurrencyUnit.EURO);

		return List.of(itemData4);
	}

}
