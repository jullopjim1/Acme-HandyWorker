/*
 * StringToCurriculaConverter.java
 *
 * Copyright (C) 2016 Universidad de Sevilla
 *
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Phase;
import repositories.PhaseRepository;

@Component
@Transactional
public class StringToPhaseConverter implements Converter<String, Phase> {

	@Autowired
	PhaseRepository phaseRepository;


	@Override
	public Phase convert(final String text) {
		Phase result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = this.phaseRepository.findOne(id);
		} catch (final Exception oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
