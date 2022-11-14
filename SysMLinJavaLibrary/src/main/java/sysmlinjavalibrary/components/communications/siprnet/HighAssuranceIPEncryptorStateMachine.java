package sysmlinjavalibrary.components.communications.siprnet;

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
import sysmlinjavalibrary.common.events.EthernetPacketEvent;
import sysmlinjavalibrary.common.events.IPPacketEvent;
import sysmlinjavalibrary.common.objects.information.IPPacket;

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

	@EffectActivity
	public SysMLEffectActivity operationalOnPlainTextPacketTransitionEffectActivity;
	@EffectActivity
	public SysMLEffectActivity operationalOnEncryptedPacketTransitionEffectActivity;
	
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
		initializingState = new SysMLState(contextBlock, "Initializing");
		operationalState = new SysMLState(contextBlock, "Operational");
	}

	@Override
	protected void createEffectActivities()
	{
		super.createEffectActivities();
		operationalOnPlainTextPacketTransitionEffectActivity = (event, contextBlock) ->
		{
			IPPacket packet = ((IPPacketEvent)event.get()).getPacket();
			HighAssuranceIPEncryptor haipe = (HighAssuranceIPEncryptor)contextBlock.get(); 
			haipe.onDecryptedPacket(packet);
	};
		operationalOnEncryptedPacketTransitionEffectActivity = (event, contextBlock) ->
		{
			IPPacket packet = ((IPPacketEvent)event.get()).getPacket();
			HighAssuranceIPEncryptor haipe = (HighAssuranceIPEncryptor)contextBlock.get(); 
			haipe.onEncryptedPacket(packet);
		};
	}

	@Override
	protected void createEffects()
	{
		super.createEffects();
		operationalOnPlainTextPacketTransitionEffect = new SysMLEffect(contextBlock, operationalOnPlainTextPacketTransitionEffectActivity, "operationalOnPlainTextPacket");
		operationalOnEncryptedPacketTransitionEffect = new SysMLEffect(contextBlock, operationalOnEncryptedPacketTransitionEffectActivity, "operationalOnEncryptedPacket");
	}

	@Override
	protected void createTransitions()
	{
		initialToInitializingTransition = new InitialTransition(contextBlock, initialState, initializingState, "InitialToInitializing");
		initializingToOperationalTransition = new SysMLTransition(contextBlock, initializingState, operationalState, "InitializingToOperational");
		operationalOnPlainTextPacketTransition = new SysMLTransition(contextBlock, operationalState, operationalState, Optional.of(EthernetPacketEvent.class), Optional.empty(), Optional.of(operationalOnPlainTextPacketTransitionEffect), "OperationalOnPlainTextPacket",
			SysMLTransitionKind.internal);
		operationalOnEncryptedPacketTransition = new SysMLTransition(contextBlock, operationalState, operationalState, Optional.of(EthernetPacketEvent.class), Optional.empty(), Optional.of(operationalOnEncryptedPacketTransitionEffect), "OperationalOnEncryptedPacket",
			SysMLTransitionKind.internal);
		operationalToFinalTransition = new FinalTransition(contextBlock, operationalState, finalState, "operationalToFinal");
	}
}
