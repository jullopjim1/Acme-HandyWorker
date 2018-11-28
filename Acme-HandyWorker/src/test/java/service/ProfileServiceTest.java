
package service;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import services.ProfileService;
import utilities.AbstractTest;
import domain.Profile;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ProfileServiceTest extends AbstractTest {

	//Service ------------------------------
	@Autowired
	private ProfileService	profileService;


	//Test
	@Test
	public void testCreate() {
		System.out.println("========== testCreate() ==========");
		final int actorId = this.getEntityId("admin");
		try {
			final Profile profile = this.profileService.create(actorId);
			profile.setLink("http://profile7.com");
			profile.setName("Antonio");
			profile.setNick("piwflow");

			Assert.notNull(profile);

			System.out.println("¡Exito!");

		} catch (final Exception e) {
			System.out.println("¡Fallo," + e.getMessage() + "!");
		}

	}

	@Test
	public void testFindOne() {
		System.out.println("========== testFindOne() ==========");
		final int profileId = this.getEntityId("profile1");

		try {
			final Profile profile = this.profileService.findOne(profileId);
			Assert.notNull(profile);

			System.out.println("¡Exito!");

		} catch (final Exception e) {
			System.out.println("¡Fallo," + e.getMessage() + "!");
		}

	}
	@Test
	public void testSave() {
		System.out.println("========== testSave() ==========");
		Profile profile, saved;
		this.authenticate("referee1");
		final Collection<Profile> profiles;
		final int refereeId = this.getEntityId("referee1");
		try {
			profile = this.profileService.create(refereeId);
			profile.setLink("http://profile10.com");
			profile.setName("Antonia");
			profile.setNick("piwflow2");
			saved = this.profileService.save(profile);

			profiles = this.profileService.findAll();
			Assert.isTrue(profiles.contains(saved));
			System.out.println("¡Exito!");

		} catch (final Exception e) {
			System.out.println("¡Fallo," + e.getMessage() + "!");
		}
		this.unauthenticate();
	}

	@Test
	public void testFindAll() {
		System.out.println("========== testFindAll() ==========");
		final int profileId = this.getEntityId("profile1");
		try {
			final Profile profile = this.profileService.findOne(profileId);
			final Collection<Profile> profiles = this.profileService.findAll();
			Assert.isTrue(profiles.contains(profile));
			System.out.println("¡Exito!");

		} catch (final Exception e) {
			System.out.println("¡Fallo," + e.getMessage() + "!");
		}

	}

	@Test
	public void testDelete() {
		System.out.println("========== testDelete() ==========");
		this.authenticate("referee1");
		final int profileId = this.getEntityId("profile1");

		try {
			final Profile profile = this.profileService.findOne(profileId);
			this.profileService.delete(profile);
			final Collection<Profile> profiles = this.profileService.findAll();
			Assert.notNull(profiles);
			Assert.isTrue(!profiles.contains(profile));

			System.out.println("¡Exito!");

		} catch (final Exception e) {
			System.out.println("¡Fallo," + e.getMessage() + "!");
		}
		this.unauthenticate();
	}

}
