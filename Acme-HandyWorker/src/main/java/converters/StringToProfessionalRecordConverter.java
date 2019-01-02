
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.ProfessionalRecordRepository;
import domain.ProfessionalRecord;

@Component
@Transactional
public class StringToProfessionalRecordConverter implements Converter<String, ProfessionalRecord> {

	@Autowired
	ProfessionalRecordRepository	professionalRecordRepository;


	@Override
	public ProfessionalRecord convert(final String source) {
		final ProfessionalRecord professionalRecord;
		int id;

		if (StringUtils.isEmpty(source))
			professionalRecord = null;
		else
			try {
				id = Integer.valueOf(source);
				professionalRecord = this.professionalRecordRepository.findOne(id);

			} catch (final Throwable throwable) {
				throw new IllegalArgumentException();
			}

		return professionalRecord;
	}

}