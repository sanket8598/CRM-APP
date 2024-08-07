package ai.rnt.crm.service.impl;

import static ai.rnt.crm.constants.CRMConstants.ACTIVITY;
import static ai.rnt.crm.constants.CRMConstants.CONTACT_INFO;
import static ai.rnt.crm.constants.CRMConstants.DESCRIPTION_DATA;
import static ai.rnt.crm.constants.CRMConstants.LEAD_ID;
import static ai.rnt.crm.constants.CRMConstants.TASK;
import static ai.rnt.crm.constants.CRMConstants.TIMELINE;
import static ai.rnt.crm.constants.CRMConstants.UPNEXT_DATA;
import static ai.rnt.crm.constants.StatusConstants.LEAD;
import static ai.rnt.crm.constants.StatusConstants.OPPORTUNITY;
import static ai.rnt.crm.dto.opportunity.mapper.OpportunityDtoMapper.TO_DASHBOARD_OPPORTUNITY_DTO;
import static ai.rnt.crm.dto_mapper.ContactDtoMapper.TO_CONTACT_DTO;
import static ai.rnt.crm.dto_mapper.ContactDtoMapper.TO_CONTACT_DTOS;
import static ai.rnt.crm.dto_mapper.ContactDtoMapper.TO_EDITCONTACT_DTO;
import static ai.rnt.crm.enums.ApiResponse.DATA;
import static ai.rnt.crm.enums.ApiResponse.MESSAGE;
import static ai.rnt.crm.enums.ApiResponse.SUCCESS;
import static ai.rnt.crm.util.CommonUtil.getActivityData;
import static ai.rnt.crm.util.CommonUtil.getDescData;
import static ai.rnt.crm.util.CommonUtil.getTaskDataMap;
import static ai.rnt.crm.util.CommonUtil.getTimelineData;
import static ai.rnt.crm.util.CommonUtil.getUpnextData;
import static ai.rnt.crm.util.CommonUtil.upNextActivities;
import static ai.rnt.crm.util.StringUtil.hasWhitespace;
import static ai.rnt.crm.util.StringUtil.splitByWhitespace;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.springframework.http.HttpStatus.OK;

import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ai.rnt.crm.dao.service.CallDaoService;
import ai.rnt.crm.dao.service.CityDaoService;
import ai.rnt.crm.dao.service.CompanyMasterDaoService;
import ai.rnt.crm.dao.service.ContactDaoService;
import ai.rnt.crm.dao.service.CountryDaoService;
import ai.rnt.crm.dao.service.EmailDaoService;
import ai.rnt.crm.dao.service.LeadDaoService;
import ai.rnt.crm.dao.service.MeetingDaoService;
import ai.rnt.crm.dao.service.OpportunityDaoService;
import ai.rnt.crm.dao.service.StateDaoService;
import ai.rnt.crm.dao.service.VisitDaoService;
import ai.rnt.crm.dto.ContactDto;
import ai.rnt.crm.dto.EditContactDto;
import ai.rnt.crm.dto.opportunity.OpportunityDto;
import ai.rnt.crm.entity.Call;
import ai.rnt.crm.entity.CityMaster;
import ai.rnt.crm.entity.CompanyMaster;
import ai.rnt.crm.entity.Contacts;
import ai.rnt.crm.entity.CountryMaster;
import ai.rnt.crm.entity.Description;
import ai.rnt.crm.entity.Email;
import ai.rnt.crm.entity.Leads;
import ai.rnt.crm.entity.Meetings;
import ai.rnt.crm.entity.Opportunity;
import ai.rnt.crm.entity.StateMaster;
import ai.rnt.crm.entity.Visit;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.exception.CRMException;
import ai.rnt.crm.exception.ResourceNotFoundException;
import ai.rnt.crm.service.CustomerService;
import ai.rnt.crm.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {

	private final ContactDaoService contactDaoService;
	private final CompanyMasterDaoService companyMasterDaoService;
	private final LeadDaoService leadDaoService;
	private final CallDaoService callDaoService;
	private final VisitDaoService visitDaoService;
	private final EmailDaoService emailDaoService;
	private final MeetingDaoService meetingDaoService;
	private final EmployeeService employeeService;
	private final OpportunityDaoService opportunityDaoService;
	private final CountryDaoService countryDaoService;
	private final StateDaoService stateDaoService;
	private final CityDaoService cityDaoService;

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> customerDashBoardData() {
		log.info("inside the customerDashBoardData method...");
		EnumMap<ApiResponse, Object> customerDashbordData = new EnumMap<>(ApiResponse.class);
		try {
			customerDashbordData.put(DATA, TO_CONTACT_DTOS.apply(contactDaoService.findAllPrimaryContacts()));
			customerDashbordData.put(SUCCESS, true);
			return new ResponseEntity<>(customerDashbordData, OK);
		} catch (Exception e) {
			customerDashbordData.put(SUCCESS, false);
			log.error("error occured while getting customer list..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> editCustomer(String field, Integer customerId) {
		log.info("inside the editCustomer method...{}", customerId);
		EnumMap<ApiResponse, Object> customer = new EnumMap<>(ApiResponse.class);
		customer.put(SUCCESS, false);
		try {
			if (isNull(field) || field.isEmpty())
				return new ResponseEntity<>(customer, OK);

			Map<String, Object> dataMap = new LinkedHashMap<>();
			Contacts contactById = contactDaoService.findById(customerId)
					.orElseThrow(() -> new ResourceNotFoundException("Contacts", "contactId", customerId));
			Leads leadById = leadDaoService.getLeadById(contactById.getLead().getLeadId())
					.orElseThrow(() -> new ResourceNotFoundException(LEAD, LEAD_ID, contactById.getLead().getLeadId()));
			Optional<EditContactDto> dto = TO_EDITCONTACT_DTO.apply(leadById);
			dto.ifPresent(e -> {
				e.setPrimaryContact(TO_CONTACT_DTO
						.apply(leadById.getContacts().stream().filter(Contacts::getPrimary).findFirst()
								.orElseThrow(() -> new ResourceNotFoundException("Priamry Contact")))
						.orElseThrow(ResourceNotFoundException::new));
			});
			if (LEAD.equalsIgnoreCase(field)) {
				List<Call> calls = callDaoService.getCallsByLeadIdAndIsOpportunity(leadById.getLeadId());
				List<Visit> visits = visitDaoService.getVisitsByLeadIdAndIsOpportunity(leadById.getLeadId());
				List<Email> emails = emailDaoService.getEmailByLeadIdAndIsOpportunity(leadById.getLeadId());
				List<Meetings> meetings = meetingDaoService.getMeetingByLeadIdAndIsOpportunity(leadById.getLeadId());
				List<Description> descriptions = leadDaoService
						.getDescriptionByLeadIdAndIsOpportunity(leadById.getLeadId());
				dataMap.put(CONTACT_INFO, dto);
				dataMap.put(TIMELINE, getTimelineData(calls, visits, emails, meetings, employeeService));
				dataMap.put(DESCRIPTION_DATA, getDescData(descriptions));
				dataMap.put(ACTIVITY, getActivityData(calls, visits, emails, meetings, employeeService));
				dataMap.put(UPNEXT_DATA,
						upNextActivities(getUpnextData(calls, visits, emails, meetings, employeeService)));
				dataMap.put(TASK, getTaskDataMap(calls, visits, meetings, leadById, null));
				customer.put(SUCCESS, true);
				customer.put(DATA, dataMap);
			} else if (OPPORTUNITY.equalsIgnoreCase(field)) {
				if (isNull(leadById.getOpportunity())) {
					dataMap.put(CONTACT_INFO, dto);
					customer.put(MESSAGE, "This Customer Not In Opportunity.");
					customer.put(SUCCESS, true);
					customer.put(DATA, dataMap);
				} else {
					Opportunity opportunity = opportunityDaoService
							.findOpportunity(leadById.getOpportunity().getOpportunityId())
							.orElseThrow(() -> new ResourceNotFoundException("Opportunity", "optId",
									leadById.getOpportunity().getOpportunityId()));
					Integer leadId = opportunity.getLeads().getLeadId();
					List<Call> calls = callDaoService.getCallsByLeadId(leadId);
					List<Visit> visits = visitDaoService.getVisitsByLeadId(leadId);
					List<Email> emails = emailDaoService.getEmailByLeadId(leadId);
					List<Meetings> meetings = meetingDaoService.getMeetingByLeadId(leadId);
					List<Description> descriptions = leadDaoService.getDescriptionByLeadId(leadId);
					OpportunityDto optyDto = TO_DASHBOARD_OPPORTUNITY_DTO.apply(opportunity)
							.orElseThrow(ResourceNotFoundException::new);
					dataMap.put(CONTACT_INFO, optyDto);
					dataMap.put(TIMELINE, getTimelineData(calls, visits, emails, meetings, employeeService));
					dataMap.put(DESCRIPTION_DATA, getDescData(descriptions));
					dataMap.put(ACTIVITY, getActivityData(calls, visits, emails, meetings, employeeService));
					dataMap.put(UPNEXT_DATA,
							upNextActivities(getUpnextData(calls, visits, emails, meetings, employeeService)));
					dataMap.put(TASK, getTaskDataMap(calls, visits, meetings, opportunity.getLeads(), opportunity));
					customer.put(SUCCESS, true);
					customer.put(DATA, dataMap);
				}
			}
			return new ResponseEntity<>(customer, OK);
		} catch (Exception e) {
			log.error("Got Exception while getting data for the customer..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	@Transactional
	public ResponseEntity<EnumMap<ApiResponse, Object>> updateCustomer(ContactDto contactDto, Integer customerId) {
		log.info("inside the updateCustomer method...{}", customerId);
		EnumMap<ApiResponse, Object> customerData = new EnumMap<>(ApiResponse.class);
		customerData.put(SUCCESS, false);
		try {
			Contacts contact = contactDaoService.findById(customerId)
					.orElseThrow(() -> new ResourceNotFoundException("Contact", "contactId", customerId));
			CompanyMaster companyMaster = companyMasterDaoService
					.findCompanyById(contactDto.getCompanyMaster().getCompanyId())
					.orElseThrow(() -> new ResourceNotFoundException("CompanyMaster", "companyId",
							contactDto.getCompanyMaster().getCompanyId()));
			CountryMaster countryMaster = countryDaoService
					.findCountryById(contactDto.getCompanyMaster().getCountry().getCountryId())
					.orElseThrow(() -> new ResourceNotFoundException("CountryMaster", "countryId",
							contactDto.getCompanyMaster().getCountry().getCountryId()));
			StateMaster stateMaster = stateDaoService
					.findStateById(contactDto.getCompanyMaster().getState().getStateId())
					.orElseThrow(() -> new ResourceNotFoundException("StateMaster", "stateId",
							contactDto.getCompanyMaster().getState().getStateId()));
			CityMaster cityMaster = cityDaoService.findCityById(contactDto.getCompanyMaster().getCity().getCityId())
					.orElseThrow(() -> new ResourceNotFoundException("CityMaster", "cityId",
							contactDto.getCompanyMaster().getCity().getCityId()));
			if (nonNull(contactDto.getName())) {
				String[] names = splitByWhitespace(contactDto.getName());
				if (hasWhitespace(contactDto.getName()) && names.length == 2) {
					contact.setFirstName(nonNull(names[0]) ? names[0] : "");
					contact.setLastName("null".equalsIgnoreCase(names[1]) ? null : names[1]);
				} else {
					contact.setFirstName(nonNull(contactDto.getName()) ? contactDto.getName() : "");
					contact.setLastName(null);
				}
			}
			contact.setDesignation(contactDto.getDesignation());
			contact.setWorkEmail(contactDto.getWorkEmail());
			contact.setContactNumberPrimary(contactDto.getContactNumberPrimary());
			companyMaster.setCompanyWebsite(contactDto.getCompanyMaster().getCompanyWebsite());
			companyMaster.setAddressLineOne(contactDto.getCompanyMaster().getAddressLineOne());
			companyMaster.setZipCode(contactDto.getCompanyMaster().getZipCode());
			companyMaster.setCountry(countryMaster);
			companyMaster.setState(stateMaster);
			companyMaster.setCity(cityMaster);
			contact.setCompanyMaster(companyMaster);
			if (nonNull(companyMasterDaoService.save(companyMaster))) {
				if (nonNull(contactDaoService.addContact(contact))) {
					customerData.put(MESSAGE, "Customer Updated Successfully !!");
					customerData.put(SUCCESS, true);
				}
			} else
				customerData.put(MESSAGE, "Customer Not Updated !!");

			return new ResponseEntity<>(customerData, OK);
		} catch (Exception e) {
			log.error("Got Exception while updating the customer data..{}", e.getMessage());
			throw new CRMException(e);
		}
	}
}
