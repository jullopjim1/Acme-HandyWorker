
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import services.RefereeService;
import domain.Referee;

@Component
@Transactional
public class StringToRefereeConverter implements Converter<String, Referee> {

	@Autowired
	RefereeService	refereeService;


	@Override
	public Referee convert(final String text) {
		Referee result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = this.refereeService.findOne(id);
		} catch (final Exception oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
