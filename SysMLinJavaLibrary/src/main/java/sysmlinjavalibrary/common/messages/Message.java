package sysmlinjavalibrary.common.messages;

import java.time.Instant;
import java.util.Optional;

import sysmlinjava.common.SysMLAnything;
import sysmlinjava.javaannotations.attributes.Attribute;
import sysmlinjava.views.common.StackedProtocolObject;

/**
 * The {@code Message} is the SysMLinJava model of a message that is used by a
 * messaging protocol to communicate arbitrary information between message
 * publishers and subscribers. This {@code Message} class should be extended for
 * specialized messages containing specialized objects. The {@code Message} also
 * implements the {@code StackedProtocolObject} interface.
 * 
 * @author ModelerOne
 *
 */
public class Message extends SysMLAnything implements StackedProtocolObject
{
	@Attribute
	public Instant publishedTime;

	@Override
	public String stackNamesString()
	{
		return stackNamesString(this, Optional.empty());
	}

	@Override
	public String toString()
	{
		return String.format("Message [publishedTime=%s]", publishedTime);
	}
}
