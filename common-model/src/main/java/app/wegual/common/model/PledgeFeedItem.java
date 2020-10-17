package app.wegual.common.model;


public class PledgeFeedItem extends FeedItem<String>{
	private static final long serialVersionUID = 1L;
	
	public PledgeFeedItem() {
		super();
	}

	public PledgeFeedItem(GenericItem<String> actor, String detail, FeedItemDetailActions detailActions,
		ActionTarget<String> actionObject) {
		super(actor, detail, detailActions, actionObject);
	}

	
}
