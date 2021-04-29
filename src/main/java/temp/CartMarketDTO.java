package temp;

public class CartMarketDTO {

	private long marketId;
	private String marketName;
	private String checkMarket;
	
	public CartMarketDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CartMarketDTO(long marketId, String marketName, String checkMarket) {
		super();
		this.marketId = marketId;
		this.marketName = marketName;
		this.checkMarket = checkMarket;
	}

	public long getMarketId() {
		return marketId;
	}

	public void setMarketId(long marketId) {
		this.marketId = marketId;
	}

	public String getMarketName() {
		return marketName;
	}

	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}

	public String getCheckMarket() {
		return checkMarket;
	}

	public void setCheckMarket(String checkMarket) {
		this.checkMarket = checkMarket;
	}
	
	
}
