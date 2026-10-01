package sysmlinjavalibrary.common.ports.information;

import java.util.Optional;
import sysmlinjava.events.SysMLSignalEvent;
import sysmlinjava.javaannotations.statemachines.Effect;
import sysmlinjava.javaannotations.statemachines.EffectActionFunction;
import sysmlinjava.javaannotations.statemachines.Guard;
import sysmlinjava.javaannotations.statemachines.GuardCondition;
import sysmlinjava.javaannotations.statemachines.State;
import sysmlinjava.javaannotations.statemachines.Transition;
import sysmlinjava.states.FinalTransition;
import sysmlinjava.states.InitialTransition;
import sysmlinjava.states.SysMLEffect;
import sysmlinjava.states.SysMLEffectActionFunction;
import sysmlinjava.states.SysMLGuard;
import sysmlinjava.states.SysMLGuardCondition;
import sysmlinjava.states.SysMLState;
import sysmlinjava.states.SysMLStateMachine;
import sysmlinjava.states.SysMLTransition;
import sysmlinjava.states.SysMLTransitionKind;
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

	@GuardCondition
	private SysMLGuardCondition isHTTPResponseGuardCondition;
	
	@Guard
	private SysMLGuard isHTTPResponseGuard;
	
	@EffectActionFunction
	private SysMLEffectActionFunction onHTTPResponseEvent;
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
		initializingState = new SysMLState(context, "Initializing");
		operationalState = new SysMLState(context, "Operational");
	}

	@Override
	protected void createGuardConditions()
	{
		isHTTPResponseGuardCondition = (event, contextBlock) ->
		{
			return event.isPresent() && event.get() instanceof SysMLSignalEvent &&
				((SysMLSignalEvent)event.get()).signal instanceof HTTPResponseSignal;
		};
	}

	@Override
	protected void createGuards()
	{
		isHTTPResponseGuard = new SysMLGuard(context, isHTTPResponseGuardCondition, "isHTTPResponse");
	}

	@Override
	protected void createEffectActionFunctions()
	{
		super.createEffectActionFunctions();
		onHTTPResponseEvent = (event, contextBlock) ->
		{
			if (event.get() instanceof SysMLSignalEvent)
			{
				SysMLSignalEvent httpResponseSignalEvent = (SysMLSignalEvent)event.get();
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
		onHTTPResponseEventEffect = new SysMLEffect(context, onHTTPResponseEvent, "onHTTPResponse");
	}

	@Override
	protected void createTransitions()
	{
		initialToInitializingTransition = new InitialTransition(context, initialState, initializingState, "IntialToInitializing");
		initializingToOperationalTransition = new SysMLTransition(context, initializingState, operationalState, "InitializingToOperational");
		operationalOnHTTPResponseTransition = new SysMLTransition(context, operationalState, operationalState, Optional.of(SysMLSignalEvent.class), Optional.of(isHTTPResponseGuard), Optional.of(onHTTPResponseEventEffect),
			"OperationalOnHTTPResponse", SysMLTransitionKind.internal);
		operationalToFinalTransition = new FinalTransition(context, operationalState, finalState, "OperationalToFinal");
	}
}