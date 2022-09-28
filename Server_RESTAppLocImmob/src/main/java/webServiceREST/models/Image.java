package webServiceREST.models;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.awt.image.BufferedImage;

@Entity
@Table(name = "images")
public class Image implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idImage")
	private int idImage;
	
	@Column(name = "name")
	private String name;
	
	@Lob()
	private byte[] image;
	
	@Column(name = "mime_type")
	private String mimeType;
	// getters, setters, hashcode et equals...
	
	public void setImage(BufferedImage bufferedImage) throws IOException {
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    ImageIO.write(bufferedImage, mimeType.split("image/")[1], baos);
	    baos.flush();
	    this.image = baos.toByteArray();
	    baos.close();
	}
	
	public BufferedImage getBufferedImage() throws IOException {
	    return ImageIO.read(new ByteArrayInputStream(image));
	}
	
	transient public static final Map acceptedMimeType = new HashMap();
	static{
	    acceptedMimeType.put("jpg", "image/jpeg");
	    acceptedMimeType.put("gif", "image/gif");
	    acceptedMimeType.put("png", "image/png");
	    acceptedMimeType.put("tif", "image/tiff");
	    acceptedMimeType.put("svg", "image/svg+xml");
	    acceptedMimeType.put("ico", "image/vnd.microsoft.icon");
	}
	
}