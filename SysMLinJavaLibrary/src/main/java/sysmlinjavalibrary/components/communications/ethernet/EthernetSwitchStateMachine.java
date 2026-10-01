package sysmlinjavalibrary.components.communications.ethernet;

import java.util.Optional;
import sysmlinjava.attributetypes.ElectricalPower;
import sysmlinjava.attributetypes.RReal;
import sysmlinjava.events.SysMLSignalEvent;
import sysmlinjava.events.SysMLTimeEvent;
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
import sysmlinjavalibrary.common.objects.information.SNMPRequest;
import sysmlinjavalibrary.common.signals.ElectricalPowerSignal;
import sysmlinjavalibrary.common.signals.EthernetPacketSignal;
import sysmlinjavalibrary.common.signals.OnOffSwitchSignal;
import sysmlinjavalibrary.common.signals.SNMPRequestSignal;
import sysmlinjavalibrary.components.communications.common.objects.EthernetSwitchStatesEnum;

public class EthernetSwitchStateMachine extends SysMLStateMachine
{
	@State
	private SysMLState powerOffState;
	@State
	private SysMLState initializingState;
	@State
	private SysMLState operationalState;

	@Transition
	private InitialTransition initialToPowerOffTransition;
	@Transition
	private SysMLTransition powerOffOnPowerSwitchedOnTransition;
	@Transition
	private SysMLTransition powerOffToInitializingTransition;
	@Transition
	private SysMLTransition initializingToOperationalTransition;
	@Transition
	private SysMLTransition operationalOnPacketTransition;
	@Transition
	private SysMLTransition operationalOnControlTransition;
	@Transition
	private SysMLTransition operationalOnControlToPowerOffTransition;
	@Transition
	private SysMLTransition operationalOnPowerSwitchedOffTransition;
	@Transition
	private SysMLTransition operationalToPowerOffTransition;
	@Transition
	private SysMLTransition powerOffToFinalTransition;

	@GuardCondition
	private SysMLGuardCondition isSwitchedOnGuardCondition;
	@GuardCondition
	private SysMLGuardCondition isSwitchedOffGuardCondition;
	@GuardCondition
	private SysMLGuardCondition isMinPowerGuardCondition;
	@GuardCondition
	private SysMLGuardCondition isEthernetPacketGuardCondition;
	@GuardCondition
	private SysMLGuardCondition isControlGuardCondition;
	@GuardCondition
	private SysMLGuardCondition isControlToPowerOffGuardCondition;
	@GuardCondition
	private SysMLGuardCondition isZeroPowerGuardCondition;

	@Guard
	private SysMLGuard isSwitchedOnGuard;
	@Guard
	private SysMLGuard isSwitchedOffGuard;
	@Guard
	private SysMLGuard isMinPowerGuard;
	@Guard
	private SysMLGuard isEthernetPacketGuard;
	@Guard
	private SysMLGuard isControlGuard;
	@Guard
	private SysMLGuard isControlToPowerOffGuard;
	@Guard
	private SysMLGuard isZeroPowerGuard;

	@EffectActionFunction
	private SysMLEffectActionFunction initialToPowerOffTransitionEffectActivity;
	@EffectActionFunction
	private SysMLEffectActionFunction powerOffOnPowerSwitchedOnTransitionEffectActivity;
	@EffectActionFunction
	private SysMLEffectActionFunction powerOffToInitializingTransitionEffectActivity;
	@EffectActionFunction
	private SysMLEffectActionFunction operationalOnControlTransitionEffectActivity;
	@EffectActionFunction
	private SysMLEffectActionFunction operationalOnPacketTransitionEffectActivity;
	@EffectActionFunction
	private SysMLEffectActionFunction operationalOnControlToPowerOffTransitionEffectActivity;
	@EffectActionFunction
	private SysMLEffectActionFunction operationalOnPowerSwitchedOffTransitionEffectActivity;
	@EffectActionFunction
	private SysMLEffectActionFunction operationalToPowerOffTransitionEffectActivity;

	@Effect
	private SysMLEffect initialToPowerOffTransitionEffect;
	@Effect
	private SysMLEffect powerOffOnPowerSwitchedOnTransitionEffect;
	@Effect
	private SysMLEffect powerOffToInitializingTransitionEffect;
	@Effect
	private SysMLEffect operationalOnPacketTransitionEffect;
	@Effect
	private SysMLEffect operationalOnControlTransitionEffect;
	@Effect
	public SysMLEffect operationalOnControlToPowerOffTransitionEffect;
	@Effect
	public SysMLEffect operationalOnPowerSwitchedOffTransitionEffect;
	@Effect
	private SysMLEffect operationalToPowerOffTransitionEffect;

	public EthernetSwitchStateMachine(EthernetSwitch ethernetSwitch)
	{
		super(Optional.of(ethernetSwitch), true, "EthernetSwitchStateMachine");
	}

	@Override
	protected void createStates()
	{
		powerOffState = new SysMLState(context, Optional.empty(), Optional.empty(), Optional.empty(), "initializing");
		initializingState = new SysMLState(context, Optional.empty(), Optional.empty(), Optional.empty(), "initializing");
		operationalState = new SysMLState(context, Optional.empty(), Optional.empty(), Optional.empty(), "operational");
	}

	@Override
	protected void createGuardConditions()
	{
		super.createGuardConditions();
		isSwitchedOnGuardCondition = (event, contextBlock) ->
		{
			return event.isPresent() &&
				event.get() instanceof SysMLSignalEvent &&
				((SysMLSignalEvent)event.get()).signal instanceof OnOffSwitchSignal &&
				((OnOffSwitchSignal)((SysMLSignalEvent)event.get()).signal).onOffSwitch.isOn;
		};
		isSwitchedOffGuardCondition = (event, contextBlock) ->
		{
			return event.isPresent() &&
				event.get() instanceof SysMLSignalEvent &&
				((SysMLSignalEvent)event.get()).signal instanceof OnOffSwitchSignal &&
				!((OnOffSwitchSignal)((SysMLSignalEvent)event.get()).signal).onOffSwitch.isOn;
		};
		isMinPowerGuardCondition = (event, contextBlock) ->
		{
			EthernetSwitch ethernetSwitch = (EthernetSwitch)contextBlock.get();
			return event.isPresent() &&
				event.get() instanceof SysMLSignalEvent &&
				((SysMLSignalEvent)event.get()).signal instanceof ElectricalPowerSignal &&
				((ElectricalPowerSignal)((SysMLSignalEvent)event.get()).signal).power.current.greaterThan(ethernetSwitch.minPowerIn);
		};
		isControlToPowerOffGuardCondition = (event, contextBlock) ->
		{
			boolean result = false;
			if(event.isPresent() &&
				event.get() instanceof SysMLSignalEvent &&
				((SysMLSignalEvent)event.get()).signal instanceof SNMPRequestSignal)
			{
				SNMPRequest request = ((SNMPRequestSignal)((SysMLSignalEvent)event.get()).signal).request;
				EthernetSwitchStatesEnum state = EthernetSwitchStatesEnum.valueOf(request.mib.getDataStrings().get(1));
				if (state == EthernetSwitchStatesEnum.PowerOff)
					result = true;
			}
			return result;
		};
		isZeroPowerGuardCondition = (event, contextBlock) ->
		{
			return event.isPresent() &&
				event.get() instanceof SysMLSignalEvent &&
				((SysMLSignalEvent)event.get()).signal instanceof ElectricalPowerSignal &&
				((ElectricalPowerSignal)((SysMLSignalEvent)event.get()).signal).power.watts().lessThan(RReal.of(1));
		};
		isEthernetPacketGuardCondition = (event, contextBlock) ->
		{ 
			return event.isPresent() &&
				event.get() instanceof SysMLSignalEvent &&
				((SysMLSignalEvent)event.get()).signal instanceof EthernetPacketSignal;
		};
		isControlGuardCondition = (event, contextBlock) ->
		{ 
			return event.isPresent() &&
				event.get() instanceof SysMLSignalEvent &&
				((SysMLSignalEvent)event.get()).signal instanceof SNMPRequestSignal;
		};
	}

	@Override
	protected void createGuards()
	{
		super.createGuards();
		isSwitchedOnGuard = new SysMLGuard(context, isSwitchedOnGuardCondition, "isSwitchedOn");
		isSwitchedOffGuard = new SysMLGuard(context, isSwitchedOffGuardCondition, "isSwitchedOff");
		isMinPowerGuard = new SysMLGuard(context, isMinPowerGuardCondition, "isMinPower");
		isZeroPowerGuard = new SysMLGuard(context, isZeroPowerGuardCondition, "isZeroPower");
		isControlToPowerOffGuard = new SysMLGuard(context, isControlToPowerOffGuardCondition, "isControlToPowerOff");
		isEthernetPacketGuard =new SysMLGuard(context, isEthernetPacketGuardCondition, "isEthernetPacket");
		isControlGuard = new SysMLGuard(context, isControlGuardCondition, "isControl");
	}

	@Override
	protected void createEffectActionFunctions()
	{
		super.createEffectActionFunctions();
		initialToPowerOffTransitionEffectActivity = (event, contextBlock) ->
		{
			EthernetSwitch system = (EthernetSwitch)contextBlock.get();
			system.initialize();
		};
		powerOffOnPowerSwitchedOnTransitionEffectActivity = (event, contextBlock) ->
		{
			EthernetSwitch system = (EthernetSwitch)contextBlock.get();
			system.onSwitchToPowerOn();
		};
		powerOffToInitializingTransitionEffectActivity = (event, contextBlock) ->
		{
			ElectricalPower power = ((ElectricalPowerSignal)((SysMLSignalEvent)event.get()).signal).power;
			EthernetSwitch system = (EthernetSwitch)contextBlock.get();
			system.onElectricalPowerOn(power);
		};
		operationalOnPacketTransitionEffectActivity = (event, contextBlock) ->
		{
			EthernetPacket packet = ((EthernetPacketSignal)((SysMLSignalEvent)event.get()).signal).packet;
			EthernetSwitch system = (EthernetSwitch)contextBlock.get();
			system.onEthernetPacket(packet);
		};
		operationalOnControlTransitionEffectActivity = (event, contextBlock) ->
		{
			SNMPRequest request = ((SNMPRequestSignal)((SysMLSignalEvent)event.get()).signal).request;
			EthernetSwitch system = (EthernetSwitch)contextBlock.get();
			system.onSNMPRequest(request);
		};
		operationalOnControlToPowerOffTransitionEffectActivity = (event, contextBlock) ->
		{
			EthernetSwitch system = (EthernetSwitch)contextBlock.get();
			system.onSNMPRequestToPowerOff();
		};
		operationalOnPowerSwitchedOffTransitionEffectActivity = (event, contextBlock) ->
		{
			EthernetSwitch system = (EthernetSwitch)contextBlock.get();
			system.onSwitchToPowerOff();
		};
		operationalToPowerOffTransitionEffectActivity = (event, contextBlock) ->
		{
			ElectricalPower power = ((ElectricalPowerSignal)((SysMLSignalEvent)event.get()).signal).power;
			EthernetSwitch system = (EthernetSwitch)contextBlock.get();
			system.onElectricalPowerOff(power);
		};
	}

	@Override
	protected void createEffects()
	{
		super.createEffects();
		initialToPowerOffTransitionEffect = new SysMLEffect(context, initialToPowerOffTransitionEffectActivity, "InitialToPowerOffTransition");
		powerOffOnPowerSwitchedOnTransitionEffect = new SysMLEffect(context, powerOffOnPowerSwitchedOnTransitionEffectActivity, "PowerOffOnPowerSwitchedOnTransition");
		powerOffToInitializingTransitionEffect = new SysMLEffect(context, powerOffToInitializingTransitionEffectActivity, "PowerOffToPoweredOnTransition");
		operationalOnPacketTransitionEffect = new SysMLEffect(context, operationalOnPacketTransitionEffectActivity, "OperationalOnPacketTransition");
		operationalOnControlTransitionEffect = new SysMLEffect(context, operationalOnControlTransitionEffectActivity, "OperationalOnControlTransition");
		operationalOnControlToPowerOffTransitionEffect = new SysMLEffect(context, operationalOnControlToPowerOffTransitionEffectActivity, "OperationalOnControlToPowerOffTransition");
		operationalOnPowerSwitchedOffTransitionEffect = new SysMLEffect(context, operationalOnPowerSwitchedOffTransitionEffectActivity, "OperationalOnPowerSwitchedOffTransition");
		operationalToPowerOffTransitionEffect = new SysMLEffect(context, operationalToPowerOffTransitionEffectActivity, "OperationalToPowerOffTransition");
	}

	@Override
	protected void createTransitions()
	{
		initialToPowerOffTransition = new InitialTransition(context, initialState, powerOffState, "InitialToPowerOff");

		powerOffOnPowerSwitchedOnTransition = new SysMLTransition(context, powerOffState, powerOffState, Optional.of(SysMLSignalEvent.class), Optional.of(isSwitchedOnGuard), Optional.of(powerOffOnPowerSwitchedOnTransitionEffect),
			"PowerOffOnPowerSwitchedOn", SysMLTransitionKind.internal);
		
		powerOffToInitializingTransition = new SysMLTransition(context, powerOffState, initializingState, Optional.of(SysMLSignalEvent.class), Optional.of(isMinPowerGuard),
			Optional.of(powerOffToInitializingTransitionEffect), "PowerOffToInitializing", SysMLTransitionKind.external);
		
		initializingToOperationalTransition = new SysMLTransition(context, initializingState, operationalState, Optional.of(SysMLTimeEvent.class), Optional.empty(), Optional.empty(), "InitializingToOperational",
			SysMLTransitionKind.external);
		
		operationalOnPacketTransition = new SysMLTransition(context, operationalState, operationalState, Optional.of(SysMLSignalEvent.class), Optional.of(isEthernetPacketGuard), Optional.of(operationalOnPacketTransitionEffect), "OperationalOnPacket",
			SysMLTransitionKind.internal);
		
		operationalOnControlToPowerOffTransition = new SysMLTransition(context, operationalState, operationalState, Optional.of(SysMLSignalEvent.class), Optional.of(isControlToPowerOffGuard),
			Optional.of(operationalOnControlToPowerOffTransitionEffect), "OperationalOnControlToPowerOff", SysMLTransitionKind.internal);
		
		operationalOnPowerSwitchedOffTransition = new SysMLTransition(context, operationalState, operationalState, Optional.of(SysMLSignalEvent.class), Optional.of(isSwitchedOffGuard),
			Optional.of(operationalOnPowerSwitchedOffTransitionEffect), "PowerOffOnPowerSwitchedOn", SysMLTransitionKind.internal);

		operationalOnControlTransition = new SysMLTransition(context, operationalState, operationalState, Optional.of(SysMLSignalEvent.class), Optional.of(isControlGuard), Optional.of(operationalOnControlTransitionEffect), "OperationalOnControl",
			SysMLTransitionKind.internal);
		
		operationalToPowerOffTransition = new SysMLTransition(context, operationalState, powerOffState, Optional.of(SysMLSignalEvent.class), Optional.of(isZeroPowerGuard), Optional.of(operationalToPowerOffTransitionEffect),
			"OperationalToPowerOff", SysMLTransitionKind.external);
		
		powerOffToFinalTransition = new FinalTransition(context, powerOffState, finalState, "PowerOffToFinal");
	}
}
