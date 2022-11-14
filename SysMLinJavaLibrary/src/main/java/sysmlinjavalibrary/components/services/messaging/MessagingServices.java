package sysmlinjavalibrary.components.services.messaging;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import sysmlinjava.annotations.Reception;
import sysmlinjava.annotations.Value;
import sysmlinjava.blocks.SysMLBlock;
import sysmlinjava.valuetypes.KeyValueMap;
import sysmlinjava.valuetypes.ThroughputQuantityPerSecond;
import sysmlinjavalibrary.common.messages.Message;
import sysmlinjavalibrary.common.ports.information.MessagingProtocol;

public abstract class MessagingServices extends SysMLBlock
{
	@Value
	protected ThroughputQuantityPerSecond messageThroughput;
	@Value
	protected KeyValueMap<Class<? extends Message>, List<MessagingProtocol>> messageToMessagingPortsMap;

	public MessagingServices(String name, long id)
	{
		super(name, id);
		createMessageToMessagingPortsMap();
	}

	@Reception
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
	protected void createValues()
	{
		super.createValues();
		messageThroughput = new ThroughputQuantityPerSecond(100);
		messageToMessagingPortsMap = new KeyValueMap<>();
	}

	@Override
	protected void createFullPorts()
	{
		super.createFullPorts();
	}

	protected abstract void createMessageToMessagingPortsMap();
}
