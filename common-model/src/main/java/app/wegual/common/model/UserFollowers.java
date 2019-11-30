package app.wegual.common.model;
import java.io.Serializable;
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
public class UserFollowers implements Serializable {
		
		@Id
    	@GeneratedValue(strategy=GenerationType.AUTO)
    	private Long id;
	
		@ManyToOne
		@JoinColumn(name="FOLLOWER_ID", nullable=false, insertable=true, updatable=false)
		private User follower;
		
		@ManyToOne
		@JoinColumn(name="FOLLOWEE_ID", nullable=false, insertable=true, updatable=false)
		private User followee;
		
		@CreationTimestamp
		private Date follow_date;

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

		public User getFollowee() {
			return followee;
		}

		public void setFollowee(User followee) {
			this.followee = followee;
		}

		public Date getFollow_date() {
			return follow_date;
		}

		public void setFollow_date(Date follow_date) {
			this.follow_date = follow_date;
		}

		
		

		
		
		
		
}
