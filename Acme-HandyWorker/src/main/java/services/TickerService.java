
package services;

import java.util.Collection;
import java.util.Date;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.Ticker;
import repositories.TickerRepository;

@Service
@Transactional
public class TickerService {

	//Repository------------------------------------------------------------

	@Autowired
	private TickerRepository tickerRepository;


	//Constructor-----------------------------------------------------------

	public TickerService() {
		super();
	}

	//CRUD Methods----------------------------------------------------------

	public Ticker create() {
		final Ticker ticker = new Ticker();
		ticker.setTicker(this.generateTicker());
		return ticker;
	}

	public Collection<Ticker> findAll() {
		return this.tickerRepository.findAll();
	}

	public Ticker save(final Ticker ticker) {
		return this.tickerRepository.save(ticker);
	}

	public Ticker findOne(final Integer tickerId) {
		return this.tickerRepository.findOne(tickerId);
	}

	public void delete(final Ticker ticker) {
		this.tickerRepository.delete(ticker);
	}

	//Other Methods------------------------------------------------------------

	@SuppressWarnings("deprecation")
	public String generateTicker() {
		final Date date = new Date();
		final Integer s1 = date.getDate();
		String day = s1.toString();
		if (day.length() == 1)
			day = "0" + day;
		final Integer s2 = date.getMonth() + 1;
		String month = s2.toString();
		if (month.length() == 1)
			month = "0" + month;
		final Integer s3 = date.getYear();
		final String year = s3.toString().substring(1);

		return year + month + day + "-" + this.generateStringAux();
	}

	private String generateStringAux() {
		final int length = 6;
		final String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		final Random rng = new Random();
		final char[] text = new char[length];
		for (int i = 0; i < 6; i++)
			text[i] = characters.charAt(rng.nextInt(characters.length()));
		return new String(text);
	}

	public Ticker isUniqueTicker() {
		Ticker result = this.create();

		if (this.findAll().contains(result))
			result = this.isUniqueTicker();

		return result;
	}

}
