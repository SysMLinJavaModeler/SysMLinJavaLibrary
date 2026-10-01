package sysmlinjavalibrary.components.services.messaging;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import sysmlinjava.attributetypes.KeyValueMap;
import sysmlinjava.attributetypes.ThroughputQuantityPerSecond;
import sysmlinjava.javaannotations.actions.Action;
import sysmlinjava.javaannotations.attributes.Attribute;
import sysmlinjava.parts.SysMLPart;
import sysmlinjavalibrary.common.messages.Message;
import sysmlinjavalibrary.common.ports.information.MessagingProtocol;

public abstract class MessagingServices extends SysMLPart
{
	@Attribute
	protected ThroughputQuantityPerSecond messageThroughput;
	@Attribute
	protected KeyValueMap<Class<? extends Message>, List<MessagingProtocol>> messageToMessagingPortsMap;

	public MessagingServices(String name, long id)
	{
		super(name, id);
		createMessageToMessagingPortsMap();
	}

	@Action
	public void routeMessage(Message message)
	{
		message.publishedTime = Instant.now();
		StringBuilder logString = new StringBuilder();
		logString.append(String.format("Message type: %s, ports=[", message.identityString()));
		Class<? extends Message> messageClass = message.getClass();
		List<MessagingProtocol> ports = messageToMessagingPortsMap.get(messageClass);
		if (ports != null && !ports.isEmpty())
		{
			for (MessagingProtocol port : ports)
			{
				port.transmit(message);
				logString.append(String.format("%s ", port.identityString()));
			}
			logString.append("]");
			logger.info(logString.toString());
		}
		else
			logger.warning("no subscriber ports for message class \"" + message.getClass().getSimpleName() + "\"");
	}

	@Override
	protected void createStateMachine()
	{
		stateMachine = Optional.of(new MessagingServicesStateMachine(this, "MessagingServicesStateMachine"));
	}

	@Override
	protected void createAttributes()
	{
		super.createAttributes();
		messageThroughput = new ThroughputQuantityPerSecond(100);
		messageToMessagingPortsMap = new KeyValueMap<>();
	}

	@Override
	protected void createPorts()
	{
		super.createPorts();
	}

	protected abstract void createMessageToMessagingPortsMap();
}
