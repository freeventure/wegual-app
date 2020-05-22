package app.wegual.common.util;

public class MessagingConstants {

	private MessagingConstants() {}
	
	public static final String EXCHANGE_NAME = "spring-boot-exchange";
	public static final String BENEFICIARY_FANOUT_EXCHANGE_NAME = "beneficiary-fanout";
	public static final String USERACTIONS_FANOUT_EXCHANGE_NAME = "user-fanout";
	public static final String GIVEUPACTIONS_FANOUT_EXCHANGE_NAME = "giveup-fanout";
	public static final String MAILSERVICE_EXCHANGE_NAME = "mail-services";
	public static final String ES_CAS_ROUTING_KEY = "es-cas";
	public static final String EMAIL_VERIFY_ROUTING_KEY = "mail-verification";
	
	
	public static final String USER_FOLLOWING_QUEUE_NAME = "user-followings";
	public static final String USER_TIMELINE_QUEUE_NAME = "user-timeline";
	public static final String USER_ACTION_QUEUE_NAME = "user-actions";
	public static final String GIVEUP_ACTION_QUEUE_NAME = "giveup-actions";
	public static final String BENEFICIARY_ACTION_QUEUE_NAME = "beneficiary-actions";
}
