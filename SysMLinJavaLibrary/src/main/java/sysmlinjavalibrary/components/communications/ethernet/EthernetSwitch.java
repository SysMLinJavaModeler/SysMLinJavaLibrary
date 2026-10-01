package sysmlinjavalibrary.components.communications.ethernet;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import sysmlinjava.attributetypes.Cost$US;
import sysmlinjava.attributetypes.CurrentAmps;
import sysmlinjava.attributetypes.ElectricalPower;
import sysmlinjava.attributetypes.ForceNewtons;
import sysmlinjava.attributetypes.FrequencyHertz;
import sysmlinjava.attributetypes.HeatWatts;
import sysmlinjava.attributetypes.InstantMilliseconds;
import sysmlinjava.attributetypes.PotentialElectricalVolts;
import sysmlinjava.attributetypes.PowerWatts;
import sysmlinjava.attributetypes.QuantityEach;
import sysmlinjava.attributetypes.VolumeMetersCubic;
import sysmlinjava.connectors.SysMLFlowConnector;
import sysmlinjava.connectors.SysMLFlowConnector.TypesEnum;
import sysmlinjava.javaannotations.actions.Action;
import sysmlinjava.javaannotations.attributes.Attribute;
import sysmlinjava.javaannotations.connectors.FlowConnector;
import sysmlinjava.javaannotations.ports.Port;
import sysmlinjava.parts.SysMLPart;
import sysmlinjava.states.FinalEvent;
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

public class EthernetSwitch extends SysMLPart
{
	@Port
	public List<EthernetProtocol> ethernets;
	@Port
	public MechanicalOnOffSwitch mechanicalPowerOnOffSwitch;
	@Port
	public MechanicalOnOffSwitchContact electronicPowerOnOffSwitch;
	@Port
	public ElectricalPowerSink electricalPower;
	@Port
	public ConvectiveHeatSource convectiveHeat;
	@Port
	public ComponentMountStructure rackMountPoints;
	@Port
	public SNMPAgentProtocol snmpAgent;

	@Attribute
	public ElectricalPower powerIn;
	@Attribute
	public ConvectiveHeat heatOut;
	@Attribute
	public ForceNewtons weightOut;

	@Attribute
	public QuantityEach numberEthernetPorts;
	@Attribute
	public VolumeMetersCubic maxSize;
	@Attribute
	public ForceNewtons maxWeight;
	@Attribute
	public PowerWatts minPowerIn;
	@Attribute
	public PowerWatts maxPowerIn;
	@Attribute
	public HeatWatts maxHeatOut;
	@Attribute
	public Cost$US maxCost;
	@Attribute
	public QuantityEach numberMountPoints;

	@FlowConnector
	private SysMLFlowConnector electronicToMechanicalPowerOnOffSwitchConnector;

	public EthernetSwitch()
	{
		super();
	}

	@Override
	@Action
	public void start()
	{
		super.start();
		maxSize.notifyAttributeObservers();
		maxWeight.notifyAttributeObservers();
		maxPowerIn.notifyAttributeObservers();
		maxHeatOut.notifyAttributeObservers();
	}
	
	@Action
	public void initialize()
	{
		logger.info("initializing...");
		ForceNewtons weightPerMountPoint = new ForceNewtons(weightOut.value / numberMountPoints.value);
		rackMountPoints.mountLeftFront.transmit(weightPerMountPoint);
		rackMountPoints.mountRightFront.transmit(weightPerMountPoint);
		rackMountPoints.mountLeftRear.transmit(weightPerMountPoint);
		rackMountPoints.mountRightRear.transmit(weightPerMountPoint);
	}

	@Action
	public void onSwitchToPowerOn()
	{
		logger.info("");
		powerIn.current.value = 15;
		powerIn.name = Optional.of(name.isPresent() ? name.get() : getClass().getSimpleName());
		electricalPower.transmit(new OnOffSwitch(true));
	}

	@Action
	public void onSwitchToPowerOff()
	{
		logger.info("switch to power off");
		powerIn.current.value = 0;
		electricalPower.transmit(powerIn);
	}

	@Action
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

	@Action
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

	@Action
	public void onEthernetPacket(EthernetPacket nextPacket)
	{
		logger.info("nextPacket: " + nextPacket.toString());
		ethernets.get(nextPacket.destinationAddress.intValue()).transmit(nextPacket);
	}

	@Action
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

	@Action
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
	protected void createAttributes()
	{
		numberMountPoints = new QuantityEach(4);
		maxSize = new VolumeMetersCubic(0.08);
		maxWeight = new ForceNewtons(30);
		minPowerIn = new PowerWatts(50);
		maxPowerIn = new PowerWatts(100);
		maxHeatOut = new HeatWatts(100);
		maxCost = new Cost$US(1000);
		numberEthernetPorts = new QuantityEach(16);
		powerIn = new ElectricalPower(new FrequencyHertz(60), new PotentialElectricalVolts(110), new CurrentAmps(0));
		heatOut = new ConvectiveHeat(new HeatWatts(0));
		weightOut = new ForceNewtons(40);
	}

	@Override
	protected void createPorts()
	{
		super.createPorts();
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
	protected void createFlowConnectors()
	{
		electronicToMechanicalPowerOnOffSwitchConnector = new SysMLFlowConnector(TypesEnum.peertopeer, false,
		electronicPowerOnOffSwitch, mechanicalPowerOnOffSwitch, "", 0L);
	}

}
