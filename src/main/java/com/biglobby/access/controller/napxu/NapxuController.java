package com.biglobby.access.controller.napxu;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.biglobby.entity.Action;
import com.biglobby.entity.BCoinHistory;
import com.biglobby.entity.MyBCoin;
import com.biglobby.entity.User;
import com.biglobby.repository.BCoinHistoryRepository;
import com.biglobby.repository.MyBCoinRepository;
import com.biglobby.repository.UserRepository;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

@Controller
public class NapxuController {
	@GetMapping("/napxu")
	public String getNapxu(Model m) {
		return "demonangcao/index-napxu";
	}

	@Autowired
	BCoinHistoryRepository daobch;
	@Autowired
	MyBCoinRepository daombc;
	@Autowired
	UserRepository daou;
	
	@PostMapping("/napxu")
	public void postNapxu(Model m, HttpServletRequest req, HttpServletResponse resp) {
		//
		BCoinHistory tempbch = new BCoinHistory();
		tempbch.setUser(daou.getById(Long.valueOf(req.getParameter("nguoinap"))));
		tempbch.setQuantity(Integer.valueOf(req.getParameter("soxu")));
		tempbch.setAction(new Action(1l));
		tempbch.setNote("NAP XU THANH CONG");
		tempbch.setCoinDate(new Timestamp(new Date().getTime()));
		tempbch.setActBy(new User(1l));
		//
		BCoinHistory tempbchSave = daobch.save(tempbch);
		if(tempbchSave != null) {
			Optional<MyBCoin> tempmbc = daombc.findByUserId(Long.valueOf(req.getParameter("nguoinap")));
			if(tempmbc.isPresent()) {
				System.err.println("111");
				MyBCoin tempmbc2 =tempmbc.get();
				tempmbc2.setCoinnum(tempmbc2.getCoinnum()+Integer.valueOf(req.getParameter("soxu")));
				daombc.save(tempmbc2);
			}else {
				MyBCoin tempmbc2 = new MyBCoin(null, daou.getById(Long.valueOf(req.getParameter("nguoinap"))), Integer.valueOf(req.getParameter("soxu")));
				daombc.save(tempmbc2);
				System.err.println("222");
			}
			postVNPAY(req, resp, "dn"+tempbchSave.getId());
		}else {
			
		}
	}

	@GetMapping("/ketqua")
	public String ketquaNapxu(Model m) {
		return "demonangcao/index-ketqua";
	}

	private void postVNPAY(HttpServletRequest req, HttpServletResponse resp, String iddonnap) {
		try {
			// MẶC ĐỊNH - md
			String vnp_Version = "2.1.0";
			String vnp_Command = "pay";
			// md > loại hóa đơn
//					String orderType = req.getParameter("ordertype");
			String orderType = "billpayment";
//					String orderType = "250000";
			// md - hình thức thanh toán: qua ngân hàng NCB
//					String bank_code = req.getParameter("bankcode");
			String bank_code = null;
//					THẺ MẪU
//					Ngân hàng: NCB
//					Số thẻ: 9704198526191432198
//					Tên chủ thẻ: NGUYEN VAN A
//					Ngày phát hành: 07/15
//					Mật khẩu OTP: 123456
			// md - ngôn ngữ hỗ trợ : Vietnam - vi
//					String locate = req.getParameter("language");
			String locate = null;
			// md - ngày tạo
			Date dt = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
			String vnp_CreateDate = formatter.format(dt);
			// md - ngày hết hạn
			Calendar cldvnp_ExpireDate = Calendar.getInstance();
			cldvnp_ExpireDate.add(Calendar.SECOND, 120);
			Date vnp_ExpireDateD = cldvnp_ExpireDate.getTime();
			String vnp_ExpireDate = formatter.format(vnp_ExpireDateD);

			// THAY ĐỔI - td
			// CẤU HÌNH BÊN PAY.CONTROLLER.CONFIG
			// td - Config.vnp_Returnurl: gọi controller để update thông tin,
			// td - Config.vnp_TmnCode: mã đăng kí thanh toán bằng VNPAY: 46NNXWON
			String vnp_TmnCode = Config.vnp_TmnCode;
			// td - Config.vnp_HashSecret: chuỗi bí mật
			//
			// NHẬN TRÊN REQUEST HOẶC SOẠN SẲN
			// td > nội dung thanh toán: không dấu: nhập hoặc sẳn
//			String vnp_OrderInfo = req.getParameter("vnp_OrderInfo");
			String vnp_OrderInfo = "nap xu thanh cong";
			// td > id đơn thanh toán (id_bcoinhistory, id_bservicehistory): lấy từ csdl
//			String vnp_TxnRef = Config.getRandomNumber(8);
//			String vnp_TxnRef = String.valueOf(301);
			// td > địa chỉ IP gửi thanh toán: nhận từ req
			String vnp_IpAddr = Config.getIpAddress(req);
			// td > số tiền thanh toán: nhập hoặc sẳn
//			int amount = Integer.parseInt(req.getParameter("amount")) * 100;
//			int amount = 77700000;
			//
			// NHẬN VÀO TỪ REQ
			String vnp_TxnRef = iddonnap;
			int amount = Integer.valueOf(req.getParameter("soxu")) * 1000 * 100;
			String nguoinap = req.getParameter("nguoinap");
			System.err.println("nguoinap " + nguoinap);

			// KHAI BÁO JSON GỬI VỀ VNPAY
			Map<String, String> vnp_Params = new HashMap<>();
			vnp_Params.put("vnp_Version", vnp_Version);
			vnp_Params.put("vnp_Command", vnp_Command);
			vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
			vnp_Params.put("vnp_Amount", String.valueOf(amount));
			vnp_Params.put("vnp_CurrCode", "VND");
			vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
			vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);
			vnp_Params.put("vnp_OrderType", orderType);
			vnp_Params.put("vnp_Locale", "vn");
			vnp_Params.put("vnp_ReturnUrl", Config.vnp_Returnurl);
			vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
			vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
			vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

			// listtentruong
			List fieldNames = new ArrayList(vnp_Params.keySet());
			Collections.sort(fieldNames);
			StringBuilder hashData = new StringBuilder();
			StringBuilder query = new StringBuilder();
			// tạo ra vòng lặp listtentruong
			Iterator itr = fieldNames.iterator();
			while (itr.hasNext()) {
				String fieldName = (String) itr.next();
				String fieldValue = (String) vnp_Params.get(fieldName);
				if ((fieldValue != null) && (fieldValue.length() > 0)) {
					// Build hash data
					hashData.append(fieldName);
					hashData.append('=');
					// hashData.append(fieldValue); //sá»­ dá»¥ng vÃ  2.0.0 vÃ  2.0.1 checksum
					// sha256
					hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
					// sá»­ dá»¥ng
					// v2.1.0 check
					// sum sha512

					// Build query
					query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
					query.append('=');
					query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
					//
					if (itr.hasNext()) {
						query.append('&');
						hashData.append('&');
					}
				}
			}

			// KHỞI TẠO QUERY STRING
			String queryUrl = query.toString();
			// String vnp_SecureHash = Config.Sha256(Config.vnp_HashSecret +
			// hashData.toString());
			String vnp_SecureHash = Config.hmacSHA512(Config.vnp_HashSecret, hashData.toString());
			queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
			String paymentUrl = Config.vnp_PayUrl + "?" + queryUrl;
			//
			com.google.gson.JsonObject job = new JsonObject();
			job.addProperty("code", "00");
			job.addProperty("message", "success");
			job.addProperty("data", paymentUrl);
			Gson gson = new Gson();
			//
			resp.getWriter().write(gson.toJson(job));
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e);
		}
	}

//	private void postVNPAY_origin(HttpServletRequest req, HttpServletResponse resp) {
//		try {
//			// MẶC ĐỊNH - md
//			String vnp_Version = "2.1.0";
//			String vnp_Command = "pay";
//			// md > loại hóa đơn
////					String orderType = req.getParameter("ordertype");
//			String orderType = "billpayment";
////					String orderType = "250000";
//			// md - hình thức thanh toán: qua ngân hàng NCB
////					String bank_code = req.getParameter("bankcode");
//			String bank_code = null;
////					THẺ MẪU
////					Ngân hàng: NCB
////					Số thẻ: 9704198526191432198
////					Tên chủ thẻ: NGUYEN VAN A
////					Ngày phát hành: 07/15
////					Mật khẩu OTP: 123456
//			// md - ngôn ngữ hỗ trợ : Vietnam - vi
////					String locate = req.getParameter("language");
//			String locate = null;
//			// md - ngày tạo
//			Date dt = new Date();
//			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
//			String vnp_CreateDate = formatter.format(dt);
//			// md - ngày hết hạn
//			Calendar cldvnp_ExpireDate = Calendar.getInstance();
//			cldvnp_ExpireDate.add(Calendar.SECOND, 60);
//			Date vnp_ExpireDateD = cldvnp_ExpireDate.getTime();
//			String vnp_ExpireDate = formatter.format(vnp_ExpireDateD);
//			// md > địa chỉ IP gửi thanh toán: nhận từ req
//			String vnp_IpAddr = Config.getIpAddress(req);
//
//			// THAY ĐỔI - td
//			// CẤU HÌNH BÊN PAY.CONTROLLER.CONFIG
//			// td - Config.vnp_Returnurl: gọi controller để update thông tin,
//			// td - Config.vnp_TmnCode: mã đăng kí thanh toán bằng VNPAY: 46NNXWON
//			String vnp_TmnCode = Config.vnp_TmnCode;
//			// td - Config.vnp_HashSecret: chuỗi bí mật
//			//
//			// NHẬN TRÊN REQUEST HOẶC SOẠN SẲN
//			// td > nội dung thanh toán: không dấu: nhập hoặc sẳn
////			String vnp_OrderInfo = req.getParameter("vnp_OrderInfo");
//			String vnp_OrderInfo = "nap xu thanh cong";
//			// td > id đơn thanh toán (id_bcoinhistory, id_bservicehistory): lấy từ csdl
//			String vnp_TxnRef = Config.getRandomNumber(8);
////			String vnp_TxnRef = String.valueOf(1117);
//			// td > số tiền thanh toán: nhập hoặc sẳn
////			int amount = Integer.parseInt(req.getParameter("amount")) * 100;
//			int amount = 77700000;
//
//			// KHAI BÁO JSON GỬI VỀ VNPAY
//			Map<String, String> vnp_Params = new HashMap<>();
//			vnp_Params.put("vnp_Version", vnp_Version);
//			vnp_Params.put("vnp_Command", vnp_Command);
//			vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
//			vnp_Params.put("vnp_Amount", String.valueOf(amount));
//			vnp_Params.put("vnp_CurrCode", "VND");
//			vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
//			vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);
//			vnp_Params.put("vnp_OrderType", orderType);
//			vnp_Params.put("vnp_Locale", "vn");
//			vnp_Params.put("vnp_ReturnUrl", Config.vnp_Returnurl);
//			vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
//			vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
//			vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);
//
//			// listtentruong
//			List fieldNames = new ArrayList(vnp_Params.keySet());
//			Collections.sort(fieldNames);
//			StringBuilder hashData = new StringBuilder();
//			StringBuilder query = new StringBuilder();
//			// tạo ra vòng lặp listtentruong
//			Iterator itr = fieldNames.iterator();
//			while (itr.hasNext()) {
//				String fieldName = (String) itr.next();
//				String fieldValue = (String) vnp_Params.get(fieldName);
//				if ((fieldValue != null) && (fieldValue.length() > 0)) {
//					// Build hash data
//					hashData.append(fieldName);
//					hashData.append('=');
//					// hashData.append(fieldValue); //sá»­ dá»¥ng vÃ  2.0.0 vÃ  2.0.1 checksum
//					// sha256
//					hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
//					// sá»­ dá»¥ng
//					// v2.1.0 check
//					// sum sha512
//
//					// Build query
//					query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
//					query.append('=');
//					query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
//					//
//					if (itr.hasNext()) {
//						query.append('&');
//						hashData.append('&');
//					}
//				}
//			}
//
//			// KHỞI TẠO QUERY STRING
//			String queryUrl = query.toString();
//			// String vnp_SecureHash = Config.Sha256(Config.vnp_HashSecret +
//			// hashData.toString());
//			String vnp_SecureHash = Config.hmacSHA512(Config.vnp_HashSecret, hashData.toString());
//			queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
//			String paymentUrl = Config.vnp_PayUrl + "?" + queryUrl;
//			//
//			com.google.gson.JsonObject job = new JsonObject();
//			job.addProperty("code", "00");
//			job.addProperty("message", "success");
//			job.addProperty("data", paymentUrl);
//			Gson gson = new Gson();
//			//
//			resp.getWriter().write(gson.toJson(job));
//		} catch (Exception e) {
//			// TODO: handle exception
//			System.err.println(e);
//		}
//	}

}
