package app.wegual.poc.common.model;

import java.io.Serializable;
import java.sql.Timestamp;
//import java.security.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;

@Entity
public class GiveUpFollower implements Serializable{
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

	@ManyToOne
    @JoinColumn(name="FOLLOWER_ID", nullable=false, insertable=true, updatable=false)
	private User follower;
	
	@ManyToOne
    @JoinColumn(name="FOLLOWEE_ID", nullable=false, insertable=true, updatable=false)
	private GiveUp giveup;
	
	@CreationTimestamp
	private Timestamp follow_date;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getFollower() {
		return follower;
	}

	public void setFollower(User follower) {
		this.follower = follower;
	}

	public GiveUp getGiveup() {
		return giveup;
	}

	public void setGiveup(GiveUp giveup) {
		this.giveup = giveup;
	}

	public Timestamp getFollow_date() {
		return follow_date;
	}

	public void setFollow_date(Timestamp follow_date) {
		this.follow_date = follow_date;
	}

}
