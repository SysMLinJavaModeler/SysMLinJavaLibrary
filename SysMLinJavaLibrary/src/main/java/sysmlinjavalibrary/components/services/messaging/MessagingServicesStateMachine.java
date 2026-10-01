package sysmlinjavalibrary.components.services.messaging;

import sysmlinjava.events.SysMLSignalEvent;
import sysmlinjavalibrary.common.messages.Message;
import sysmlinjavalibrary.common.signals.MessageSignal;
import sysmlinjavalibrary.components.services.common.MicroserviceStateMachine;

public class MessagingServicesStateMachine extends MicroserviceStateMachine
{
	public MessagingServicesStateMachine(MessagingServices messagingServices, String name)
	{
		super(messagingServices, name);
	}

	@Override
	public void createEffectActionFunctions()
	{
		onMessageEffectActivity = (event, contextBlock) ->
		{
			logger.info(event.toString());
			Message message = ((MessageSignal)((SysMLSignalEvent)event.get()).signal).message;
			MessagingServices service = (MessagingServices)contextBlock.get();
			service.routeMessage(message);
		};
	}
}
