package com.gyeongbuk.spring.guarantee.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gyeongbuk.spring.guarantee.dao.MemberAdmDao;

@Service
public class MemberAdmServiceImpl extends RootService implements MemberAdmService {

	@Autowired
	private MemberAdmDao dao;

	@Autowired
	private AligoMsgService aligoMsgService;

	//로그인 페이지 열기
	@Override
	public String loginPage(HttpServletRequest request) {
		String result = "";
		HttpSession session = request.getSession();
		String loginCheck = (String) session.getAttribute("userId");
		
		if( loginCheck == null) {
			result = "admin/common/login";
		} else {
			int userLevel = (int) session.getAttribute("userLevel");
			
			if(userLevel > 9) {
				//최고관리자인 경우
				
				//인증번호 확인 여부
				boolean checkMsg = (boolean) (session.getAttribute("certificationCorrect") != null ? session.getAttribute("certificationCorrect") : false);
				if(checkMsg) {
					result = "redirect:/reserve/adm/memberManage/list";
				} else {
					result = "redirect:/reserve/adm/checkMsg";
				}
			} else {
				//서브관리자인 경우
				result = "redirect:/reserve/adm/branchTimeManage/list";
			}

		}
		return result;
	}
	
	// 로그인 시도
	@Override
	public HashMap<String, Object> loginSubmit(HashMap<String, Object> map, HttpServletRequest request) {

		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(-1);
		HashMap<String, Object> result = new HashMap<String, Object>();

		Map<String, Object> params = map;

		String password = (String) params.get("userPw");

		try {
			password = SHA256.encrypt(password);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		params.put("userPw", password);

		HashMap<String, Object> data = dao.login(params);

		if (data != null && !data.isEmpty()) {
			result.put("result", 1);
			result.put("data", data);

			int memIdx = (int) data.get("mem_idx");
			String userId = (String) data.get("mem_userid");
			String userPhone = (String) data.get("mem_phone");
			String userName = (String) data.get("mem_username");
			String userFirst = String.valueOf( data.get("mem_is_first") );

			int userLevel = Integer.parseInt( String.valueOf( data.get("mem_level") ) );

			if (userLevel > 9) {
				session.setAttribute("certificateCorrect", false);

				session.setAttribute("memIdx", memIdx);
				session.setAttribute("userId", userId);
				session.setAttribute("userPhone", userPhone);
				session.setAttribute("userName", userName);
				session.setAttribute("userLevel", userLevel);
				session.setAttribute("userFirst", userFirst);

				HashMap<String, Object> msgParams = new HashMap<String, Object>();

				String certificationCode = numberGen(6, 1);

				msgParams.put("targetName", userName);
				msgParams.put("targetHp", userPhone);

				String targetMsg = userName + "님. 안녕하세요. 경북신용보증재단입니다.\n"
				+ "로그인 인증번호는 [" + certificationCode + "] 입니다.";
				
				System.out.println(certificationCode);

				msgParams.put("targetMsg", targetMsg);

				aligoMsgService.sendMsg(msgParams);

				HashMap<String, Object> certificationParams = new HashMap<String, Object>();

				certificationParams.put("memIdx", memIdx);
				certificationParams.put("certificationCode", certificationCode);

				dao.updateCertificationCode(certificationParams);

			} else {
				session.setAttribute("certificateCorrect", true);
				String clientIp = getClientIP(request);

				params.put("clientIp", clientIp);

				int checkIp = dao.checkIp(params);

				if (checkIp > 0) {
					session.setAttribute("memIdx", memIdx);
					session.setAttribute("userId", userId);
					session.setAttribute("userPhone", userPhone);
					session.setAttribute("userName", userName);
					session.setAttribute("userLevel", userLevel);
					session.setAttribute("userFirst", userFirst);
				} else {
					result.put("result", 2);
				}

			}

		} else {

			result.put("result", 0);
		}

		return result;

	}

	// 로그아웃
	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response) {
		request.getSession().invalidate();
		request.getSession(true);
		String msg = "로그아웃되었습니다.";
		String targetLink = "/reserve/adm/login";
		makeSubmitAlertMsg(response, msg, targetLink);
	}

	//인증번호 확인
	@Override
	public int certificationCodeCheck(HashMap<String, Object> params, HttpServletRequest request) {

		HttpSession session = request.getSession();
		String submitCode = (String) params.get("certificationCode");
		String memIdx = String.valueOf(session.getAttribute("memIdx"));

		params.put("memIdx", memIdx);
		params.put("certificationCode", submitCode);

		int result = dao.checkCertificationCode(params);

		// 맞을 시
		if (result == 1) {
			session.setAttribute("certificationCorrect", true);
		}

		return result;

	}

	//암호 SHA256으로 변환
	private static class SHA256 {

		public static String encrypt(String text) throws NoSuchAlgorithmException {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(text.getBytes());

			return bytesToHex(md.digest());
		}

		public static String bytesToHex(byte[] bytes) {
			StringBuilder builder = new StringBuilder();
			for (byte b : bytes) {
				builder.append(String.format("%02x", b));
			}
			return builder.toString();
		}

	}

	// 접속IP 확인하기
	public static String getClientIP(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");

		if (ip == null) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null) {
			ip = request.getRemoteAddr();
		}

		return ip;
	}

	/*
	 * 비밀번호 변경
	 */
	@Override
	@Transactional
	public void changePassword(HttpServletRequest request, HttpServletResponse response) {

		HashMap<String, Object> params = formatJmMapRequest(request);

		HttpSession session = request.getSession();

		params.put("memIdx", session.getAttribute("memIdx"));
		params.put("userId", session.getAttribute("userId"));

		String password = (String) params.get("userPw");

		try {
			password = SHA256.encrypt(password);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		params.put("userPw", password);

		int success = dao.changePassword(params);
		
		String msg = "비밀번호 변경에 성공했습니다. 다시 로그인해주세요.";
		String targetLink = "/reserve/adm/login";
		
		if (success == 0) {
			msg = "비밀번호 변경에 실패했습니다. 잠시 후 다시 시도해주세요.";
			targetLink = "/reserve/adm/loginFirst";
		} else {
			request.getSession().invalidate();
			request.getSession(true);
		}

		makeSubmitAlertMsg(response, msg, targetLink);

	}

	/*
	 * ip변경 요청
	 */
	@Override
	@Transactional
	public void inquiryIp(HttpServletRequest request, HttpServletResponse response) {

		HashMap<String, Object> params = formatJmMapRequest(request);

		String password = (String) params.get("userPw");

		try {
			password = SHA256.encrypt(password);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		params.put("userPw", password);

		HashMap<String, Object> inquiryIdInfo = dao.inquiryIpCheckId(params);
		
		String msg = "아이디와 비밀번호를 확인해주세요.";
		String targetLink = "/reserve/adm/inquiryIp";
		
		if (inquiryIdInfo != null && !inquiryIdInfo.isEmpty()) {
			params.put("memIdx", inquiryIdInfo.get("mem_idx"));
			params.put("memUserName", inquiryIdInfo.get("mem_username"));
			params.put("memUserId", inquiryIdInfo.get("mem_userid"));
			params.put("milOriginIp", inquiryIdInfo.get("mem_access_ip"));

			int success = dao.inquiryIp(params);

			if (success > 0) {
				msg = "IP요청에 성공했습니다. 최종 관리자 승인까지 기다려주세요.";
				targetLink = "/reserve/adm/login";
			} else {
				msg = "IP요청에 실패했습니다. 잠시 후 다시 시도해주세요.";
				targetLink = "/reserve/adm/inquiryIp";
			}

		}
		
		makeSubmitAlertMsg(response, msg, targetLink);

	}

	@Override
	public HashMap<String, Object> checkMsgResend(HttpServletRequest request) {
		HashMap<String, Object> result = new HashMap<String, Object>();

		HttpSession session = request.getSession();

		String memIdx = String.valueOf(session.getAttribute("memIdx"));
		String userName = (String) session.getAttribute("userName");
		String userPhone = (String) session.getAttribute("userPhone");

		HashMap<String, Object> msgParams = new HashMap<String, Object>();

		String certificationCode = numberGen(6, 1);

		msgParams.put("targetName", userName);
		msgParams.put("targetHp", userPhone);
		  
		String targetMsg = userName + "님. 안녕하세요. 경북신용보증재단입니다.\n"
		+ "로그인 인증번호는 [" + certificationCode + "] 입니다.";

		msgParams.put("targetMsg", targetMsg);
		 
		aligoMsgService.sendMsg(msgParams);

		HashMap<String, Object> certificationParams = new HashMap<String, Object>();

		certificationParams.put("memIdx", memIdx);
		certificationParams.put("certificationCode", certificationCode);

		dao.updateCertificationCode(certificationParams);

		result.put("result", true);
		
		return result;
	}

}
