package ai.rnt.crm.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConvertDateFormat {

	private ConvertDateFormat() {

	}

	public static Date convertDate(String toConvertDate) {
		try {
			SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
			return outputFormat
					.parse(outputFormat.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(toConvertDate)));
		} catch (Exception e) {
			log.error("Got Excetion while converting date format:",
					 e.getMessage());
		}
		return null;
	}
}
