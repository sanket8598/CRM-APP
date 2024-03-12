package ai.rnt.crm.util;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ai.rnt.crm.security.JWTTokenHelper;

class AuditAwareUtilTest {

	@Mock
    private JWTTokenHelper jwtTokenHelper;

    @InjectMocks
    private AuditAwareUtil auditAwareUtil;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }


}
