package sysmlinjavalibrary.components.services.common;

import java.util.Optional;
import sysmlinjava.annotations.statemachines.Effect;
import sysmlinjava.annotations.statemachines.EffectActivity;
import sysmlinjava.annotations.statemachines.State;
import sysmlinjava.annotations.statemachines.Transition;
import sysmlinjava.blocks.SysMLBlock;
import sysmlinjava.statemachine.FinalTransition;
import sysmlinjava.statemachine.InitialTransition;
import sysmlinjava.statemachine.SysMLEffect;
import sysmlinjava.statemachine.SysMLEffectActivity;
import sysmlinjava.statemachine.SysMLState;
import sysmlinjava.statemachine.SysMLStateMachine;
import sysmlinjava.statemachine.SysMLTransition;
import sysmlinjava.statemachine.SysMLTransitionKind;
import sysmlinjavalibrary.common.events.MessageEvent;

public abstract class MicroserviceStateMachine extends SysMLStateMachine
{
	@State
	protected SysMLState initializingState;
	@State
	protected SysMLState operationalState;

	@Transition
	protected InitialTransition initialToInitializingTransition;
	@Transition
	protected SysMLTransition initializingToOperationalTransition;
	@Transition
	protected SysMLTransition operationalOnMessageTransition;
	@Transition
	protected SysMLTransition operationalToFinalTransition;

	@EffectActivity
	protected SysMLEffectActivity onMessageEffectActivity;

	@Effect
	protected SysMLEffect onMessageEffect;

	public MicroserviceStateMachine(SysMLBlock microservice, String name)
	{
		super(Optional.of(microservice), true, name);
	}

	@Override
	protected void createStates()
	{
		super.createStates();
		initializingState = new SysMLState(contextBlock, "Initializing");
		operationalState = new SysMLState(contextBlock, "Operational");
	}

	protected abstract void createEffectActivities();
	
	@Override
	protected void createEffects()
	{
		super.createEffects();
		onMessageEffect = new SysMLEffect(contextBlock, onMessageEffectActivity, "onMessage");
	}

	@Override
	protected void createTransitions()
	{
		initialToInitializingTransition = new InitialTransition(contextBlock, initialState, initializingState, "InitialToInitializing");
		initializingToOperationalTransition = new SysMLTransition(contextBlock, initializingState, operationalState, "InitializingToOperational");
		operationalOnMessageTransition = new SysMLTransition(contextBlock, operationalState, operationalState, Optional.of(MessageEvent.class), Optional.empty(), Optional.of(onMessageEffect), "OperationalOnMessage", SysMLTransitionKind.internal);
		operationalToFinalTransition = new FinalTransition(contextBlock, operationalState, finalState, "OperationalToFinal");
	}
}
