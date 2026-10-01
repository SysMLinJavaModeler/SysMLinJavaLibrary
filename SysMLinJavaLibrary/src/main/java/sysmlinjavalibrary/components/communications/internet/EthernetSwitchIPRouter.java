package sysmlinjavalibrary.components.communications.internet;

import static sysmlinjava.attributetypes.ElectricalPower.standard110V;
import static sysmlinjava.attributetypes.ElectricalPower.standard50Hz;

import java.util.HashMap;
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
import sysmlinjavalibrary.common.objects.energy.thermal.ConvectiveHeat;
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
import sysmlinjavalibrary.common.ports.information.InternetProtocol;
import sysmlinjavalibrary.common.ports.information.InternetRoutingProtocol;
import sysmlinjavalibrary.common.ports.information.SNMPAgentProtocol;
import sysmlinjavalibrary.common.ports.information.UserDatagramProtocol;
import sysmlinjavalibrary.components.communications.common.objects.EthernetSwitchIPRouterStatesEnum;

/**
 * The {@code EthernetSwitchIPRouter} is a SysMLinJava model for a common
 * switch/router which is a system component that performs local-area network
 * and internetwork communications for connected computers and devices. It is an
 * extension of the standard {@code SysMLPart} that transmits and receives
 * {@code IPPacket}s encapsulated in {@code EthernetPacket}s and routes them to
 * connected devices in accordance with installed routing tables. The
 * {@code EthernetSwitchIPRouter} model operates in accordance with standard IP
 * and Ethernet protocol specifications.
 * <p>
 * The {@code EthernetSwitchIPRouter} block includes full ports for each of the
 * protocols used to communicate with the connected devices. These protocols
 * consist of IP over ethernet as well as an internet routing protocol. It also
 * includes full ports for switching the device on ad off as well as ports for
 * receiving electrical power and convecting heat. It has flow values for
 * power-in and heat-out. Other specified block values include availability,
 * size, weight, speed, and cost.
 * <p>
 * The block also contains all of the connectors between ports in the block.
 * These include the connectors between the ports that represent the protocol
 * stacks of the external interfaces. Note the connectors between
 * {@code EthernetSwitchIPRouter} protocols and external systems are specified
 * in the system block that contains the switch/router as a part.
 * 
 * @author ModelerOne
 */
public class EthernetSwitchIPRouter extends SysMLPart
{
	@Port
	public EthernetProtocol ethernet0;
	@Port
	public EthernetProtocol ethernet1;
	@Port
	public EthernetProtocol ethernet2;
	@Port
	public EthernetProtocol ethernet3;
	@Port
	public InternetProtocol ip;
	@Port
	public InternetRoutingProtocol ipRouting;
	@Port
	public UserDatagramProtocol udp;
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
	public HeatWatts maxHeatOut;
	@Attribute
	public CurrentAmps minCurrentIn;
	@Attribute
	public CurrentAmps maxCurrentIn;
	@Attribute
	public PowerWatts maxPowerIn;
	@Attribute
	public Cost$US maxCost;
	@Attribute
	public QuantityEach numberMountPoints;
	@Attribute
	public IInteger rackMountHole;

	@FlowConnector
	public SysMLFlowConnector ethernetToIPConnectors;
	@FlowConnector
	private SysMLFlowConnector electronicToMechanicalPowerOnOffSwitchConnector;

	public IPAddressToEthernetPortMap ipToEthernetMap;
	private SysMLFlowConnector ethernetToIP;
	private SysMLFlowConnector ipRoutingToEthernet;

	public EthernetSwitchIPRouter(String name, long id)
	{
		super(name, id);
	}

	public EthernetSwitchIPRouter()
	{
		super("EthernetSwitchIPRouter", 0L);
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
		rackMount.mountLeftFront.transmit(new ForceNewtons(weightPerMountPoint, 0, rackMountHole.value + 0));
		rackMount.mountRightFront.transmit(new ForceNewtons(weightPerMountPoint, 0, rackMountHole.value + 1));
		rackMount.mountLeftRear.transmit(new ForceNewtons(weightPerMountPoint, 0, rackMountHole.value + 2));
		rackMount.mountRightRear.transmit(new ForceNewtons(weightPerMountPoint, 0, rackMountHole.value + 3));
	}

	@Action
	public void onSwitchToPowerOn()
	{
		logger.info("switch to power on");
		powerIn.current.setValue(maxCurrentIn.added(minCurrentIn).dividedBy(2.0)); // assume "medium" activity
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
			ethernet0.start();
			ethernet1.start();
			ethernet2.start();
			ethernet3.start();
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
		MIB mib = new MIB(InstantMilliseconds.now(), this.getClass().getSimpleName(), EthernetSwitchIPRouterStatesEnum.PowerOff.toString());
		snmpAgent.transmit(new SNMPResponse(InstantMilliseconds.now(), mib));
		delay(2);
		acceptEvent(new FinalEvent());
	}

	@Action
	public void onIPPacket(IPPacket packet)
	{
		logger.info(packet.toString());
		ipRouting.transmit(packet);
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
				mib = new MIB(InstantMilliseconds.now(), this.getClass().getSimpleName(), EthernetSwitchIPRouterStatesEnum.Operational.toString());
				snmpAgent.transmit(new SNMPResponse(InstantMilliseconds.now(), mib));
			}
		}
	}

	@Action
	public void onSNMPRequestToPowerOff()
	{
		logger.info("control to power off");
		ethernet0.stop();
		ethernet1.stop();
		ethernet2.stop();
		ethernet3.stop();
		electronicPowerOnOffSwitch.transmit(new OnOffSwitch(false));
	}

	@Override
	protected void createStateMachine()
	{
		stateMachine = Optional.of(new EthernetSwitchIPRouterStateMachine(this));
	}

	@Override
	protected void createAttributes()
	{
		maxSize = new VolumeMetersCubic(0.08);
		maxWeight = new ForceNewtons(20);
		minCurrentIn = new CurrentAmps(2);
		maxCurrentIn = new CurrentAmps(5);
		maxPowerIn = new PowerWatts(maxCurrentIn.multipliedBy(standard110V));
		maxHeatOut = new HeatWatts(maxPowerIn.value);
		maxCost = new Cost$US(1000);
		numberMountPoints = new QuantityEach(4);
		rackMountHole = new IInteger(2);
		ipToEthernetMap = new IPAddressToEthernetPortMap();
		sizeOut = new VolumeMetersCubic(0.01);
		powerIn = new ElectricalPower(standard50Hz, standard110V, new CurrentAmps(0));
		heatOut = new ConvectiveHeat(new HeatWatts(0));
		weightOut = new ForceNewtons(20);
	}

	@Override
	protected void createPorts()
	{
		ethernet0 = new EthernetProtocol(this, 0L);
		ethernet1 = new EthernetProtocol(this, 1L);
		ethernet2 = new EthernetProtocol(this, 2L);
		ethernet3 = new EthernetProtocol(this, 3L);
		ip = new InternetProtocol(this, this, 0L);
		ipRouting = new InternetRoutingProtocol(this, 0L);
		udp = new UserDatagramProtocol(this, 0L);
		mechanicalPowerOnOffSwitch = new MechanicalOnOffSwitch(this, 0L);
		electronicPowerOnOffSwitch = new MechanicalOnOffSwitchContact(this, 0L);
		electricalPower = new ElectricalPowerSink(this, 0L);
		convectiveHeat = new ConvectiveHeatSource(this, 0L);
		rackMount = new ComponentMountStructure(this, 0L);
		snmpAgent = new SNMPAgentProtocol(this, 0L);
	}

	@Override
	protected void createFlowConnectors()
	{
		ethernetToIP = new SysMLFlowConnector(TypesEnum.servertoclient, false,
			List.of(ethernet0, ethernet1, ethernet2, ethernet3),
			List.of(ip, ip, ip, ip), "", 0L);
		ipRoutingToEthernet = new SysMLFlowConnector(TypesEnum.clienttoserver, false,
			List.of(ipRouting, ipRouting, ipRouting, ipRouting),
			List.of(ethernet0, ethernet1, ethernet2, ethernet3), "", 0L);

		electronicToMechanicalPowerOnOffSwitchConnector = new SysMLFlowConnector(TypesEnum.peertopeer, false,
			electronicPowerOnOffSwitch, mechanicalPowerOnOffSwitch, "", 0L);
	}

	public class IPAddressToEthernetPortMap extends HashMap<Integer, Integer>
	{
		private static final long serialVersionUID = -3171005757832433632L;

		public IPAddressToEthernetPortMap()
		{
			super();
		}

		public Integer ethernetPortFor(Integer ipAddress)
		{
			return get(ipAddress);
		}
	}
}
