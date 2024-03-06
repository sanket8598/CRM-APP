package ai.rnt.crm.dao.service.impl;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import ai.rnt.crm.entity.ServiceFallsMaster;
import ai.rnt.crm.repository.ServiceFallRepository;

/**
 * @author Nikhil Gaikwad
 * @since 06/03/2024.
 *
 */
class ServiceFallsDaoSeviceImplTest {

	@InjectMocks
	ServiceFallsDaoSeviceImpl serviceFallsDaoSeviceImpl;

	@Autowired
	MockMvc mockMvc;

	@InjectMocks
	ServiceFallsMaster serviceFallsMaster;

	@Mock
	private ServiceFallRepository serviceFallRepository;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(serviceFallsDaoSeviceImpl).build();
	}

	@Test
	void getServiceFallByIdTest() throws Exception {
        when(serviceFallRepository.findById(anyInt())).thenReturn(Optional.of(serviceFallsMaster));
        serviceFallsDaoSeviceImpl.getServiceFallById(1);
        verify(serviceFallRepository).findById(1);
	}

	@Test
	void findByNameTest() throws Exception {
        when(serviceFallRepository.findByServiceName(anyString())).thenReturn(Optional.of(serviceFallsMaster));
        serviceFallsDaoSeviceImpl.findByName("IT service");
        verify(serviceFallRepository).findByServiceName("IT service");
	}

	@Test
	void testGetAllServiceFalls() {
		List<ServiceFallsMaster> serviceFallsList = new ArrayList<>();
		ServiceFallsMaster serviceFall1 = new ServiceFallsMaster();
		serviceFallsList.add(serviceFall1);
		when(serviceFallRepository.findByDeletedDateIsNullOrderByServiceNameAsc()).thenReturn(serviceFallsList);
		serviceFallsDaoSeviceImpl.getAllSerciveFalls();
		verify(serviceFallRepository).findByDeletedDateIsNullOrderByServiceNameAsc();
	}

	@Test
	void testSave() throws Exception {
		ServiceFallsMaster serviceFalls = new ServiceFallsMaster();
		when(serviceFallRepository.save(serviceFalls)).thenReturn(serviceFalls);
		serviceFallsDaoSeviceImpl.save(serviceFalls);
		verify(serviceFallRepository).save(serviceFalls);
	}
}
