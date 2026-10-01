package sysmlinjavalibrary.components.services.common;

import java.util.Optional;

import sysmlinjava.events.SysMLSignalEvent;
import sysmlinjava.javaannotations.statemachines.Effect;
import sysmlinjava.javaannotations.statemachines.EffectActionFunction;
import sysmlinjava.javaannotations.statemachines.Guard;
import sysmlinjava.javaannotations.statemachines.GuardCondition;
import sysmlinjava.javaannotations.statemachines.State;
import sysmlinjava.javaannotations.statemachines.Transition;
import sysmlinjava.parts.SysMLPart;
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
import sysmlinjavalibrary.common.signals.MessageSignal;

public abstract class MicroserviceStateMachine extends SysMLStateMachine
{
	@State
	protected SysMLState initializingState;
	@State
	protected SysMLState operationalState;

	@GuardCondition
	protected SysMLGuardCondition isMessageGuardCondition;

	@Guard
	protected SysMLGuard isMessageGuard;

	@Transition
	protected InitialTransition initialToInitializingTransition;
	@Transition
	protected SysMLTransition initializingToOperationalTransition;
	@Transition
	protected SysMLTransition operationalOnMessageTransition;
	@Transition
	protected SysMLTransition operationalToFinalTransition;

	@EffectActionFunction
	protected SysMLEffectActionFunction onMessageEffectActivity;

	@Effect
	protected SysMLEffect onMessageEffect;

	public MicroserviceStateMachine(SysMLPart microservice, String name)
	{
		super(Optional.of(microservice), true, name);
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
		isMessageGuardCondition = (event, contextBlock) ->
		{
			return event.isPresent() &&
			event.get() instanceof SysMLSignalEvent signalEvent &&
			signalEvent.signal instanceof MessageSignal;
		};
	}

	@Override
	protected void createGuards()
	{
		isMessageGuard = new SysMLGuard(context, isMessageGuardCondition, "isMessage");
	}

	@Override
	protected abstract void createEffectActionFunctions();

	@Override
	protected void createEffects()
	{
		super.createEffects();
		onMessageEffect = new SysMLEffect(context, onMessageEffectActivity, "onMessage");
	}

	@Override
	protected void createTransitions()
	{
		initialToInitializingTransition = new InitialTransition(context, initialState, initializingState, "InitialToInitializing");
		initializingToOperationalTransition = new SysMLTransition(context, initializingState, operationalState, "InitializingToOperational");
		operationalOnMessageTransition = new SysMLTransition(context, operationalState, operationalState, Optional.of(SysMLSignalEvent.class), Optional.of(isMessageGuard), Optional.of(onMessageEffect), "OperationalOnMessage", SysMLTransitionKind.internal);
		operationalToFinalTransition = new FinalTransition(context, operationalState, finalState, "OperationalToFinal");
	}
}
