
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.EducationRecord;

@Component
@Transactional
public class EducationRecordToStringConverter implements Converter<EducationRecord, String> {

	@Override
	public String convert(final EducationRecord educationRecord) {
		String string;

		if (educationRecord == null)
			string = null;
		else
			string = String.valueOf(educationRecord.getId());

		return string;
	}
}
