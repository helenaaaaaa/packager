package eu.unite.recruiting.packager.dtos;

import java.util.List;

public class PackageInDto {

	private double maxWeight;

	private String weightUnit;

	private List<ItemDto> items;

	public double getMaxWeight() {
		return maxWeight;
	}

	public void setMaxWeight(final double maxWeight) {
		this.maxWeight = maxWeight;
	}

	public String getWeightUnit() {
		return weightUnit;
	}

	public void setWeightUnit(final String weightUnit) {
		this.weightUnit = weightUnit;
	}

	public List<ItemDto> getItems() {
		return items;
	}

	public void setItems(final List<ItemDto> items) {
		this.items = items;
	}

}
