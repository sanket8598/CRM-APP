package ai.rnt.crm.dto;

import static java.util.Objects.nonNull;

import ai.rnt.crm.util.LeadsCardUtil;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditContactDto {

	private ContactDto primaryContact;

	public String getShortName() {
		return nonNull(primaryContact)
				? LeadsCardUtil.shortName(primaryContact.getFirstName(), primaryContact.getLastName())
				: null;
	}
}
