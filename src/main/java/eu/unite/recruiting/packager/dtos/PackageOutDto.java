package eu.unite.recruiting.packager.dtos;

import java.util.List;

public class PackageOutDto {

	private double totalPrice;

	private List<Integer> ids;

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(final double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public List<Integer> getIds() {
		return ids;
	}

	public void setIds(final List<Integer> ids) {
		this.ids = ids;
	}

}
