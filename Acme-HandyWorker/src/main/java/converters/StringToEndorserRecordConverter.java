
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.EndorserRecordRepository;
import domain.EndorserRecord;

@Component
@Transactional
public class StringToEndorserRecordConverter implements Converter<String, EndorserRecord> {

	@Autowired
	EndorserRecordRepository endorserRecordRepository;


	@Override
	public EndorserRecord convert(final String source) {
		final EndorserRecord endorserRecord;
		int id;

		if (StringUtils.isEmpty(source))
			endorserRecord = null;
		else
			try {
				id = Integer.valueOf(source);
				endorserRecord = this.endorserRecordRepository.findOne(id);

			} catch (final Throwable throwable) {
				throw new IllegalArgumentException();
			}

		return endorserRecord;
	}

}
