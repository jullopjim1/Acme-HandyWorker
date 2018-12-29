
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import services.ProfileService;
import domain.Profile;

@Component
@Transactional
public class StringToProfileConverter implements Converter<String, Profile> {

	@Autowired
	ProfileService	profileService;


	@Override
	public Profile convert(final String text) {
		Profile result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = this.profileService.findOne(id);
		} catch (final Exception oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
