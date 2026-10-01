package sysmlinjavalibrary.components.communications.siprnet;

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
import sysmlinjavalibrary.components.communications.common.objects.SIPRNetRouterStatesEnum;

public class SIPRNetRouterStateMachine extends SysMLStateMachine
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
	private SysMLTransition operationalOnIPPacketHAIPETransition;
	@Transition
	private SysMLTransition operationalOnIPPacketDataLinkTransition;
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
	private SysMLGuardCondition isControlGuardCondition;
	@GuardCondition
	private SysMLGuardCondition isZeroPowerGuardCondition;
	@GuardCondition
	private SysMLGuardCondition isDatalinkIPPacketGuardCondition;
	@GuardCondition
	private SysMLGuardCondition isHAIPEIPPacketGuardCondition;

	@Guard
	private SysMLGuard isSwitchedOnGuard;
	@Guard
	private SysMLGuard isSwitchedOffGuard;
	@Guard
	private SysMLGuard isMinPowerGuard;
	@Guard
	private SysMLGuard isControlToPowerOffGuard;
	@Guard
	private SysMLGuard isControlGuard;
	@Guard
	private SysMLGuard isZeroPowerGuard;
	@Guard
	private SysMLGuard isDatalinkIPPacketGuard;
	@Guard
	private SysMLGuard isHAIPEIPPacketGuard;

	@EffectActionFunction
	private SysMLEffectActionFunction initialToPowerOffTransitionEffectAction;
	@EffectActionFunction
	private SysMLEffectActionFunction powerOffOnPowerSwitchedOnTransitionEffectAction;
	@EffectActionFunction
	private SysMLEffectActionFunction powerOffToInitializingTransitionEffectAction;
	@EffectActionFunction
	private SysMLEffectActionFunction operationalOnControlTransitionEffectAction;
	@EffectActionFunction
	private SysMLEffectActionFunction operationalOnIPPacketHAIPETransitionEffectAction;
	@EffectActionFunction
	private SysMLEffectActionFunction operationalOnIPPacketDataLinkTransitionEffectAction;
	@EffectActionFunction
	private SysMLEffectActionFunction operationalOnControlToPowerOffTransitionEffectAction;
	@EffectActionFunction
	private SysMLEffectActionFunction operationalOnPowerSwitchedOffTransitionEffectAction;
	@EffectActionFunction
	private SysMLEffectActionFunction operationalToPowerOffTransitionEffectAction;

	@Effect
	private SysMLEffect initialToPowerOffTransitionEffect;
	@Effect
	private SysMLEffect powerOffOnPowerSwitchedOnTransitionEffect;
	@Effect
	private SysMLEffect powerOffToInitializingTransitionEffect;
	@Effect
	private SysMLEffect operationalOnIPPacketHAIPETransitionEffect;
	@Effect
	private SysMLEffect operationalOnIPPacketDataLinkTransitionEffect;
	@Effect
	private SysMLEffect operationalOnControlTransitionEffect;
	@Effect
	public SysMLEffect operationalOnControlToPowerOffTransitionEffect;
	@Effect
	public SysMLEffect operationalOnPowerSwitchedOffTransitionEffect;
	@Effect
	private SysMLEffect operationalToPowerOffTransitionEffect;

	public SIPRNetRouterStateMachine(SIPRNetRouter ethernetSwitch)
	{
		super(Optional.of(ethernetSwitch), true, "SIPRNetRouterStateMachine");
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
			SIPRNetRouter siprnetRouter = (SIPRNetRouter)contextBlock.get();
			return event.isPresent() &&
				event.get() instanceof SysMLSignalEvent &&
				((SysMLSignalEvent)event.get()).signal instanceof ElectricalPowerSignal &&
				((ElectricalPowerSignal)((SysMLSignalEvent)event.get()).signal).power.current.greaterThan(siprnetRouter.minCurrentIn);
		};
		isControlToPowerOffGuardCondition = (event, contextBlock) ->
		{
			boolean result = false;
			if(event.isPresent() &&
				event.get() instanceof SysMLSignalEvent &&
				((SysMLSignalEvent)event.get()).signal instanceof SNMPRequestSignal)
			{
				SNMPRequest request = ((SNMPRequestSignal)((SysMLSignalEvent)event.get()).signal).request;
				SIPRNetRouterStatesEnum state = SIPRNetRouterStatesEnum.valueOf(request.mib.getDataStrings().get(1));
				if (state == SIPRNetRouterStatesEnum.PowerOff)
					result = true;
			}
			return result;
		};
		isControlGuardCondition = (event, contextBlock) ->
		{
			return ((SysMLSignalEvent)event.get()).signal instanceof SNMPRequestSignal;
		};
		isZeroPowerGuardCondition = (event, contextBlock) ->
		{
			return event.isPresent() &&
				event.get() instanceof SysMLSignalEvent &&
				((SysMLSignalEvent)event.get()).signal instanceof ElectricalPowerSignal &&
				((ElectricalPowerSignal)((SysMLSignalEvent)event.get()).signal).power.watts().lessThan(RReal.of(1));
		};
		isDatalinkIPPacketGuardCondition = (event, contextBlock) ->
		{
			return ((SysMLSignalEvent)event.get()).signal instanceof IPPacketSignal &&
				((IPPacketSignal)((SysMLSignalEvent)event.get()).signal).packet.isEncrypted;
		};
		isHAIPEIPPacketGuardCondition = (event, contextBlock) ->
		{
			return event.isPresent() &&
				event.get() instanceof SysMLSignalEvent &&
				((SysMLSignalEvent)event.get()).signal instanceof IPPacketSignal &&
				!((IPPacketSignal)((SysMLSignalEvent)event.get()).signal).packet.isEncrypted;
		};
	}

	@Override
	protected void createGuards()
	{
		super.createGuards();
		isSwitchedOnGuard = new SysMLGuard(context, isSwitchedOnGuardCondition, "isSwitchedOn");
		isSwitchedOffGuard = new SysMLGuard(context, isSwitchedOffGuardCondition, "isSwitchedOff");
		isMinPowerGuard = new SysMLGuard(context, isMinPowerGuardCondition, "isMinPower");
		isControlToPowerOffGuard = new SysMLGuard(context, isControlToPowerOffGuardCondition, "isControlToPowerOff");
		isControlGuard = new SysMLGuard(context, isControlGuardCondition, "isControl");
		isZeroPowerGuard = new SysMLGuard(context, isZeroPowerGuardCondition, "isZeroPower");
		isDatalinkIPPacketGuard = new SysMLGuard(context, isDatalinkIPPacketGuardCondition, "isZeroPower");
		isHAIPEIPPacketGuard = new SysMLGuard(context, isHAIPEIPPacketGuardCondition, "isZeroPower");
	}

	@Override
	protected void createEffectActionFunctions()
	{
		super.createEffectActionFunctions();
		initialToPowerOffTransitionEffectAction = (event, contextBlock) ->
		{
			SIPRNetRouter siprnetRouter = (SIPRNetRouter)contextBlock.get();
			siprnetRouter.initialize();
		};
		powerOffOnPowerSwitchedOnTransitionEffectAction = (event, contextBlock) ->
		{
			SIPRNetRouter siprnetRouter = (SIPRNetRouter)contextBlock.get();
			siprnetRouter.onSwitchToPowerOn();
		};
		powerOffToInitializingTransitionEffectAction = (event, contextBlock) ->
		{
			ElectricalPower power = ((ElectricalPowerSignal)((SysMLSignalEvent)event.get()).signal).power;
			SIPRNetRouter siprnetRouter = (SIPRNetRouter)contextBlock.get();
			siprnetRouter.onElectricalPowerOn(power);
		};
		operationalOnIPPacketHAIPETransitionEffectAction = (event, contextBlock) ->
		{
			IPPacket packet = ((IPPacketSignal)((SysMLSignalEvent)event.get()).signal).packet;
			SIPRNetRouter siprnetRouter = (SIPRNetRouter)contextBlock.get();
			siprnetRouter.onIPPacketHAIPE(packet);
		};
		operationalOnIPPacketDataLinkTransitionEffectAction = (event, contextBlock) ->
		{
			IPPacket packet = ((IPPacketSignal)((SysMLSignalEvent)event.get()).signal).packet;
			SIPRNetRouter siprnetRouter = (SIPRNetRouter)contextBlock.get();
			siprnetRouter.onIPPacketDataLink(packet);
		};
		operationalOnControlTransitionEffectAction = (event, contextBlock) ->
		{
			SNMPRequest request = ((SNMPRequestSignal)((SysMLSignalEvent)event.get()).signal).request;
			SIPRNetRouter siprnetRouter = (SIPRNetRouter)contextBlock.get();
			siprnetRouter.onSNMPRequest(request);
		};
		operationalOnControlToPowerOffTransitionEffectAction = (event, contextBlock) ->
		{
			SIPRNetRouter siprnetRouter = (SIPRNetRouter)contextBlock.get();
			siprnetRouter.onSNMPRequestToPowerOff();
		};
		operationalOnPowerSwitchedOffTransitionEffectAction = (event, contextBlock) ->
		{
			SIPRNetRouter siprnetRouter = (SIPRNetRouter)contextBlock.get();
			siprnetRouter.onSwitchToPowerOff();
		};
		operationalToPowerOffTransitionEffectAction = (event, contextBlock) ->
		{
			ElectricalPower power = ((ElectricalPowerSignal)((SysMLSignalEvent)event.get()).signal).power;
			SIPRNetRouter siprnetRouter = (SIPRNetRouter)contextBlock.get();
			siprnetRouter.onElectricalPowerOff(power);
		};
	}

	@Override
	protected void createEffects()
	{
		super.createEffects();
		initialToPowerOffTransitionEffect = new SysMLEffect(context, initialToPowerOffTransitionEffectAction, "InitialToPowerOffTransition");
		powerOffOnPowerSwitchedOnTransitionEffect = new SysMLEffect(context, powerOffOnPowerSwitchedOnTransitionEffectAction, "PowerOffOnPowerSwitchedOnTransition");
		powerOffToInitializingTransitionEffect = new SysMLEffect(context, powerOffToInitializingTransitionEffectAction, "PowerOffToInitializingTransition");
		operationalOnIPPacketHAIPETransitionEffect = new SysMLEffect(context, operationalOnIPPacketHAIPETransitionEffectAction, "OperationalOnIPPacketHAIPETransition");
		operationalOnIPPacketDataLinkTransitionEffect = new SysMLEffect(context, operationalOnIPPacketDataLinkTransitionEffectAction, "OperationalOnIPPacketDataLinkTransition");
		operationalOnControlTransitionEffect = new SysMLEffect(context, operationalOnControlTransitionEffectAction, "OperationalOnControlTransition");
		operationalOnControlToPowerOffTransitionEffect = new SysMLEffect(context, operationalOnControlToPowerOffTransitionEffectAction, "OperationalOnControlToPowerOffTransition");
		operationalOnPowerSwitchedOffTransitionEffect = new SysMLEffect(context, operationalOnPowerSwitchedOffTransitionEffectAction, "OperationalOnPowerSwitchedOffTransition");
		operationalToPowerOffTransitionEffect = new SysMLEffect(context, operationalToPowerOffTransitionEffectAction, "OperationalToPowerOffTransition");
	}

	@Override
	protected void createTransitions()
	{
		initialToPowerOffTransition = new InitialTransition(context, initialState, powerOffState, initialToPowerOffTransitionEffect, "InitialToPowerOff");
		
		powerOffOnPowerSwitchedOnTransition = new SysMLTransition(context, powerOffState, powerOffState, Optional.of(SysMLSignalEvent.class), Optional.of(isSwitchedOnGuard), Optional.of(powerOffOnPowerSwitchedOnTransitionEffect),
			"PowerOffOnPowerSwitchedOn", SysMLTransitionKind.internal);
		
		powerOffToInitializingTransition = new SysMLTransition(context, powerOffState, initializingState, Optional.of(SysMLSignalEvent.class), Optional.of(isMinPowerGuard),
			Optional.of(powerOffToInitializingTransitionEffect), "PowerOffToInitializing", SysMLTransitionKind.external);
		
		initializingToOperationalTransition = new SysMLTransition(context, initializingState, operationalState, Optional.empty(), Optional.empty(), Optional.empty(), "InitializingToOperational", SysMLTransitionKind.external);
		
		operationalOnIPPacketHAIPETransition = new SysMLTransition(context, operationalState, operationalState, Optional.of(SysMLSignalEvent.class), Optional.of(isHAIPEIPPacketGuard), Optional.of(operationalOnIPPacketHAIPETransitionEffect),
			"OperationalOnIPPacketHAIPE", SysMLTransitionKind.internal);
		
		operationalOnIPPacketDataLinkTransition = new SysMLTransition(context, operationalState, operationalState, Optional.of(SysMLSignalEvent.class), Optional.of(isDatalinkIPPacketGuard), Optional.of(operationalOnIPPacketDataLinkTransitionEffect),
			"OperationalOnIPPacketDataLink", SysMLTransitionKind.internal);
		
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
