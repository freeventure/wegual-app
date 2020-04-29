package app.wegual.common.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserHomePageData {

	private User user;
	private PledgeAnalyticsForUser counts;
}
