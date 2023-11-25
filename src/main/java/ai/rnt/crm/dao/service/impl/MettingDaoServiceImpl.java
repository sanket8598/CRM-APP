package ai.rnt.crm.dao.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ai.rnt.crm.dao.service.MettingDaoService;
import ai.rnt.crm.entity.Mettings;
import ai.rnt.crm.repository.MettingRepository;
import lombok.RequiredArgsConstructor;

/**
 * @author Nikhil Gaikwad
 * @since 25/11/2023
 * @version 1.0
 *
 */
@Service
@RequiredArgsConstructor
@Transactional
public class MettingDaoServiceImpl implements MettingDaoService {

	private final MettingRepository mettingRepository;

	@Override
	public Mettings addMetting(Mettings metting) {
		return mettingRepository.save(metting);
	}

}
