package com.biglobby.services.impl;

import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.biglobby.services.MailerService;

import com.biglobby.entity.MailInfo;



@Service
public class MailerServiceImpl implements MailerService {
	@Autowired
	JavaMailSender sender;

	@Override
	public void send(MailInfo mail) throws MessagingException {
		// Tạo message
		MimeMessage message = this.sender.createMimeMessage();
		// Sử dụng Helper để thiết lập các thông tin cần thiết cho message
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
		helper.setFrom(mail.getFrom());
		helper.setTo(mail.getTo());
		helper.setSubject(mail.getSubject());
		helper.setText(mail.getBody(), true);
		helper.setReplyTo(mail.getFrom());
		 
		for (String email : mail.getCc()) {
			helper.addCc(email);
		}
		for (String email : mail.getBcc()) {
			helper.addBcc(email);
		}
		for (MultipartFile file : mail.getAttachments()) {
			helper.addAttachment(file.getName(), file);
		}
		 
		// Gửi message đến SMTP server
		sender.send(message);

	}
	List<MailInfo> list = new ArrayList<>();

	@Scheduled(fixedDelay = 5000)
	public void run() {
		while (!list.isEmpty()) {
			MailInfo mail = list.remove(0);
			try {
				this.send(mail);
				System.err.println("OK");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void send(String to, String subject, String body) throws MessagingException {
		this.send(new MailInfo(to, subject, body));
	}

	@Override
	public void queue(MailInfo mail) {
		list.add(mail);
		System.err.println(list.size());
	}

	@Override
	public void queue(String to, String subject, String body) throws MessagingException {
		this.queue(new MailInfo(to, subject, body));
	}
}
