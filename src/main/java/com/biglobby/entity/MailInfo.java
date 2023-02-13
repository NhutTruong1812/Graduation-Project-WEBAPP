package com.biglobby.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MailInfo {
	String from;
	String to;
//	String[] cc;
//	String[] bcc;
//	String[] attachments;
	String subject;
	String body;
	List<String> cc = new ArrayList<>();
	List<String> bcc = new ArrayList<>();
	List<MultipartFile> attachments = new ArrayList<>();


	public MailInfo(String to, String subject, String body) {
		this.from = "Diễn đàn thương mại điện tử BIGLOBBY <BIGLOBBY@gmail.com>";
		this.to = to;
		this.subject = subject;
		this.body = body;
	}
}
