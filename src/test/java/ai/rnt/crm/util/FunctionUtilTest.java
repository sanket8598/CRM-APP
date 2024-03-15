package ai.rnt.crm.util;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class FunctionUtilTest {

	@InjectMocks
	private FunctionUtil functionUtil;

	@Autowired
	MockMvc mockMvc;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(functionUtil).build();
	}

	@Test
	void testEvalMapperCollection_NullCollection() {
		Collection<Object> list = null;
		Collection<String> result = FunctionUtil.evalMapperCollection(list, String.class);
		Collection<Object> list1 = Arrays.asList("a", "b", "c");
		List<Object> result1 = (List<Object>) FunctionUtil.evalMapperCollection(list1, null);
		Collection<Object> list2 = Collections.emptyList();
		Collection<String> result2 = FunctionUtil.evalMapperCollection(list2, String.class);
		assertTrue(result.isEmpty());
		assertTrue(result1.isEmpty());
		assertTrue(result2.isEmpty());
	}

	@Test
	void testEvalMapperCollection_MapperReturnsValidValue() {
		Collection<String> list = Arrays.asList("1", "2", "3");
		List<Integer> result = (List<Integer>) FunctionUtil.evalMapperCollection(list, Integer.class);
		assertEquals(Arrays.asList(1, 2, 3), result);
	}

	@Test
	void testEvalMapperProjectionNullObject() {
		Object obj = null;
		Object obj1 = new FunctionUtil();
		Optional<String> result = FunctionUtil.evalMapperProjection(obj, String.class);
		assertTrue(result.isEmpty());
		Optional<String> result1 = FunctionUtil.evalMapperProjection(obj1, String.class);
		assertTrue(result1.isPresent());
		Optional<String> result2 = FunctionUtil.evalMapperProjection(obj1, String.class);
		assertFalse(result2.isEmpty());
		Optional<String> result3 = FunctionUtil.evalMapperProjection(obj1, null);
		assertTrue(result3.isEmpty());
	}
}
