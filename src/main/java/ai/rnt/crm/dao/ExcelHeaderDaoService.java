package ai.rnt.crm.dao;

import java.util.List;

import ai.rnt.crm.entity.ExcelHeaderMaster;

public interface ExcelHeaderDaoService{

	public List <ExcelHeaderMaster> getExcelHeadersFromDB();
}
