package sysmlinjavalibrary.components.communications.siprnet;

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
import sysmlinjavalibrary.common.objects.information.IPPacket;
import sysmlinjavalibrary.common.signals.IPPacketSignal;

public class HighAssuranceIPEncryptorStateMachine extends SysMLStateMachine
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
	private SysMLTransition operationalOnPlainTextPacketTransition;
	@Transition
	private SysMLTransition operationalOnEncryptedPacketTransition;
	@Transition
	private FinalTransition operationalToFinalTransition;

	@GuardCondition
	private SysMLGuardCondition isPlainTextGuardCondition;
	@GuardCondition
	private SysMLGuardCondition isEncryptedGuardCondition;

	@Guard
	private SysMLGuard isPlainTextGuard;
	@Guard
	private SysMLGuard isEncryptedGuard;

	@EffectActionFunction
	public SysMLEffectActionFunction operationalOnPlainTextPacketTransitionEffectActivity;
	@EffectActionFunction
	public SysMLEffectActionFunction operationalOnEncryptedPacketTransitionEffectActivity;

	@Effect
	public SysMLEffect operationalOnPlainTextPacketTransitionEffect;
	@Effect
	public SysMLEffect operationalOnEncryptedPacketTransitionEffect;

	public HighAssuranceIPEncryptorStateMachine(HighAssuranceIPEncryptor haipe)
	{
		super(Optional.of(haipe), true, "HighAssuranceIPEncryptorStateMachine");
		createStates();
		createTransitions();
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
		super.createGuardConditions();
		isPlainTextGuardCondition = (event, contextBlock) ->
		{
			return ((SysMLSignalEvent)event.get()).signal instanceof IPPacketSignal &&
				!((IPPacketSignal)((SysMLSignalEvent)event.get()).signal).packet.isEncrypted;
		};
		isEncryptedGuardCondition = (event, contextBlock) ->
		{
			return ((SysMLSignalEvent)event.get()).signal instanceof IPPacketSignal &&
				((IPPacketSignal)((SysMLSignalEvent)event.get()).signal).packet.isEncrypted;
		};
	}

	@Override
	protected void createGuards()
	{
		super.createGuards();
		isPlainTextGuard = new SysMLGuard(context, isPlainTextGuardCondition, "isPlainText");
		isEncryptedGuard = new SysMLGuard(context, isEncryptedGuardCondition, "isEncrypted");
	}

	@Override
	protected void createEffectActionFunctions()
	{
		super.createEffectActionFunctions();
		operationalOnPlainTextPacketTransitionEffectActivity = (event, contextBlock) ->
		{
			IPPacket packet = ((IPPacketSignal)((SysMLSignalEvent)event.get()).signal).packet;
			HighAssuranceIPEncryptor haipe = (HighAssuranceIPEncryptor)contextBlock.get();
			haipe.onDecryptedPacket(packet);
		};
		operationalOnEncryptedPacketTransitionEffectActivity = (event, contextBlock) ->
		{
			IPPacket packet = ((IPPacketSignal)((SysMLSignalEvent)event.get()).signal).packet;
			HighAssuranceIPEncryptor haipe = (HighAssuranceIPEncryptor)contextBlock.get();
			haipe.onEncryptedPacket(packet);
		};
	}

	@Override
	protected void createEffects()
	{
		super.createEffects();
		operationalOnPlainTextPacketTransitionEffect = new SysMLEffect(context, operationalOnPlainTextPacketTransitionEffectActivity, "operationalOnPlainTextPacket");
		operationalOnEncryptedPacketTransitionEffect = new SysMLEffect(context, operationalOnEncryptedPacketTransitionEffectActivity, "operationalOnEncryptedPacket");
	}

	@Override
	protected void createTransitions()
	{
		initialToInitializingTransition = new InitialTransition(context, initialState, initializingState, "InitialToInitializing");
		initializingToOperationalTransition = new SysMLTransition(context, initializingState, operationalState, "InitializingToOperational");
		operationalOnPlainTextPacketTransition = new SysMLTransition(context, operationalState, operationalState, Optional.of(SysMLSignalEvent.class), Optional.of(isPlainTextGuard),
			Optional.of(operationalOnPlainTextPacketTransitionEffect), "OperationalOnPlainTextPacket", SysMLTransitionKind.internal);
		operationalOnEncryptedPacketTransition = new SysMLTransition(context, operationalState, operationalState, Optional.of(SysMLSignalEvent.class), Optional.of(isEncryptedGuard),
			Optional.of(operationalOnEncryptedPacketTransitionEffect), "OperationalOnEncryptedPacket", SysMLTransitionKind.internal);
		operationalToFinalTransition = new FinalTransition(context, operationalState, finalState, "operationalToFinal");
	}
}
