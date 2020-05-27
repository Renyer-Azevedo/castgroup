package br.com.desafiocastgroup.castgroup.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Component
public class AwsSaver {
	
	private AmazonS3 amazonS3;
	
	@Autowired
	public AwsSaver(AmazonS3 amazonS3) {
		this.amazonS3 = amazonS3;
	}

	@Value("${amazon.s3.bucket}")
	private String bucket;

	public String write(Base64Image base64Image) {
			
		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentLength(base64Image.getContentLength());
		objectMetadata.setContentType(base64Image.getContentType());
		
		PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, 
				base64Image.getFileName(), base64Image.getFile(), objectMetadata)
				.withCannedAcl(CannedAccessControlList.PublicRead);
		
		amazonS3.putObject(putObjectRequest);
		
		return amazonS3.getUrl(bucket, base64Image.getFileName()).toString();

	}

}
