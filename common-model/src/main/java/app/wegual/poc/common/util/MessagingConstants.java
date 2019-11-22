package app.wegual.poc.common.util;

public class MessagingConstants {

	private MessagingConstants() {}
	
	public static final String EXCHANGE_NAME = "spring-boot-exchange";
	public static final String ES_CAS_ROUTING_KEY = "es-cas";
	
	public static final String directExchange = "timeline";
	public static final String userRoutingKey = "user";
	public static final String beneficiaryRoutingKey = "beneficiary";
	
	public static final String queueNameUserTimeline = "userTimeline";
    public static final String queueNameBeneficiaryTimeline = "beneficiaryTimeline";
    
}
