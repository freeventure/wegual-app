package app.wegual.poc.common.util;

public class MessagingConstants {

	private MessagingConstants() {}
	
	public static final String EXCHANGE_NAME = "spring-boot-exchange";
	public static final String ES_CAS_ROUTING_KEY = "es-cas";
	
	public static final String fanoutExchange = "fanout";
	public static final String directExchange = "direct";
	
	public static final String userRoutingKey = "user";
	public static final String beneficiaryRoutingKey = "beneficiary";
	public static final String giveUpRoutingKey = "giveup";
	public static final String timelineRoutingKey = "timeline";
	public static final String userServiceSchedulerRoutingKey = "userServiceScheduler";
	public static final String loginReminderRoutingKey = "loginReminder";
	
	public static final String queueNameUserTimeline = "userTimeline";
    public static final String queueNameBeneficiaryTimeline = "beneficiaryTimeline";
    public static final String queueNameGiveUpTimeline = "giveUpTimeline";
    public static final String queueNameUserLoginReminderScheduler = "userLoginReminderScheduler";
    public static final String queueNameLoginReminderMailService = "loginReminderMailService";
    public static final String queueNameLoginReminderTimeline = "loginReminderTimeline";
}
