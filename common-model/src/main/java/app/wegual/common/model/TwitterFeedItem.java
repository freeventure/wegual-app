package app.wegual.common.model;

public class TwitterFeedItem extends FeedItem<String>{
	
	private static final long serialVersionUID = 6942607962707775550L;

	public TwitterFeedItem() {
		super();
	}

	public TwitterFeedItem(GenericItem<String> actor, String detail, FeedItemDetailActions detailActions,
		ActionTarget<String> actionObject) {
		super(actor, detail, detailActions, actionObject);
	}

}
