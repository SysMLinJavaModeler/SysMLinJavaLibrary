package sysmlinjavalibrary.components.communications.ethernet;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import sysmlinjava.annotations.AssociationConnector;
import sysmlinjava.annotations.AssociationConnectorFunction;
import sysmlinjava.annotations.Flow;
import sysmlinjava.annotations.FullPort;
import sysmlinjava.annotations.Operation;
import sysmlinjava.annotations.Reception;
import sysmlinjava.annotations.Value;
import sysmlinjava.blocks.SysMLBlock;
import sysmlinjava.connectors.SysMLAssociationBlockConnector;
import sysmlinjava.connectors.SysMLAssociationBlockConnectorFunction;
import sysmlinjava.kinds.SysMLFlowDirectionKind;
import sysmlinjava.statemachine.FinalEvent;
import sysmlinjava.valuetypes.Cost$US;
import sysmlinjava.valuetypes.CurrentAmps;
import sysmlinjava.valuetypes.ElectricalPower;
import sysmlinjava.valuetypes.ForceNewtons;
import sysmlinjava.valuetypes.FrequencyHertz;
import sysmlinjava.valuetypes.HeatWatts;
import sysmlinjava.valuetypes.InstantMilliseconds;
import sysmlinjava.valuetypes.PotentialElectricalVolts;
import sysmlinjava.valuetypes.PowerWatts;
import sysmlinjava.valuetypes.QuantityEach;
import sysmlinjava.valuetypes.VolumeMetersCubic;
import sysmlinjavalibrary.common.objects.energy.thermal.ConvectiveHeat;
import sysmlinjavalibrary.common.objects.information.EthernetPacket;
import sysmlinjavalibrary.common.objects.information.MIB;
import sysmlinjavalibrary.common.objects.information.OnOffSwitch;
import sysmlinjavalibrary.common.objects.information.SNMPRequest;
import sysmlinjavalibrary.common.objects.information.SNMPResponse;
import sysmlinjavalibrary.common.ports.energy.electrical.ElectricalPowerSink;
import sysmlinjavalibrary.common.ports.energy.mechanical.ComponentMountStructure;
import sysmlinjavalibrary.common.ports.energy.mechanical.MechanicalOnOffSwitch;
import sysmlinjavalibrary.common.ports.energy.mechanical.MechanicalOnOffSwitchContact;
import sysmlinjavalibrary.common.ports.energy.thermal.ConvectiveHeatSource;
import sysmlinjavalibrary.common.ports.information.EthernetProtocol;
import sysmlinjavalibrary.common.ports.information.SNMPAgentProtocol;
import sysmlinjavalibrary.components.communications.common.objects.EthernetSwitchStatesEnum;

public class EthernetSwitch extends SysMLBlock
{
	@FullPort
	public List<EthernetProtocol> ethernets;
	@FullPort
	public MechanicalOnOffSwitch mechanicalPowerOnOffSwitch;
	@FullPort
	public MechanicalOnOffSwitchContact electronicPowerOnOffSwitch;
	@FullPort
	public ElectricalPowerSink electricalPower;
	@FullPort
	public ConvectiveHeatSource convectiveHeat;
	@FullPort
	public ComponentMountStructure rackMountPoints;
	@FullPort
	public SNMPAgentProtocol snmpAgent;

	@Flow(direction = SysMLFlowDirectionKind.in)
	public ElectricalPower powerIn;
	@Flow(direction = SysMLFlowDirectionKind.out)
	public ConvectiveHeat heatOut;
	@Flow(direction = SysMLFlowDirectionKind.out)
	public ForceNewtons weightOut;

	@Value
	public QuantityEach numberEthernetPorts;
	@Value
	public VolumeMetersCubic maxSize;
	@Value
	public ForceNewtons maxWeight;
	@Value
	public PowerWatts minPowerIn;
	@Value
	public PowerWatts maxPowerIn;
	@Value
	public HeatWatts maxHeatOut;
	@Value
	public Cost$US maxCost;
	@Value
	public QuantityEach numberMountPoints;

	@AssociationConnectorFunction
	private SysMLAssociationBlockConnectorFunction electronicToMechanicalPowerOnOffSwitchConnectorFunction;

	@AssociationConnector
	private SysMLAssociationBlockConnector electronicToMechanicalPowerOnOffSwitchConnector;

	public EthernetSwitch()
	{
		super();
	}

	@Operation
	public void start()
	{
		super.start();
		maxSize.notifyValueChangeObservers();
		maxWeight.notifyValueChangeObservers();
		maxPowerIn.notifyValueChangeObservers();
		maxHeatOut.notifyValueChangeObservers();
	}
	
	@Operation
	public void initialize()
	{
		logger.info("initializing...");
		ForceNewtons weightPerMountPoint = new ForceNewtons(weightOut.value / numberMountPoints.value);
		rackMountPoints.mountLeftFront.transmit(weightPerMountPoint);
		rackMountPoints.mountRightFront.transmit(weightPerMountPoint);
		rackMountPoints.mountLeftRear.transmit(weightPerMountPoint);
		rackMountPoints.mountRightRear.transmit(weightPerMountPoint);
	}

	@Reception
	public void onSwitchToPowerOn()
	{
		logger.info("");
		powerIn.current.value = 15;
		powerIn.name = Optional.of(name.isPresent() ? name.get() : getClass().getSimpleName());
		electricalPower.transmit(new OnOffSwitch(true));
	}

	@Reception
	public void onSwitchToPowerOff()
	{
		logger.info("switch to power off");
		powerIn.current.value = 0;
		electricalPower.transmit(powerIn);
	}

	@Reception
	public void onElectricalPowerOn(ElectricalPower power)
	{
		logger.info(power.toString());
		if (power.watts().greaterThanOrEqualTo(minPowerIn) && power.watts().lessThanOrEqualTo(maxPowerIn))
		{
			powerIn.current.value = power.current.value;
			heatOut.heat.value = power.watts().value;
			heatOut.name = Optional.of(getClass().getSimpleName());
			convectiveHeat.transmit(heatOut);
		}
		else
			logger.severe("power not in acceptable range: " + power.toString());
	}

	@Reception
	public void onElectricalPowerOff(ElectricalPower power)
	{
		logger.info(power.toString());
		powerIn.current.value = 0;
		heatOut.heat.value = 0;
		convectiveHeat.transmit(heatOut);
		MIB mib = new MIB(InstantMilliseconds.now(), this.getClass().getSimpleName(), EthernetSwitchStatesEnum.PowerOff.toString());
		snmpAgent.transmit(new SNMPResponse(InstantMilliseconds.now(), mib));
		delay(2);
		acceptEvent(new FinalEvent());
	}

	@Reception
	public void onEthernetPacket(EthernetPacket nextPacket)
	{
		logger.info("nextPacket: " + nextPacket.toString());
		ethernets.get(nextPacket.destinationAddress.intValue()).transmit(nextPacket);
	}

	@Reception
	public void onSNMPRequest(SNMPRequest request)
	{
		logger.info(request.toString());
		List<String> dataStrings = request.mib.getDataStrings();
		if (dataStrings.get(0).contains(this.getClass().getSimpleName()))
		{
			String state = dataStrings.get(1);
			MIB mib;
			if (state.equals("Operational"))
			{
				mib = new MIB(InstantMilliseconds.now(), this.getClass().getSimpleName(), state);
				snmpAgent.transmit(new SNMPResponse(InstantMilliseconds.now(), mib));
			}
			else
			{
				logger.severe("invalid reception for requested state: " + state);
				mib = new MIB(InstantMilliseconds.now(), this.getClass().getSimpleName(), EthernetSwitchStatesEnum.Operational.toString());
				snmpAgent.transmit(new SNMPResponse(InstantMilliseconds.now(), mib));
			}
		}
	}

	@Reception
	public void onSNMPRequestToPowerOff()
	{
		logger.info("control to power off");
		electronicPowerOnOffSwitch.transmit(new OnOffSwitch(false));
	}

	@Override
	protected void createStateMachine()
	{
		stateMachine = Optional.of(new EthernetSwitchStateMachine(this));
	}

	@Override
	protected void createValues()
	{
		numberMountPoints = new QuantityEach(4);
		maxSize = new VolumeMetersCubic(0.08);
		maxWeight = new ForceNewtons(30);
		minPowerIn = new PowerWatts(50);
		maxPowerIn = new PowerWatts(100);
		maxHeatOut = new HeatWatts(100);
		maxCost = new Cost$US(1000);
		numberEthernetPorts = new QuantityEach(16);
	}

	@Override
	protected void createFlows()
	{
		powerIn = new ElectricalPower(new FrequencyHertz(60), new PotentialElectricalVolts(110), new CurrentAmps(0));
		heatOut = new ConvectiveHeat(new HeatWatts(0));
		weightOut = new ForceNewtons(40);
	}

	@Override
	protected void createFullPorts()
	{
		super.createFullPorts();
		ethernets = new ArrayList<EthernetProtocol>();
		for (long i = 0; i < numberEthernetPorts.value; i++)
			ethernets.add(new EthernetProtocol(this, i));
		mechanicalPowerOnOffSwitch = new MechanicalOnOffSwitch(this, 0L);
		electronicPowerOnOffSwitch = new MechanicalOnOffSwitchContact(this, 0L);
		electricalPower = new ElectricalPowerSink(this, 0L);
		convectiveHeat = new ConvectiveHeatSource(this, 0L);
		rackMountPoints = new ComponentMountStructure(this, 0L);
		snmpAgent = new SNMPAgentProtocol(this, 0L);
	}

	@Override
	protected void createConnectorFunctions()
	{
		electronicToMechanicalPowerOnOffSwitchConnectorFunction = () ->
		{
			electronicPowerOnOffSwitch.addConnectedPortPeer(mechanicalPowerOnOffSwitch);
		};
	}

	@Override
	protected void createConnectors()
	{
		electronicToMechanicalPowerOnOffSwitchConnector = new SysMLAssociationBlockConnector(this, this, electronicToMechanicalPowerOnOffSwitchConnectorFunction);
	}

}
