
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.HandyWorker;
import services.HandyWorkerService;

@Component
@Transactional
public class StringToHandyWorkerConverter implements Converter<String, HandyWorker> {

	@Autowired
	HandyWorkerService handyWorkerService;


	@Override
	public HandyWorker convert(final String text) {
		HandyWorker result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = this.handyWorkerService.findOne(id);
		} catch (final Exception oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
