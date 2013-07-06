package name.richardson.james.bukkit.jchat.title;

import org.bukkit.entity.Player;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TitleRequestInvalidationEventTest extends TestCase {

	private TitleRequestInvalidationEvent event;

	@Mock
	private Player player;

	@Test
	public void testGetPlayer()
	throws Exception {
		Assert.assertEquals(player, event.getPlayer());

	}

	@Test
	public void testGetHandlers()
	throws Exception {
		Assert.assertNotNull(event.getHandlers());
	}

	@Before
	public void setUp()
	throws Exception {
		event = new TitleRequestInvalidationEvent(player);
	}
}
