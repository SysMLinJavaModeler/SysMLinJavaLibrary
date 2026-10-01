package sysmlinjavalibrary.components.communications.internet;

import java.util.Optional;

import sysmlinjava.attributetypes.ElectricalPower;
import sysmlinjava.attributetypes.RReal;
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
import sysmlinjavalibrary.common.objects.information.SNMPRequest;
import sysmlinjavalibrary.common.signals.ElectricalPowerSignal;
import sysmlinjavalibrary.common.signals.IPPacketSignal;
import sysmlinjavalibrary.common.signals.OnOffSwitchSignal;
import sysmlinjavalibrary.common.signals.SNMPRequestSignal;
import sysmlinjavalibrary.components.communications.common.objects.EthernetSwitchIPRouterStatesEnum;

public class EthernetSwitchIPRouterStateMachine extends SysMLStateMachine
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
	private SysMLTransition operationalOnIPPacketTransition;
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
	private SysMLGuardCondition isControlToPowerOffGuardCondition;
	@GuardCondition
	private SysMLGuardCondition isZeroPowerGuardCondition;
	@GuardCondition
	private SysMLGuardCondition isIPPacketGuardCondition;

	@Guard
	private SysMLGuard isSwitchedOnGuard;
	@Guard
	private SysMLGuard isSwitchedOffGuard;
	@Guard
	private SysMLGuard isMinPowerGuard;
	@Guard
	private SysMLGuard isControlToPowerOffGuard;
	@Guard
	private SysMLGuard isZeroPowerGuard;
	@Guard
	private SysMLGuard isIPPacketGuard;

	@EffectActionFunction
	private SysMLEffectActionFunction initialToPowerOffTransitionEffectActivity;
	@EffectActionFunction
	private SysMLEffectActionFunction powerOffOnPowerSwitchedOnTransitionEffectActivity;
	@EffectActionFunction
	private SysMLEffectActionFunction powerOffToPoweredOnTransitionEffectActivity;
	@EffectActionFunction
	private SysMLEffectActionFunction operationalOnControlTransitionEffectActivity;
	@EffectActionFunction
	private SysMLEffectActionFunction operationalOnIPPacketTransitionEffectActivity;
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
	private SysMLEffect powerOffToPoweredOnTransitionEffect;
	@Effect
	private SysMLEffect operationalOnIPPacketTransitionEffect;
	@Effect
	private SysMLEffect operationalOnControlTransitionEffect;
	@Effect
	public SysMLEffect operationalOnControlToPowerOffTransitionEffect;
	@Effect
	public SysMLEffect operationalOnPowerSwitchedOffTransitionEffect;
	@Effect
	private SysMLEffect operationalToPowerOffTransitionEffect;

	public EthernetSwitchIPRouterStateMachine(EthernetSwitchIPRouter ethernetSwitch)
	{
		super(Optional.of(ethernetSwitch), true, "EthernetSwitchIPRouterStateMachine");
	}

	@Override
	protected void createStates()
	{
		super.createStates();
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
			EthernetSwitchIPRouter switchRouter = (EthernetSwitchIPRouter)contextBlock.get();
			return event.isPresent() &&
			event.get() instanceof SysMLSignalEvent &&
			((SysMLSignalEvent)event.get()).signal instanceof ElectricalPowerSignal &&
			((ElectricalPowerSignal)((SysMLSignalEvent)event.get()).signal).power.current.greaterThan(switchRouter.minCurrentIn);
		};
		isControlToPowerOffGuardCondition = (event, contextBlock) ->
		{
			boolean result = false;
			if(event.isPresent() &&
			event.get() instanceof SysMLSignalEvent &&
			((SysMLSignalEvent)event.get()).signal instanceof SNMPRequestSignal)
			{
				SNMPRequest request = ((SNMPRequestSignal)((SysMLSignalEvent)event.get()).signal).request;
				EthernetSwitchIPRouterStatesEnum state = EthernetSwitchIPRouterStatesEnum.valueOf(request.mib.getDataStrings().get(1));
				if (state == EthernetSwitchIPRouterStatesEnum.PowerOff)
					result = true;
			}
			return result;
		};
		isZeroPowerGuardCondition = (event, contextBlock) ->
		{
			return event.isPresent() &&
			event.get() instanceof SysMLSignalEvent &&
			((SysMLSignalEvent)event.get()).signal instanceof ElectricalPowerSignal &&
			((ElectricalPowerSignal)((SysMLSignalEvent)event.get()).signal).power.watts().lessThanOrEqualTo(RReal.of(1));
		};
		isIPPacketGuardCondition = (event, contextBlock) ->
		{
			return event.isPresent() &&
			event.get() instanceof SysMLSignalEvent &&
			((SysMLSignalEvent)event.get()).signal instanceof IPPacketSignal;
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
		isIPPacketGuard = new SysMLGuard(context, isIPPacketGuardCondition, "isIPPacket");
		isControlToPowerOffGuard = new SysMLGuard(context, isControlToPowerOffGuardCondition, "isControlToPowerOff");
	}

	@Override
	protected void createEffectActionFunctions()
	{
		super.createEffectActionFunctions();
		initialToPowerOffTransitionEffectActivity = (event, contextBlock) ->
		{
			EthernetSwitchIPRouter switchRouter = (EthernetSwitchIPRouter)contextBlock.get();
			switchRouter.initialize();
			System.out.println(this.getClass().getSimpleName() + ".initToPwrOff()");
		};
		powerOffOnPowerSwitchedOnTransitionEffectActivity = (event, contextBlock) ->
		{
			EthernetSwitchIPRouter switchRouter = (EthernetSwitchIPRouter)contextBlock.get();
			switchRouter.onSwitchToPowerOn();
			System.out.println(this.getClass().getSimpleName() + ".powerOffOnPowerSwtchdOn()");
		};
		powerOffToPoweredOnTransitionEffectActivity = (event, contextBlock) ->
		{
			ElectricalPower power = ((ElectricalPowerSignal)((SysMLSignalEvent)event.get()).signal).power;
			EthernetSwitchIPRouter switchRouter = (EthernetSwitchIPRouter)contextBlock.get();
			switchRouter.onElectricalPowerOn(power);
			System.out.println(this.getClass().getSimpleName() + ".powerOffToPoweredOn()");
		};
		operationalOnIPPacketTransitionEffectActivity = (event, contextBlock) ->
		{
			IPPacket packet = ((IPPacketSignal)((SysMLSignalEvent)event.get()).signal).packet;
			EthernetSwitchIPRouter switchRouter = (EthernetSwitchIPRouter)contextBlock.get();
			switchRouter.onIPPacket(packet);
			System.out.println(this.getClass().getSimpleName() + ".oprtnlOnIPPacket()");
		};
		operationalOnControlTransitionEffectActivity = (event, contextBlock) ->
		{
			SNMPRequest request = ((SNMPRequestSignal)((SysMLSignalEvent)event.get()).signal).request;
			EthernetSwitchIPRouter switchRouter = (EthernetSwitchIPRouter)contextBlock.get();
			switchRouter.onSNMPRequest(request);
			System.out.println(this.getClass().getSimpleName() + ".oprtnlOnControl()");
		};
		operationalOnControlToPowerOffTransitionEffectActivity = (event, contextBlock) ->
		{
			EthernetSwitchIPRouter switchRouter = (EthernetSwitchIPRouter)contextBlock.get();
			switchRouter.onSNMPRequestToPowerOff();
			System.out.println(this.getClass().getSimpleName() + ".oprtnlOnControlToPwrOff()");
		};
		operationalOnPowerSwitchedOffTransitionEffectActivity = (event, contextBlock) ->
		{
			EthernetSwitchIPRouter computer = (EthernetSwitchIPRouter)contextBlock.get();
			computer.onSwitchToPowerOff();
			System.out.println(this.getClass().getSimpleName() + ".oprtnlOnPowerSwtchdOff()");
		};
		operationalToPowerOffTransitionEffectActivity = (event, contextBlock) ->
		{
			ElectricalPower power = ((ElectricalPowerSignal)((SysMLSignalEvent)event.get()).signal).power;
			EthernetSwitchIPRouter switchRouter = (EthernetSwitchIPRouter)contextBlock.get();
			switchRouter.onElectricalPowerOff(power);
			System.out.println(this.getClass().getSimpleName() + ".oprtnlToPowerOff()");
		};
	}

	@Override
	protected void createEffects()
	{
		super.createEffects();
		initialToPowerOffTransitionEffect = new SysMLEffect(context, initialToPowerOffTransitionEffectActivity, "InitialToPowerOffTransition");
		powerOffOnPowerSwitchedOnTransitionEffect = new SysMLEffect(context, powerOffOnPowerSwitchedOnTransitionEffectActivity, "PowerOffOnPowerSwitchedOnTransition");
		powerOffToPoweredOnTransitionEffect = new SysMLEffect(context, powerOffToPoweredOnTransitionEffectActivity, "PowerOffToPoweredOnTransition");
		operationalOnIPPacketTransitionEffect = new SysMLEffect(context, operationalOnIPPacketTransitionEffectActivity, "OperationalOnPacketTransition");
		operationalOnControlTransitionEffect = new SysMLEffect(context, operationalOnControlTransitionEffectActivity, "OperationalOnControlTransition");
		operationalOnControlToPowerOffTransitionEffect = new SysMLEffect(context, operationalOnControlToPowerOffTransitionEffectActivity, "OperationalOnControlToPowerOffTransition");
		operationalOnPowerSwitchedOffTransitionEffect = new SysMLEffect(context, operationalOnPowerSwitchedOffTransitionEffectActivity, "OperationalOnPowerSwitchedOffTransition");
		operationalToPowerOffTransitionEffect = new SysMLEffect(context, operationalToPowerOffTransitionEffectActivity, "OperationalToPowerOffTransition");
	}

	@Override
	protected void createTransitions()
	{
		initialToPowerOffTransition = new InitialTransition(context, initialState, powerOffState, initialToPowerOffTransitionEffect, "InitialToPowerOff");

		powerOffOnPowerSwitchedOnTransition = new SysMLTransition(context, powerOffState, powerOffState, Optional.of(SysMLSignalEvent.class), Optional.of(isSwitchedOnGuard), Optional.of(powerOffOnPowerSwitchedOnTransitionEffect),
		"PowerOffOnPowerSwitchedOn", SysMLTransitionKind.internal);

		powerOffToInitializingTransition = new SysMLTransition(context, powerOffState, initializingState, Optional.of(SysMLSignalEvent.class), Optional.of(isMinPowerGuard), Optional.of(powerOffToPoweredOnTransitionEffect),
		"PowerOffToPoweredOn", SysMLTransitionKind.external);

		initializingToOperationalTransition = new SysMLTransition(context, initializingState, operationalState, Optional.empty(), Optional.empty(), Optional.empty(), "InitializingToOperational", SysMLTransitionKind.external);

		operationalOnIPPacketTransition = new SysMLTransition(context, operationalState, operationalState, Optional.of(SysMLSignalEvent.class), Optional.of(isIPPacketGuard), Optional.of(operationalOnIPPacketTransitionEffect),
		"OperationalOnIPPacket", SysMLTransitionKind.internal);

		operationalOnControlToPowerOffTransition = new SysMLTransition(context, operationalState, operationalState, Optional.of(SysMLSignalEvent.class), Optional.of(isControlToPowerOffGuard),
		Optional.of(operationalOnControlToPowerOffTransitionEffect), "OperationalOnControlToPowerOff", SysMLTransitionKind.internal);

		operationalOnPowerSwitchedOffTransition = new SysMLTransition(context, operationalState, operationalState, Optional.of(SysMLSignalEvent.class), Optional.of(isSwitchedOffGuard),
		Optional.of(operationalOnPowerSwitchedOffTransitionEffect), "PowerOffOnPowerSwitchedOn", SysMLTransitionKind.internal);

		operationalOnControlTransition = new SysMLTransition(context, operationalState, operationalState, Optional.of(SysMLSignalEvent.class), Optional.empty(), Optional.of(operationalOnControlTransitionEffect), "OperationalOnControl",
		SysMLTransitionKind.internal);

		operationalToPowerOffTransition = new SysMLTransition(context, operationalState, powerOffState, Optional.of(SysMLSignalEvent.class), Optional.of(isZeroPowerGuard), Optional.of(operationalToPowerOffTransitionEffect),
		"OperationalToPowerOff", SysMLTransitionKind.external);

		powerOffToFinalTransition = new FinalTransition(context, powerOffState, finalState, "PowerOffToFinal");
	}
}
