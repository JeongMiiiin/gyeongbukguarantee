package com.gyeongbuk.spring.guarantee.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gyeongbuk.spring.guarantee.dto.BranchDateSettingDto;
import com.gyeongbuk.spring.guarantee.dto.BranchDto;
import com.gyeongbuk.spring.guarantee.dto.BranchTimeSettingDto;

@Repository
public class BranchManageDaoImpl implements BranchManageDao {
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	@Override
	public List<BranchDto> selectListData(BranchDto searchDto) {
		List<BranchDto> result = new ArrayList<>();
		
		try {

			result = sqlSession.selectList(SERVICE_ID+".selectListData",searchDto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
		
	}
	
	@Override
	public int selectTotalCnt(BranchDto searchDto) {
		int result = 0;
		try {

			result = sqlSession.selectOne(SERVICE_ID+".selectTotalCnt",searchDto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public BranchDto selectViewData(int br_idx) {
		
		BranchDto result = null;
		
		try {

			result = sqlSession.selectOne(SERVICE_ID+".selectViewData",br_idx);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
		
	}
	
	@Override
	public BranchDto selectFrontViewData(int br_idx) {
		
		BranchDto result = null;
		
		try {

			result = sqlSession.selectOne(SERVICE_ID+".selectFrontViewData",br_idx);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
		
	}
	
	//특정시간대별 세팅 조회
	@Override
	public List<BranchTimeSettingDto> selectTimeSettingList(HashMap<String, Object> params) {
		
		List<BranchTimeSettingDto> result = null;
		
		try {			
			result = sqlSession.selectList(SERVICE_ID+".selectTimeSettingList", params);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
		
	}
	//특정일별 세팅 조회
	@Override
	public List<BranchDateSettingDto> selectDateSettingList(HashMap<String, Object> params) {
			
		List<BranchDateSettingDto> result = null;
			
		try {			
			result = sqlSession.selectList(SERVICE_ID+".selectDateSettingList", params);
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
			
	}
	
	@Override
	public HashMap<String, Object> selectTargetConsultDefaultNum(Map<String, Object> params) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		try {

			result = sqlSession.selectOne(SERVICE_ID+".selectTargetConsultDefaultNum",params);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public HashMap<String, Object> selectTargetConsultNum(Map<String, Object> params) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		try {

			result = sqlSession.selectOne(SERVICE_ID+".selectTargetConsultNum",params);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	@Transactional
	public int insertData(Map<String, Object> params) {
		
		int result = 0;
		
		try {
			
			sqlSession.insert(SERVICE_ID+".insertData",params);
			result = Integer.parseInt(String.valueOf(params.get("br_idx")));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;

	}
	
	@Override
	@Transactional
	public void updateData(Map<String, Object> params) {
		try {

			sqlSession.update(SERVICE_ID+".updateData",params);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@Override
	public int checkTimeSetting(BranchTimeSettingDto params) {
		int result = 0;
		
		try {
			
			result = sqlSession.selectOne(SERVICE_ID+".checkTimeSetting",params);
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
		return result;
	}
	
	@Override
	public int checkDateSetting(BranchDateSettingDto params) {
		int result = 0;
		
		try {

			result = sqlSession.selectOne(SERVICE_ID+".checkDateSetting",params);
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
		return result;
	}
	
	//지점 기본시간 추가
	@Override
	@Transactional
	public void insertDefaultConsultTime(Map<String, Object> params) {
		try {

			sqlSession.insert(SERVICE_ID+".insertDefaultConsultTime",params);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	};
	
	//지점 기본창구개수 추가
	@Override
	@Transactional
	public void insertDefaultConsultNum(Map<String, Object> params) {
		try {

			sqlSession.insert(SERVICE_ID+".insertDefaultConsultNum",params);
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	};
	
	@Override
	@Transactional
	public void insertOperationTime(Map<String, Object> params) {
		try {

			sqlSession.insert(SERVICE_ID+".insertOperationTime",params);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	};
	
	@Override
	@Transactional
	public void deleteTimeSettingData(HashMap<String, Object> params) {
		try {

			sqlSession.delete(SERVICE_ID+".deleteTimeSettingData",params);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@Override
	@Transactional
	public void insertTimeSettingData(BranchTimeSettingDto params) {
		try {

			sqlSession.insert(SERVICE_ID+".insertTimeSettingData",params);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@Override
	@Transactional
	public void deleteDateSettingData(int br_idx) {
		try {

			sqlSession.delete(SERVICE_ID+".deleteDateSettingData",br_idx);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@Override
	@Transactional
	public void insertDateSettingData(BranchDateSettingDto params) {
		try {

			sqlSession.insert(SERVICE_ID+".insertDateSettingData",params);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@Override
	@Transactional
	public void deleteData(int br_idx) {
		try {

			sqlSession.update(SERVICE_ID+".deleteData",br_idx);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@Override
	public HashMap<String, Object> selectTargetBranchData(HashMap<String, Object> map){
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		try {

			result = sqlSession.selectOne(SERVICE_ID+".selectTargetBranchData",map);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@Override
	public List<HashMap<String, Object>> selectTargetBranchTimeData(HashMap<String, Object> map){
		List<HashMap<String, Object>> result = new ArrayList<>();
		
		try {

			result = sqlSession.selectList(SERVICE_ID+".selectTargetBranchTimeData",map);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@Override
	public List<HashMap<String, Object>> selectTargetBranchDateData(HashMap<String, Object> map){
		List<HashMap<String, Object>> result = new ArrayList<>();
		
		try {

			result = sqlSession.selectList(SERVICE_ID+".selectTargetBranchDateData",map);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@Override
	public String selectTargetConsultTime(Map<String, Object> params) {
		String result = "";
		
		try {

			result = sqlSession.selectOne(SERVICE_ID+".selectTargetConsultTime",params);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public List<HashMap<String, Object>> selectTargetOperationTime(Map<String, Object> params) {
		List<HashMap<String, Object>> result = new ArrayList<>();
		
		try {

			result = sqlSession.selectList(SERVICE_ID+".selectTargetOperationTime",params);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
		
	}
	
	@Override
	public HashMap<String, Object> selectTargetOperationWeekTime(HashMap<String, Object> params) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		try {

			result = sqlSession.selectOne(SERVICE_ID+".selectTargetOperationWeekTime",params);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
		
	}
	
	@Override
	public List<HashMap<String, Object>> selectSpecialTimeList(HashMap<String, Object> params){
		List<HashMap<String, Object>> result = new ArrayList<>();
		try {

			result = sqlSession.selectList(SERVICE_ID+".selectSpecialTimeList",params);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public int checkBranchManager(int mem_idx) {
		int result = 0;
		try {
			result = sqlSession.selectOne(SERVICE_ID+".checkBranchManager",mem_idx);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	};
	
	@Override
	public List<HashMap<String, String>> selectTargetPrivateTimeSettingList(HashMap<String, String> params){
		List<HashMap<String, String>> result = new ArrayList<>();
		try {
			result =sqlSession.selectList(SERVICE_ID + ".selectTargetPrivateTimeSettingList", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	};
	
}
