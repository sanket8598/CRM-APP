package ai.rnt.crm.dao.service;

import java.util.List;

import ai.rnt.crm.entity.ExcelHeaderMaster;

public interface ExcelHeaderDaoService{

	public List <ExcelHeaderMaster> getExcelHeadersFromDB();
}
