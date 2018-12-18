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

import domain.Curriculum;
import repositories.CurriculumRepository;

@Component
@Transactional
public class StringToCurriculumConverter implements Converter<String, Curriculum> {

	@Autowired
	CurriculumRepository curriculumRepository;


	@Override
	public Curriculum convert(final String text) {
		Curriculum result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = this.curriculumRepository.findOne(id);
		} catch (final Exception oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
