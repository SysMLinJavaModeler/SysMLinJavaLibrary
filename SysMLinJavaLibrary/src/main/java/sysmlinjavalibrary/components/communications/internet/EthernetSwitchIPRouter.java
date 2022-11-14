package sysmlinjavalibrary.components.communications.internet;

import static sysmlinjava.valuetypes.ElectricalPower.standard110V;
import static sysmlinjava.valuetypes.ElectricalPower.standard50Hz;
import java.util.HashMap;
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
import sysmlinjava.valuetypes.HeatWatts;
import sysmlinjava.valuetypes.InstantMilliseconds;
import sysmlinjava.valuetypes.IInteger;
import sysmlinjava.valuetypes.PowerWatts;
import sysmlinjava.valuetypes.QuantityEach;
import sysmlinjava.valuetypes.VolumeMetersCubic;
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
 * The {@code EthernetSwitchIPRouter} is a SysMLinJava model for a common switch/router which is a system component that performs local-area
 * network and internetwork communications for connected computers and devices.
 * It is an extension of the standard {@code SysMLBlock} that transmits and receives {@code IPPacket}s encapsulated in {@code EthernetPacket}s and
 * routes them to connected devices in accordance with installed routing tables.
 * The {@code EthernetSwitchIPRouter} model  operates in accordance with standard IP and
 * Ethernet protocol specifications.
 * <p>
 * The {@code EthernetSwitchIPRouter} block includes full ports for each of the
 * protocols used to communicate with the connected devices. These protocols
 * consist of IP over ethernet as well as an internet routing protocol. It also
 * includes full ports for switching the device on ad off as well as ports for receiving electrical power and convecting heat.
 * It has flow values for power-in and heat-out. Other specified block values include availability, size,
 * weight, speed, and cost.
 * <p>
 * The block also contains all of the connectors between ports in the block.
 * These include the connectors between the ports that represent the
 * protocol stacks of the external interfaces. Note the connectors between
 * {@code EthernetSwitchIPRouter} protocols and external systems are specified in the
 * system block that contains the switch/router as a part.
 * 
 * @author ModelerOne
 *
 */
public class EthernetSwitchIPRouter extends SysMLBlock
{
	@FullPort
	public EthernetProtocol ethernet0;
	@FullPort
	public EthernetProtocol ethernet1;
	@FullPort
	public EthernetProtocol ethernet2;
	@FullPort
	public EthernetProtocol ethernet3;
	@FullPort
	public InternetProtocol ip;
	@FullPort
	public InternetRoutingProtocol ipRouting;
	@FullPort
	public UserDatagramProtocol udp;
	@FullPort
	public SNMPAgentProtocol snmpAgent;
	@FullPort
	public MechanicalOnOffSwitch mechanicalPowerOnOffSwitch;
	@FullPort
	public MechanicalOnOffSwitchContact electronicPowerOnOffSwitch;
	@FullPort
	public ElectricalPowerSink electricalPower;
	@FullPort
	public ConvectiveHeatSource convectiveHeat;
	@FullPort
	public ComponentMountStructure rackMount;

	@Flow(direction = SysMLFlowDirectionKind.out)
	public VolumeMetersCubic sizeOut;
	@Flow(direction = SysMLFlowDirectionKind.in)
	public ElectricalPower powerIn;
	@Flow(direction = SysMLFlowDirectionKind.out)
	public ConvectiveHeat heatOut;
	@Flow(direction = SysMLFlowDirectionKind.out)
	public ForceNewtons weightOut;

	@Value
	public VolumeMetersCubic maxSize;
	@Value
	public ForceNewtons maxWeight;
	@Value
	public HeatWatts maxHeatOut;
	@Value
	public CurrentAmps minCurrentIn;
	@Value
	public CurrentAmps maxCurrentIn;
	@Value
	public PowerWatts maxPowerIn;
	@Value
	public Cost$US maxCost;
	@Value
	public QuantityEach numberMountPoints;
	@Value
	public IInteger rackMountHole;

	@AssociationConnectorFunction
	public SysMLAssociationBlockConnectorFunction ethernetToIPConnectorsFunction;
	@AssociationConnectorFunction
	private SysMLAssociationBlockConnectorFunction electronicToMechanicalPowerOnOffSwitchConnectorFunction;

	@AssociationConnector
	public SysMLAssociationBlockConnector ethernetToIPConnectors;
	@AssociationConnector
	private SysMLAssociationBlockConnector electronicToMechanicalPowerOnOffSwitchConnector;

	public IPAddressToEthernetPortMap ipToEthernetMap;

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
		maxSize.notifyValueChangeObservers();
		maxWeight.notifyValueChangeObservers();
		maxPowerIn.notifyValueChangeObservers();
		maxHeatOut.notifyValueChangeObservers();
	}

	@Operation
	public void initialize()
	{
		logger.info("initializing...");
		double weightPerMountPoint = weightOut.value / numberMountPoints.value;
		rackMount.mountLeftFront .transmit(new ForceNewtons(weightPerMountPoint, 0, rackMountHole.value + 0));
		rackMount.mountRightFront.transmit(new ForceNewtons(weightPerMountPoint, 0, rackMountHole.value + 1));
		rackMount.mountLeftRear  .transmit(new ForceNewtons(weightPerMountPoint, 0, rackMountHole.value + 2));
		rackMount.mountRightRear .transmit(new ForceNewtons(weightPerMountPoint, 0, rackMountHole.value + 3));
	}

	@Reception
	public void onSwitchToPowerOn()
	{
		logger.info("switch to power on");
		powerIn.current.setValue(maxCurrentIn.added(minCurrentIn).dividedBy(2.0)); //assume "medium" activity
		powerIn.name = Optional.of(name.isPresent() ? name.get() : getClass().getSimpleName());
		electricalPower.transmit(new ElectricalPower(powerIn));
	}

	@Reception
	public void onSwitchToPowerOff()
	{
		logger.info("switch to power off");
		powerIn.current.setValue(0);
		electricalPower.transmit(powerIn);
	}

	@Reception
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

	@Reception
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

	@Operation
	public void onIPPacket(IPPacket packet)
	{
		logger.info(packet.toString());
		ipRouting.transmit(packet);
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
				mib = new MIB(InstantMilliseconds.now(), this.getClass().getSimpleName(), EthernetSwitchIPRouterStatesEnum.Operational.toString());
				snmpAgent.transmit(new SNMPResponse(InstantMilliseconds.now(), mib));
			}
		}
	}

	@Reception
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
	protected void createValues()
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
	}

	@Override
	protected void createFlows()
	{
		sizeOut = new VolumeMetersCubic(0.01);
		powerIn = new ElectricalPower(standard50Hz, standard110V, new CurrentAmps(0));
		heatOut = new ConvectiveHeat(new HeatWatts(0));
		weightOut = new ForceNewtons(20);
	}

	@Override
	protected void createFullPorts()
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
	protected void createConnectorFunctions()
	{
		ethernetToIPConnectorsFunction = () ->
		{
			ethernet0.addConnectedPortClient(ip);
			ethernet1.addConnectedPortClient(ip);
			ethernet2.addConnectedPortClient(ip);
			ethernet3.addConnectedPortClient(ip);
			ipRouting.addConnectedPortServer(ethernet0);
			ipRouting.addConnectedPortServer(ethernet1);
			ipRouting.addConnectedPortServer(ethernet2);
			ipRouting.addConnectedPortServer(ethernet3);
		};
		electronicToMechanicalPowerOnOffSwitchConnectorFunction = () ->
		{
			electronicPowerOnOffSwitch.addConnectedPortPeer(mechanicalPowerOnOffSwitch);
		};
	}

	@Override
	protected void createConnectors()
	{
		ethernetToIPConnectors = new SysMLAssociationBlockConnector(this, this, ethernetToIPConnectorsFunction);
		electronicToMechanicalPowerOnOffSwitchConnector = new SysMLAssociationBlockConnector(this, this, electronicToMechanicalPowerOnOffSwitchConnectorFunction);
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
