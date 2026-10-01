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
import sysmlinjavalibrary.common.objects.information.EthernetPacket;
import sysmlinjavalibrary.common.signals.EthernetPacketSignal;

public class EthernetProtocolStateMachine extends SysMLStateMachine
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
	private SysMLTransition operationalOnEthernetPacketTransition;
	@Transition
	private SysMLTransition operationalToFinalTransition;

	@GuardCondition
	private SysMLGuardCondition isEthernetPacketGuardCondition;

	@Guard
	private SysMLGuard isEthernetPacketGuard;

	@EffectActionFunction
	private SysMLEffectActionFunction onEthernetPacketEvent;

	@Effect
	private SysMLEffect onEthernetPacketEventEffect;

	public EthernetProtocolStateMachine(EthernetProtocol ethernetPort)
	{
		super(Optional.of(ethernetPort), true, "EthernetPortStateMachine");
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
		isEthernetPacketGuardCondition = (event, contextBlock) ->
		{
			return event.isPresent() && event.get() instanceof SysMLSignalEvent && ((SysMLSignalEvent)event.get()).signal instanceof EthernetPacketSignal;
		};
	}

	@Override
	protected void createGuards()
	{
		isEthernetPacketGuard = new SysMLGuard(context, isEthernetPacketGuardCondition, "isEthernetPacket");
	}

	@Override
	protected void createEffectActionFunctions()
	{
		super.createEffectActionFunctions();
		onEthernetPacketEvent = (event, contextBlock) ->
		{
			EthernetPacket packet = ((EthernetPacketSignal)((SysMLSignalEvent)event.get()).signal).packet;
			EthernetProtocol ethernetPort = (EthernetProtocol)contextBlock.get();
			ethernetPort.onEthernetPacketReceived(packet);
		};
	}

	@Override
	protected void createEffects()
	{
		super.createEffects();
		onEthernetPacketEventEffect = new SysMLEffect(context, onEthernetPacketEvent, "onEthernetPacket");
	}

	@Override
	protected void createTransitions()
	{
		initialToInitializingTransition = new InitialTransition(context, initialState, initializingState, "IntialToInitializing");
		initializingToOperationalTransition = new SysMLTransition(context, initializingState, operationalState, "InitializingToOperational");
		operationalOnEthernetPacketTransition = new SysMLTransition(context, operationalState, operationalState, Optional.of(SysMLSignalEvent.class), Optional.of(isEthernetPacketGuard), Optional.of(onEthernetPacketEventEffect),
			"OperationalOnEthernetPacket", SysMLTransitionKind.internal);
		operationalToFinalTransition = new FinalTransition(context, operationalState, finalState, "OperationalToFinal");
	}

}