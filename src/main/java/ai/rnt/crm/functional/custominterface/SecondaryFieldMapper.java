package ai.rnt.crm.functional.custominterface;

import ai.rnt.crm.dto.LeadsCardDto;
import ai.rnt.crm.entity.Leads;

@FunctionalInterface
public interface SecondaryFieldMapper {
	void apply(Leads lead, LeadsCardDto leadsCardDto);

}
