package my.first.step.common;

import java.io.Serializable;
import java.util.Date;

/**
 * Cette classe est la symbolisation sous forme d'objet de la table daily_record. L'ORM
 * (Object Relationnal Mapping) est réalisé grâce à la configuration se trouvant
 * dans le fichier DailyRecord.hbm.xml
 */
public class DailyRecord implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 130224046705238482L;

	private Integer recordId;
	private Stock stock;
	private Long volume;
	private Date date;

	public DailyRecord() {

	}

	public DailyRecord(Stock stock, Long volume, Date date) {
		this.stock = stock;
		this.volume = volume;
		this.date = date;
	}

	public Integer getRecordId() {
		return recordId;
	}

	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}

	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

	public Long getVolume() {
		return volume;
	}

	public void setVolume(Long volume) {
		this.volume = volume;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String toString() {
		return recordId + " " + date + " " + volume;
	}
}
