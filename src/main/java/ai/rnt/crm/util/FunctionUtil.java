package ai.rnt.crm.util;

import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

/**
 * This utility class contains the method used to perform operation on java functions 
 * and convertions between entity and DTO, and DTO into entity.
 * 
 * @author Sanket Wakankar
 * @since 19-08-2023
 * @version 1.0
 * File FunctionUtil 
 */
public class FunctionUtil {

	private FunctionUtil() {}

	private static final ModelMapper mp = new ModelMapper();
	
	static {
		 mp.getConfiguration().setAmbiguityIgnored(true);
		 mp.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		 mp.getConfiguration().setPreferNestedProperties(false);
	}
   
	/**
	 * This method will take any type list and java function and 
	 * apply passed function to each element in list.
	 * @param <T>
	 * @param <R>
	 * @param list
	 * @param function
	 * @return null if passed list or function is null. Otherwise list evaluated by function
	 * @since version 1.0
	 */
	public static <T, R> List<R> eval(List<T> list, Function<T, R> function) {
		return isNull(list) || list.isEmpty() ? Collections.emptyList() : list.stream().map(function).distinct().collect(toList());
	}
	/**
	 * This method will take any object and class. 
	 * map object to passed class type and return.
	 * @param <T>
	 * @param obj
	 * @param clazz
	 * @return Map fields of passed object to passed class
	 * @since version 1.0
	 */
	public static <T> Optional<T> evalMapper(Object obj, Class<T> clazz) {
		return isNull(obj) || isNull(clazz) ? Optional.empty() : Optional.of(mp.map(obj, clazz));
	}
	/**
	 * This method will take any object and class. 
	 * map object to passed class type and return.
	 * @param <T>
	 * @param obj
	 * @param clazz
	 * @return Map fields of passed object to passed class
	 * @since version 1.0
	 */
	public static <T> Optional<T> evalMapperProjection(Object obj, Class<T> clazz) {
		return isNull(obj) || isNull(clazz) ? Optional.empty() : Optional.of(mp.map(obj, clazz));
	}
	
	public static <C,T> Collection<T> evalMapperCollection(Collection<C> list, Class<T> clazz) {
		if (isNull(list) || isNull(clazz) || list.isEmpty()) return Collections.emptyList();
		return list.stream().map(e -> evalMapper(e, clazz)).filter(Optional :: isPresent)
			.map(Optional :: get).collect(Collectors.toList());
	}
}