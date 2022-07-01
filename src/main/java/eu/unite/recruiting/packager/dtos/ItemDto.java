package eu.unite.recruiting.packager.dtos;

public class ItemDto {

	private int id;

	private double weight;

	private String weightUnit;

	private double price;

	private String currency;

	public int getId() {
		return id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(final double weight) {
		this.weight = weight;
	}

	public String getWeightUnit() {
		return weightUnit;
	}

	public void setWeightUnit(final String weightUnit) {
		this.weightUnit = weightUnit;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(final double price) {
		this.price = price;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(final String currency) {
		this.currency = currency;
	}

}
