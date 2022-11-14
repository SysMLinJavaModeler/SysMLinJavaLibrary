package sysmlinjavalibrary.components.communications.siprnet;

import static sysmlinjava.valuetypes.ElectricalPower.standard110V;
import static sysmlinjava.valuetypes.ElectricalPower.standard50Hz;
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

public class SIPRNetRouter extends SysMLBlock
{
	@FullPort
	public HighAssuranceIPEncryptor haipe;
	@FullPort
	public InternetProtocol ipEncrypted;
	@FullPort
	public EthernetProtocol ethernetEncrypted;
	@FullPort
	public HDLCProtocol hdlc;
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
	public CurrentAmps minCurrentIn;
	@Value
	public CurrentAmps maxCurrentIn;
	@Value
	public PowerWatts maxPowerIn;
	@Value
	public HeatWatts maxHeatOut;
	@Value
	public Cost$US maxCost;
	@Value
	public QuantityEach numberMountPoints;

	@AssociationConnectorFunction
	public SysMLAssociationBlockConnectorFunction routerToHAIPEConnectorFunction;
	@AssociationConnectorFunction
	public SysMLAssociationBlockConnectorFunction electronicToMechanicalPowerOnOffSwitchConnectorFunction;

	@AssociationConnector
	public SysMLAssociationBlockConnector routerToHAIPEConnector;
	@AssociationConnector
	public SysMLAssociationBlockConnector electronicToMechanicalPowerOnOffSwitchConnector;
	@AssociationConnector
	public IInteger rackMountHole;

	public SIPRNetRouter(String name, long id)
	{
		super(name, id);
	}

	public SIPRNetRouter()
	{
		super("SIPRNetRouter", 0L);
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
	public void onIPPacketHAIPE(IPPacket ipPacket)
	{
		logger.info(ipPacket.toString());
		Integer source = ipPacket.sourceAddress;
		Integer destination = ipPacket.destinationAddress;
		hdlc.transmit(new HDLCFrame(source, destination, ipPacket));
	}

	@Reception
	public void onIPPacketDataLink(IPPacket packet)
	{
		logger.info(packet.toString());
		Integer source = packet.sourceAddress;
		Integer destination = packet.destinationAddress;
		haipe.transmit(new IPPacket(source, destination, packet.data));
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
				mib = new MIB(InstantMilliseconds.now(), this.getClass().getSimpleName(), SIPRNetRouterStatesEnum.Operational.toString());
				snmpAgent.transmit(new SNMPResponse(InstantMilliseconds.now(), mib));
			}
		}
	}

	@Reception
	public void onSNMPRequestToPowerOff()
	{
		logger.info("control to power off");
		haipe.stop();
		electronicPowerOnOffSwitch.transmit(new OnOffSwitch(false));
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
			haipe.start();
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
	protected void createValues()
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
	}

	@Override
	protected void createFlows()
	{
		sizeOut = new VolumeMetersCubic(0.03);
		powerIn = new ElectricalPower(standard50Hz, standard110V, new CurrentAmps(0));
		heatOut = new ConvectiveHeat(new HeatWatts(0));
		weightOut = new ForceNewtons(40);
	}

	@Override
	protected void createFullPorts()
	{
		super.createFullPorts();
		haipe = new HighAssuranceIPEncryptor(this, 0L);
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
	protected void createConnectorFunctions()
	{
		super.createConnectorFunctions();
		routerToHAIPEConnectorFunction = () ->
		{
			ipEncrypted.addConnectedPortPeer(haipe.ipEncrypted);
			haipe.ipEncrypted.addConnectedPortPeer(ipEncrypted);
			ethernetEncrypted.addConnectedPortPeer(haipe.ethernetEncrypted);
			haipe.ethernetEncrypted.addConnectedPortPeer(ethernetEncrypted);
		};
		electronicToMechanicalPowerOnOffSwitchConnectorFunction = () ->
		{
			electronicPowerOnOffSwitch.addConnectedPortPeer(mechanicalPowerOnOffSwitch);
		};
	}

	@Override
	protected void createConnectors()
	{
		super.createConnectors();
		routerToHAIPEConnector = new SysMLAssociationBlockConnector(this, haipe, routerToHAIPEConnectorFunction);
		electronicToMechanicalPowerOnOffSwitchConnector = new SysMLAssociationBlockConnector(this, this, electronicToMechanicalPowerOnOffSwitchConnectorFunction);
	}
}
