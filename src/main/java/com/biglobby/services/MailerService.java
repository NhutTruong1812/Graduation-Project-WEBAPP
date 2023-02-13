package com.biglobby.services;
import javax.mail.MessagingException;

import com.biglobby.entity.MailInfo;

public interface MailerService {
	/**
	 * Gửi email
	 * 
	 * @param mail thông tin email
	 * @return 
	 * @throws MessagingException lỗi gửi email
	 */
	public void send(MailInfo mail) throws MessagingException;

	/**
	 * Gửi email đơn giản
	 * 
	 * @param to      email người nhận
	 * @param subject tiêu đề email
	 * @param body    nội dung email
	 * @throws MessagingException lỗi gửi email
	 */
	public void send(String to, String subject, String body) throws MessagingException;

	/**
	 * Xếp mail vào hàng đợi
	 * 
	 * @param mail thông tin email
	 */
	public void queue(MailInfo mail);

	/**
	 * Tạo MailInfo và xếp vào hàng đợi
	 * 
	 * @param to      email người nhận
	 * @param subject tiêu đề email
	 * @param body    nội dung email
	 */
	public void queue(String to, String subject, String body) throws MessagingException ;
}
