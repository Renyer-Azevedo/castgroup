package br.com.desafiocastgroup.castgroup.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class AmazonS3Configuration {
	
	@Value("${amazon.s3.access.key}")
	private String accessKey;
	
	@Value("${amazon.s3.secret.key}")
	private String secretKey;
	
	@Value("${amazon.s3.region}")
	private String region;
	
	@Bean
	public BasicAWSCredentials basicAWSCredentials(){
		return new BasicAWSCredentials(accessKey, secretKey);
	}
	
	@Bean
	public AmazonS3 amazonS3() {
		return AmazonS3ClientBuilder.standard().withRegion(region)
			.withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials())).build();
	}

}
