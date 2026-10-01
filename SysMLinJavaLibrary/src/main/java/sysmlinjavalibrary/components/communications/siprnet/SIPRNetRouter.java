package sysmlinjavalibrary.components.communications.siprnet;

import static sysmlinjava.attributetypes.ElectricalPower.standard110V;
import static sysmlinjava.attributetypes.ElectricalPower.standard50Hz;

import java.util.List;
import java.util.Optional;

import sysmlinjava.attributetypes.Cost$US;
import sysmlinjava.attributetypes.CurrentAmps;
import sysmlinjava.attributetypes.ElectricalPower;
import sysmlinjava.attributetypes.ForceNewtons;
import sysmlinjava.attributetypes.HeatWatts;
import sysmlinjava.attributetypes.IInteger;
import sysmlinjava.attributetypes.InstantMilliseconds;
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
import sysmlinjava.states.StateBehaviorContext;
import sysmlinjavalibrary.common.objects.energy.thermal.ConvectiveHeat;
import sysmlinjavalibrary.common.objects.information.HDLCFrame;
import sysmlinjavalibrary.common.objects.information.IPPacket;
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
import sysmlinjavalibrary.common.ports.information.HDLCProtocol;
import sysmlinjavalibrary.common.ports.information.InternetProtocol;
import sysmlinjavalibrary.common.ports.information.SNMPAgentProtocol;
import sysmlinjavalibrary.components.communications.common.objects.SIPRNetRouterStatesEnum;

public class SIPRNetRouter extends SysMLPart implements StateBehaviorContext
{
	@Port
	public HighAssuranceIPEncryptor haipe;
	@Port
	public InternetProtocol ipEncrypted;
	@Port
	public EthernetProtocol ethernetEncrypted;
	@Port
	public HDLCProtocol hdlc;
	@Port
	public SNMPAgentProtocol snmpAgent;
	@Port
	public MechanicalOnOffSwitch mechanicalPowerOnOffSwitch;
	@Port
	public MechanicalOnOffSwitchContact electronicPowerOnOffSwitch;
	@Port
	public ElectricalPowerSink electricalPower;
	@Port
	public ConvectiveHeatSource convectiveHeat;
	@Port
	public ComponentMountStructure rackMount;

	@Attribute
	public VolumeMetersCubic sizeOut;
	@Attribute
	public ElectricalPower powerIn;
	@Attribute
	public ConvectiveHeat heatOut;
	@Attribute
	public ForceNewtons weightOut;

	@Attribute
	public VolumeMetersCubic maxSize;
	@Attribute
	public ForceNewtons maxWeight;
	@Attribute
	public CurrentAmps minCurrentIn;
	@Attribute
	public CurrentAmps maxCurrentIn;
	@Attribute
	public PowerWatts maxPowerIn;
	@Attribute
	public HeatWatts maxHeatOut;
	@Attribute
	public Cost$US maxCost;
	@Attribute
	public QuantityEach numberMountPoints;
	@Attribute
	public IInteger rackMountHole;
	
	@FlowConnector
	private SysMLFlowConnector ipToToHAIPEIP;
	@FlowConnector
	private SysMLFlowConnector ethernetToHAIPEEthernet;
	@FlowConnector
	private SysMLFlowConnector electronicToMechanicalPowerOnOffSwitch;

	public SIPRNetRouter(String name, long id)
	{
		super(name, id);
	}

	@Override
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
		double weightPerMountPoint = weightOut.value / numberMountPoints.value;
		rackMount.mountLeftFront .transmit(new ForceNewtons(weightPerMountPoint, 0, rackMountHole.value + 0));
		rackMount.mountRightFront.transmit(new ForceNewtons(weightPerMountPoint, 0, rackMountHole.value + 1));
		rackMount.mountLeftRear  .transmit(new ForceNewtons(weightPerMountPoint, 0, rackMountHole.value + 2));
		rackMount.mountRightRear .transmit(new ForceNewtons(weightPerMountPoint, 0, rackMountHole.value + 3));
	}

	@Action
	public void onIPPacketHAIPE(IPPacket ipPacket)
	{
		logger.info(ipPacket.toString());
		Integer source = ipPacket.sourceAddress;
		Integer destination = ipPacket.destinationAddress;
		hdlc.transmit(new HDLCFrame(source, destination, ipPacket));
	}

	@Action
	public void onIPPacketDataLink(IPPacket packet)
	{
		logger.info(packet.toString());
		Integer source = packet.sourceAddress;
		Integer destination = packet.destinationAddress;
		haipe.transmit(new IPPacket(source, destination, packet.data));
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
				mib = new MIB(InstantMilliseconds.now(), this.getClass().getSimpleName(), SIPRNetRouterStatesEnum.Operational.toString());
				snmpAgent.transmit(new SNMPResponse(InstantMilliseconds.now(), mib));
			}
		}
	}

	@Action
	public void onSNMPRequestToPowerOff()
	{
		logger.info("control to power off");
		haipe.stop();
		electronicPowerOnOffSwitch.transmit(new OnOffSwitch(false));
	}

	@Action
	public void onSwitchToPowerOn()
	{
		logger.info("switch to power on");
		powerIn.current.setValue(maxCurrentIn.added(minCurrentIn).dividedBy(2.0)); //assume "medium" activity
		powerIn.name = Optional.of(name.isPresent() ? name.get() : getClass().getSimpleName());
		electricalPower.transmit(new ElectricalPower(powerIn));
	}

	@Action
	public void onSwitchToPowerOff()
	{
		logger.info("switch to power off");
		powerIn.current.setValue(0);
		electricalPower.transmit(powerIn);
	}

	@Action
	public void onElectricalPowerOn(ElectricalPower power)
	{
		logger.info(power.toString());
		if (power.current.greaterThanOrEqualTo(minCurrentIn) && power.current.lessThanOrEqualTo(maxCurrentIn))
		{
			powerIn.current.setValue(power.current);
			heatOut.heat.setValue(power.watts());
			heatOut.name = Optional.of(getClass().getSimpleName());
			convectiveHeat.transmit(heatOut);
			haipe.start();
		}
		else
			logger.severe("power not in acceptable range: " + power.toString());
	}

	@Action
	public void onElectricalPowerOff(ElectricalPower power)
	{
		logger.info(power.toString());
		powerIn.current.setValue(0);
		heatOut.heat.setValue(0);
		convectiveHeat.transmit(heatOut);
		MIB mib = new MIB(InstantMilliseconds.now(), this.getClass().getSimpleName(), SIPRNetRouterStatesEnum.PowerOff.toString());
		snmpAgent.transmit(new SNMPResponse(InstantMilliseconds.now(), mib));
		acceptEvent(new FinalEvent());
	}

	@Override
	protected void createStateMachine()
	{
		stateMachine = Optional.of(new SIPRNetRouterStateMachine(this));
	}

	@Override
	protected void createAttributes()
	{
		maxSize = new VolumeMetersCubic(0.08);
		maxWeight = new ForceNewtons(40);
		minCurrentIn = new CurrentAmps(2);
		maxCurrentIn = new CurrentAmps(7);
		maxPowerIn = new PowerWatts(maxCurrentIn.multipliedBy(standard110V));
		maxHeatOut = new HeatWatts(maxPowerIn.value);
		maxCost = new Cost$US(50_000);
		numberMountPoints = new QuantityEach(4);
		rackMountHole = new IInteger(3);
		sizeOut = new VolumeMetersCubic(0.03);
		powerIn = new ElectricalPower(standard50Hz, standard110V, new CurrentAmps(0));
		heatOut = new ConvectiveHeat(new HeatWatts(0));
		weightOut = new ForceNewtons(40);
	}

	@Override
	protected void createPorts()
	{
		super.createPorts();
		haipe = new HighAssuranceIPEncryptor(this, "HAIPE", 0L);
		ethernetEncrypted = new EthernetProtocol(this, 0L);
		ipEncrypted = new InternetProtocol(this, 0L);
		hdlc = new HDLCProtocol(this, 0L);
		snmpAgent = new SNMPAgentProtocol(this, 0L);
		mechanicalPowerOnOffSwitch = new MechanicalOnOffSwitch(this, 0L);
		electronicPowerOnOffSwitch = new MechanicalOnOffSwitchContact(this, 0L);
		electricalPower = new ElectricalPowerSink(this, 0L);
		convectiveHeat = new ConvectiveHeatSource(this, 0L);
		rackMount = new ComponentMountStructure(this, 0L);
	}

	@Override
	protected void createFlowConnectors()
	{
		ipToToHAIPEIP = new SysMLFlowConnector(TypesEnum.peertopeer, true, ipEncrypted, haipe.ipEncrypted, "", 0L);
		ethernetToHAIPEEthernet = new SysMLFlowConnector(TypesEnum.peertopeer, true, ethernetEncrypted, haipe.ethernetEncrypted, "", 0L);

		electronicToMechanicalPowerOnOffSwitch = new SysMLFlowConnector(TypesEnum.peertopeer, true,
			electronicPowerOnOffSwitch, mechanicalPowerOnOffSwitch, "", 0L);
	}
}
