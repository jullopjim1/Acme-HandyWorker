
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.MiscellaneousRecordRepository;
import domain.MiscellaneousRecord;

@Component
@Transactional
public class StringToMiscellaneousRecordConverter implements Converter<String, MiscellaneousRecord> {

	@Autowired
	MiscellaneousRecordRepository	miscellaneousRecordRepository;


	@Override
	public MiscellaneousRecord convert(final String source) {
		final MiscellaneousRecord miscellaneousRecord;
		int id;

		if (StringUtils.isEmpty(source))
			miscellaneousRecord = null;
		else
			try {
				id = Integer.valueOf(source);
				miscellaneousRecord = this.miscellaneousRecordRepository.findOne(id);

			} catch (final Throwable throwable) {
				throw new IllegalArgumentException();
			}

		return miscellaneousRecord;
	}

}
