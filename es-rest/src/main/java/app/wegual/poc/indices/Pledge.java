package app.wegual.poc.indices;

import java.sql.Date;
import java.util.Currency;

import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.stereotype.Component;

@Component
//@JsonIgnoreProperties(ignoreUnknown = true)
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class Pledge {
	
	private long id;
	private String user_id;
	private Date pledgedDate;
	private double amount;
	private Currency currency;
	private String beneficiary_id;
	private String giveUp_id;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public Date getPledgedDate() {
		return pledgedDate;
	}
	public void setPledgedDate(Date pledgedDate) {
		this.pledgedDate = pledgedDate;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public Currency getCurrency() {
		return currency;
	}
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}
	public String getBeneficiary_id() {
		return beneficiary_id;
	}
	public void setBeneficiary_id(String beneficiary_id) {
		this.beneficiary_id = beneficiary_id;
	}
	public String getGiveUp_id() {
		return giveUp_id;
	}
	public void setGiveUp_id(String giveUp_id) {
		this.giveUp_id = giveUp_id;
	}
	
	/*public void createUserIndex() {
		
		CreateIndexRequest request = new CreateIndexRequest("pledge");
		request.mapping(
		        "{\n" +
		        "  \"properties\": {\n" +
		        "    \"user_id\": {\n" +
		        "      \"type\": \"keyword\"\n" +
		        "    \"pledgedDate\": {\n" +
		        "      \"type\": \"date\"\n" +
		        "    \"amount\": {\n" +
		        "      \"type\": \"double\"\n" +
		        "    \"currency\": {\n" +
		        "      \"type\": \"keyword\"\n" +
		        "    \"beneficiary_id\": {\n" +
		        "      \"type\": \"keyword\"\n" +
		        "    \"giveUp_id\": {\n" +
		        "      \"type\": \"keyword\"\n" +
		        "    }\n" +
		        "  }\n" +
		        "}", 
		        XContentType.JSON);
	}
*/
}
