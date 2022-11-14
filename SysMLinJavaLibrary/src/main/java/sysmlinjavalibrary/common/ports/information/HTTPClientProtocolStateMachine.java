package sysmlinjavalibrary.common.ports.information;

import java.util.Optional;
import sysmlinjava.annotations.statemachines.Effect;
import sysmlinjava.annotations.statemachines.EffectActivity;
import sysmlinjava.annotations.statemachines.State;
import sysmlinjava.annotations.statemachines.Transition;
import sysmlinjava.statemachine.FinalTransition;
import sysmlinjava.statemachine.InitialTransition;
import sysmlinjava.statemachine.SysMLEffect;
import sysmlinjava.statemachine.SysMLEffectActivity;
import sysmlinjava.statemachine.SysMLState;
import sysmlinjava.statemachine.SysMLStateMachine;
import sysmlinjava.statemachine.SysMLTransition;
import sysmlinjava.statemachine.SysMLTransitionKind;
import sysmlinjavalibrary.common.events.HTTPResponseSignalEvent;
import sysmlinjavalibrary.common.objects.information.HTTPResponse;
import sysmlinjavalibrary.common.signals.HTTPResponseSignal;

/**
 * State machine for the HTTP Client Protocol enabling the protocol to operate
 * asynchronously from the thread that synchronously submits/sends HTTP Requests
 * and receives HTTP Responses. States include an initialization and operational
 * states with the most significant transition being the one that occurs upon
 * occurence of a signal event for a received HTTP response. See the state
 * machine declaration below for details.
 * 
 * @author ModelerOne
 *
 */
public class HTTPClientProtocolStateMachine extends SysMLStateMachine
{
	@State
	private SysMLState initializingState;
	@State
	private SysMLState operationalState;

	@Transition
	private InitialTransition initialToInitializingTransition;
	@Transition
	private SysMLTransition initializingToOperationalTransition;
	@Transition
	private SysMLTransition operationalOnHTTPResponseTransition;
	@Transition
	private FinalTransition operationalToFinalTransition;

	@EffectActivity
	private SysMLEffectActivity onHTTPResponseEvent;
	@Effect
	private SysMLEffect onHTTPResponseEventEffect;

	public HTTPClientProtocolStateMachine(HTTPClientProtocol httpClient)
	{
		super(Optional.of(httpClient), true, "HTTPClientProtocolStateMachine");
	}

	@Override
	protected void createStates()
	{
		super.createStates();
		initializingState = new SysMLState(contextBlock, "Initializing");
		operationalState = new SysMLState(contextBlock, "Operational");
	}

	@Override
	protected void createEffectActivities()
	{
		super.createEffectActivities();
		onHTTPResponseEvent = (event, contextBlock) ->
		{
			if (event.get() instanceof HTTPResponseSignalEvent )
			{
				HTTPResponseSignalEvent httpResponseSignalEvent = (HTTPResponseSignalEvent)event.get();
				HTTPResponse response = ((HTTPResponseSignal)httpResponseSignalEvent.signal).response;
				HTTPClientProtocol httpClient = (HTTPClientProtocol)contextBlock.get();
				httpClient.onHTTPResponse(response);
			}
			else
				logger.warning("unexpected event type: " + event.get().getClass().getSimpleName());
		};
	}

	@Override
	protected void createEffects()
	{
		super.createEffects();
		onHTTPResponseEventEffect = new SysMLEffect(contextBlock, onHTTPResponseEvent, "onHTTPResponse");
	}

	@Override
	protected void createTransitions()
	{
		initialToInitializingTransition = new InitialTransition(contextBlock, initialState, initializingState, "IntialToInitializing");
		initializingToOperationalTransition = new SysMLTransition(contextBlock, initializingState, operationalState, "InitializingToOperational");
		operationalOnHTTPResponseTransition = new SysMLTransition(contextBlock, operationalState, operationalState, Optional.of(HTTPResponseSignalEvent.class), Optional.empty(), Optional.of(onHTTPResponseEventEffect),
			"OperationalOnHTTPResponse", SysMLTransitionKind.internal);
		operationalToFinalTransition = new FinalTransition(contextBlock, operationalState, finalState, "OperationalToFinal");
	}
}