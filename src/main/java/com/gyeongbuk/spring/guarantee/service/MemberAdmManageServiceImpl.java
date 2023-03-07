package com.gyeongbuk.spring.guarantee.service;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.gyeongbuk.spring.guarantee.dao.MemberAdmManageDao;
import com.gyeongbuk.spring.guarantee.dto.MemberAdmDto;
import com.gyeongbuk.spring.guarantee.dto.Pagination;

@Service
public class MemberAdmManageServiceImpl extends RootService implements MemberAdmManageService {
	
		@Autowired
		private MemberAdmManageDao dao;
		
		@Autowired
		private BranchManageAdmService branchManageAdminService;
		
		/* 리스트 페이지 */
		@Override
		public String listPage(MemberAdmDto searchDto, Model model) {
			Pagination pagination = new Pagination();
			pagination.setCurrentPageNo(searchDto.getPageIndex());
			pagination.setRecordCountPerPage(searchDto.getPageUnit());
			pagination.setPageSize(searchDto.getPageSize());
			
			searchDto.setFirstIndex(pagination.getFirstRecordIndex());
			searchDto.setRecordCountPerPage(pagination.getRecordCountPerPage());
			
			List<MemberAdmDto> result = dao.selectListData(searchDto);
			int totalCnt = dao.selectTotalCnt(searchDto);

			pagination.setTotalRecordCount(totalCnt);

			searchDto.setEndDate(pagination.getLastPageNoOnPageList());
			searchDto.setStartDate(pagination.getFirstPageNoOnPageList());
			searchDto.setPrev(pagination.getXprev());
			searchDto.setNext(pagination.getXnext());
			searchDto.setRealEnd(pagination.getRealEnd());
			
			model.addAttribute("resultList", result);
			model.addAttribute("totCnt",totalCnt);
			model.addAttribute("totalPageCnt",(int)Math.ceil(totalCnt / (double)searchDto.getPageUnit()));
			model.addAttribute("pagination",pagination);
			
			try {
				searchDto.setQustr();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return "admin/member/list";
		}
		
		@Override
		public String insertPage(MemberAdmDto searchDto) {
			try {
				searchDto.setQustr();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return "admin/member/insert";
		}
		
		/* 중복확인 */
		@Override
		@Transactional
		public HashMap<String, Object> checkDuplicateId(HashMap<String, Object> params, HttpServletRequest request) {

			HashMap<String, Object> result = new HashMap<String, Object>();
			int data = dao.checkDuplicateId(params); 
			
			if (data == 1) {
				result.put("result", true);
			} else {
				result.put("result", false);
			}
			
			return result;
			
		}
		
		/* 등록 */
		@Override
		@Transactional
		public void insertData( MemberAdmDto searchDto, HttpServletResponse response ) {
			
			String password = searchDto.getMemPassword();
			
			try {
				password = SHA256.encrypt(password);
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			searchDto.setMemPassword(password);
			
			try {
				searchDto.setQustr();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			int result = dao.insertData(searchDto);
			
			String msg = "";
			String targetLink = "/reserve/adm/memberManage/list";
			
			if(result > 0) {
				msg = "등록이 완료되었습니다.";
			} else {
				msg = "등록에 실패했습니다. 잠시 후 다시 시도해주세요";
			}
			
			makeSubmitAlertMsg(response, msg, targetLink);
			
		}
		
		@Override
		public String viewPage(MemberAdmDto searchDto, int memIdx, Model model) {
			MemberAdmDto viewData = dao.selectViewData(memIdx);
			model.addAttribute("updateData", viewData);
			
			try {
				searchDto.setQustr();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return "admin/member/view";
		}
		
		@Override
		public String updatePage(MemberAdmDto searchDto, int memIdx, Model model) {
			MemberAdmDto viewData = dao.selectViewData(memIdx);
			model.addAttribute("updateData", viewData);
			
			try {
				searchDto.setQustr();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return "admin/member/update";
		}
		
		/* 업데이트 */
		@Override
		@Transactional
		public void updateData( MemberAdmDto searchDto, HttpServletResponse response ) {
			int result = dao.updateData(searchDto);
			
			try {
				searchDto.setQustr();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			String msg = "";
			String targetLink = "/reserve/adm/memberManage/list";
			
			if(result > 0) {
				msg = "변경이 완료되었습니다.";
			} else {
				msg = "변경에 실패했습니다. 잠시 후 다시 시도해주세요";
			}
			
			makeSubmitAlertMsg(response, msg, targetLink);
			
		}
		
		/* 비밀번호 초기화 */
		@Override
		@Transactional
		public HashMap<String, Object> initializePassword(HashMap<String, Object> params, HttpServletRequest request) {
			String password = "12345678";
			
			try {
				password = SHA256.encrypt(password);
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			params.put("userPw", password);
			
			HashMap<String, Object> result = new HashMap<String, Object>();
			int data = dao.initializePassword(params); 
			
			if (data == 1) {
				result.put("result", true);
			} else {
				result.put("result", false);
			}
			
			return result;
			
		}
		
		/* 논리적 삭제 */
		@Override
		@Transactional
		public void deleteData( MemberAdmDto searchDto, int memIdx, HttpServletResponse response ) {
			
			int checkBranch = branchManageAdminService.checkBranchManager(memIdx);
			
			String msg = "";
			String targetLink = "/reserve/adm/memberManage/list";
			
			if(checkBranch == 0) {
				int result = dao.deleteData(memIdx);
				
				if(result > 0) {
					msg = "삭제가 완료되었습니다.";
				} else {
					msg = "삭제에 실패했습니다. 잠시 후 다시 시도해주세요";
				}
			} else {
				targetLink = "/reserve/adm/memberManage/update?mem_idx=" + memIdx;
				msg = "지점의 담당자로 설정되어 있습니다. 담당자 변경 후 다시 시도해주세요.";
			}
			
			makeSubmitAlertMsg(response, msg, targetLink);
			
		}
		
		//암호 SHA256으로 변환
		public static class SHA256 {

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
		
		@Override
		public List<MemberAdmDto> selectManagerList(){
			return dao.selectManagerList();
		};
		
}
