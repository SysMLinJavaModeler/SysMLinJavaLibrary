package sysmlinjavalibrary.components.services.messaging;

import sysmlinjavalibrary.common.events.MessageEvent;
import sysmlinjavalibrary.common.messages.Message;
import sysmlinjavalibrary.components.services.common.MicroserviceStateMachine;

public class MessagingServicesStateMachine extends MicroserviceStateMachine
{
	public MessagingServicesStateMachine(MessagingServices messagingServices, String name)
	{
		super(messagingServices, name);
	}

	@Override
	public void createEffectActivities()
	{
		onMessageEffectActivity = (event, contextBlock) ->
		{
			logger.info(event.toString());
			Message message = ((MessageEvent)event.get()).getMessage();
			MessagingServices service = (MessagingServices)contextBlock.get();
			System.out.println(getClass().getSimpleName() + ".createEffectAct(): message=" + message.getClass().getSimpleName());			
			service.routeMessage(message);
		};
	}
}
