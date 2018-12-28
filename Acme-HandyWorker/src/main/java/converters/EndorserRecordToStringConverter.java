
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.EndorserRecord;

@Component
@Transactional
public class EndorserRecordToStringConverter implements Converter<EndorserRecord, String> {

	@Override
	public String convert(final EndorserRecord endorserRecord) {
		String string;

		if (endorserRecord == null)
			string = null;
		else
			string = String.valueOf(endorserRecord.getId());

		return string;
	}

}
