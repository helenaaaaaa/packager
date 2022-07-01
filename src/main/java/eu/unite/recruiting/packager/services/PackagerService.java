package eu.unite.recruiting.packager.services;

import java.util.List;
import javax.annotation.Nonnull;

import eu.unite.recruiting.packager.models.ItemData;
import eu.unite.recruiting.packager.models.WeightUnit;

public interface PackagerService {

	List<ItemData> findMaxPriceSumCombination(@Nonnull List<ItemData> items, double weightLimit, @Nonnull WeightUnit weightLimitUnit);

}
