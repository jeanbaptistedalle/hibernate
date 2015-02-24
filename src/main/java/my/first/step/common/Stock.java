package my.first.step.common;

import java.util.HashSet;
import java.util.Set;

/**
 * Cette classe est la symbolisation sous forme d'objet de la table stock. L'ORM
 * (Object Relationnal Mapping) est réalisé grâce à la configuration se trouvant
 * dans le fichier Stock.hbm.xml
 */
public class Stock implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1515372912011034166L;

	private Integer stockId;
	private String stockCode;
	private String stockName;
	private Set<DailyRecord> dailyRecords;

	public Stock() {
		dailyRecords = new HashSet<DailyRecord>();
	}

	public Stock(String stockCode, String stockName, Set<DailyRecord> dailyRecords) {
		this.stockCode = stockCode;
		this.stockName = stockName;
		this.dailyRecords = dailyRecords;
		dailyRecords = new HashSet<DailyRecord>();
	}

	public Integer getStockId() {
		return this.stockId;
	}

	public void setStockId(Integer stockId) {
		this.stockId = stockId;
	}

	public String getStockCode() {
		return this.stockCode;
	}

	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}

	public String getStockName() {
		return this.stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	public Set<DailyRecord> getDailyRecords() {
		return dailyRecords;
	}

	public void setDailyRecords(Set<DailyRecord> dailyRecords) {
		this.dailyRecords = dailyRecords;
	}

	public String toString() {
		return stockId + " " + stockCode + " " + stockName;
	}
}
