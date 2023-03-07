package com.gyeongbuk.spring.guarantee.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.gyeongbuk.spring.guarantee.dao.MyInfoDao;
import com.gyeongbuk.spring.guarantee.dao.ReserveManageListDao;
import com.gyeongbuk.spring.guarantee.dto.ReserveDto;

@Service
public class MyInfoServiceImpl extends RootService implements MyInfoService {
	
	@Autowired
	private MyInfoDao dao;
	
	@Autowired
	private ReserveManageListAdmService reserveManageListAdminService;
	
	@Override
	public String listPage(HttpServletRequest request, Model model) {
		
		HttpSession session = request.getSession();
		
		String rlName = (String) (session.getAttribute("rlName") != null ? session.getAttribute("rlName") : "정민");
		String rlHp = (String) (session.getAttribute("rlHp") != null ? session.getAttribute("rlHp") : "01075310206");
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("rlName", rlName);
		params.put("rlHp", rlHp);
		
		List<ReserveDto> result = dao.selectMyReserveList(params);
		
		model.addAttribute("reserveList", result);
		
		return "client/myInfo/list";
		
	}
	
	@Override
	@Transactional
	public HashMap<String, Object> deleteReserve( HashMap<String, ArrayList> map ) {

		int data = dao.deleteReserve(map);
		
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		if (data > 0) {
			
			result.put("result", true);
			
			ArrayList deleteFullReserveList = map.get("rl_reserve_info_array");
			
			int i = 0;
			while(i < deleteFullReserveList.size()) {		
				String[] target = ((String) deleteFullReserveList.get(i)).split(",");
				HashMap<String, Object> targetFullReserve = new HashMap<String, Object>();
				targetFullReserve.put("brIdx", target[0]);
				targetFullReserve.put("selectDate", target[1]);
				
				reserveManageListAdminService.removeFullReserve(targetFullReserve);
				
				
				i++;
			}
			
		} else {
						
			result.put("result", false);
		}
		
		return result;
		
	}
}
