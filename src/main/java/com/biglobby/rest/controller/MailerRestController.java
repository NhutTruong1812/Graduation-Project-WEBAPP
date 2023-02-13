package com.biglobby.rest.controller;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController; 
 
import com.biglobby.services.MailerService;
import com.biglobby.services.SessionService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/sendmail")
public class MailerRestController {

	@Autowired
	MailerService mailer;
	
	@Autowired
	SessionService session;
	
	@GetMapping(params = {"mailer.to", "mailer.subject"})
	public void queuemailto(@RequestParam("mailer.to") String to, @RequestParam("mailer.subject") String subject){
//		Long idUser = session.get("usersessionstatus");
		Long idRegisterActive = session.get("sessionRegisterActive");
		String body = "Nếu bạn tin tưởng và muốn tham gia vào cộng đồng của chúng tôi hãy nhấn vào nút bên dưới: <a href=\"http://localhost:8080/api/registeractives/active/"+idRegisterActive+"\" style=\"background-color: #5457ff;"
				+ "border-color: #000000;\r\n"
				+ "border-style: solid;\r\n"
				+ "border-width: 1px;\r\n"
				+ "color: #ffffff;\r\n"
				+ "display: inline-block;\r\n"
				+ "font-family: Nunito;\r\n"
				+ "font-weight: 600;\r\n"
				+ "line-height: 20.8px;\r\n"
				+ "padding: 12px 21px;\r\n"
				+ "text-align: center;\r\n"
				+ "border: none;\r\n"
				+ "text-decoration: none;\r\n"
				+ " border-radius: 4px;\">Xác nhận<a>";
		try {
			 mailer.send(to, subject, body);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@GetMapping(params = {"to", "subject", "body"})
	public void queuemailto(@RequestParam("to") String to, @RequestParam("subject") String subject, @RequestParam("body") String body ){
		try {
			 mailer.send(to, subject, body);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
//	@PostMapping("/queue/{to}/{subject}/{body}")
//	public void queuemailto(@PathVariable("to") String to, @PathVariable("subject") String subject, @PathVariable("body") String body ){
//		try {
//			 mailer.send(to, subject, body);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
	@PostMapping("/send/{to}/{subject}/{body}/{img}")
	public void sendemailto(@PathVariable("to") String to, @PathVariable("subject") String subject, @PathVariable("body") String body,@PathVariable("img") String img ){
		try {
			// MailInfo mail = new MailInfo(to, subject, body);
			//MultipartFile attach = new File
			//mail.setAttachments(null)
			 mailer.send(to, subject, body);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	


}
