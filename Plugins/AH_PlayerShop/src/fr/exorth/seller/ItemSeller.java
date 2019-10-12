package fr.exorth.seller;

import java.util.UUID;

import fr.exorth.Item.ForSaleItemStack;

public class ItemSeller {
	
	
	private UUID uuid;
	private int totalItemsForSale;
	private int sellsLimit;
	private ForSaleItemStack[] itemsForSale;
	private ForSaleItemStack[] expiratedItems;
	
	public ItemSeller(UUID uuid) {
		
		this.uuid=uuid;
		createSeller(uuid);
		
	}

	private void createSeller(UUID sellerUUID) {
		
		
		
	}
	
	public boolean canSell() {
		
		return sellsLimit - totalItemsForSale >0;
		
	}
	

}
