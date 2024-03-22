package ai.rnt.crm.security;

import static ai.rnt.crm.constants.CRMConstants.EMAIL_ID;
import static ai.rnt.crm.constants.CRMConstants.FULL_NAME;
import static ai.rnt.crm.constants.CRMConstants.ROLE;
import static ai.rnt.crm.constants.CRMConstants.STAFF_ID;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import ai.rnt.crm.dto.EmployeeDto;
import ai.rnt.crm.service.EmployeeService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@ExtendWith(MockitoExtension.class)
class JWTTokenHelperTest {

	@InjectMocks
	private JWTTokenHelper jwtTokenHelper;

	@Mock
	private EmployeeService employeeService;

	@Test
	void testGenerateToken_Success() throws Exception {
		String username = "testUser";
		String fullName = "John Doe";
		Integer staffId = 1234;
		String emailId = "johndoe@example.com";
		String role = "ROLE_USER";

		UserDetails userDetails = Mockito.mock(UserDetails.class);
		Mockito.when(userDetails.getUsername()).thenReturn(username);

		Map<String, Object> expectedClaims = new HashMap<>();
		expectedClaims.put(FULL_NAME, fullName);
		expectedClaims.put(STAFF_ID, staffId);
		expectedClaims.put(EMAIL_ID, emailId);
		expectedClaims.put(ROLE, role);

		Mockito.when(employeeService.getEmployeeByUserId(username))
				.thenReturn(Optional.of(Mockito.mock(EmployeeDto.class)));
		String token = jwtTokenHelper.generateToken(userDetails);
		Claims claims = Jwts.parser().setSigningKey(JWTTokenHelper.keyPair.getPublic()).parseClaimsJws(token).getBody();
		assertNotNull(claims);
	}
}
