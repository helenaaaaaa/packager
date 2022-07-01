package eu.unite.recruiting.packager.models;

import java.math.BigDecimal;
import javax.annotation.Nonnull;

public class ItemData {

	private int id;

	private BigDecimal weight;

	@Nonnull
	private WeightUnit weightUnit;

	private BigDecimal price;

	@Nonnull
	private CurrencyUnit currencyUnit;

	public int getId() {
		return id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(final BigDecimal weight) {
		this.weight = weight;
	}

	@Nonnull
	public WeightUnit getWeightUnit() {
		return weightUnit;
	}

	public void setWeightUnit(@Nonnull final WeightUnit weightUnit) {
		this.weightUnit = weightUnit;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(final BigDecimal price) {
		this.price = price;
	}

	@Nonnull
	public CurrencyUnit getCurrencyUnit() {
		return currencyUnit;
	}

	public void setCurrencyUnit(@Nonnull final CurrencyUnit currencyUnit) {
		this.currencyUnit = currencyUnit;
	}

}
