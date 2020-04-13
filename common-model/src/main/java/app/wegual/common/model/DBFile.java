package app.wegual.common.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "user_files")
public class DBFile {

	@Id
	@GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	
	@Column(nullable = true)
    private String fileName;
	
	@Column(nullable = true)
    private String fileType;
    
    @Lob
    @Column(nullable = false)
    private byte[] fileData;

    public DBFile() {

    }

    public DBFile(String fileName, String fileType, byte[] data) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileData = data;
    }
}
