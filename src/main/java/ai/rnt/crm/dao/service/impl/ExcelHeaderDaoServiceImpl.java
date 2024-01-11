package ai.rnt.crm.dao.service.impl;

import static ai.rnt.crm.constants.CacheConstant.EXCEL_HEADER;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ai.rnt.crm.dao.service.ExcelHeaderDaoService;
import ai.rnt.crm.entity.ExcelHeaderMaster;
import ai.rnt.crm.repository.ExcelHeaderRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ExcelHeaderDaoServiceImpl implements ExcelHeaderDaoService {

	private final ExcelHeaderRepository excelHeaderRepository;

	@Override
	@Cacheable(value = EXCEL_HEADER)
	public List<ExcelHeaderMaster> getExcelHeadersFromDB() {
		return excelHeaderRepository.findAll();
	}
}
