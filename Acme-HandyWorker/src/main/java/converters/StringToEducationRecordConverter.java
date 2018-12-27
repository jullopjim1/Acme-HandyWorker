
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.EducationRecordRepository;
import domain.EducationRecord;

@Component
@Transactional
public class StringToEducationRecordConverter implements Converter<String, EducationRecord> {

	@Autowired
	EducationRecordRepository	educationRecordRepository;


	@Override
	public EducationRecord convert(final String source) {
		final EducationRecord educationRecord;
		int id;

		if (StringUtils.isEmpty(source))
			educationRecord = null;
		else
			try {
				id = Integer.valueOf(source);
				educationRecord = this.educationRecordRepository.findOne(id);

			} catch (final Throwable throwable) {
				throw new IllegalArgumentException();
			}

		return educationRecord;
	}
}
