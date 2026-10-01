package sysmlinjavalibrary.components.communications.siprnet;

import java.util.Optional;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import sysmlinjava.attributetypes.Cost$US;
import sysmlinjava.attributetypes.HeatWatts;
import sysmlinjava.attributetypes.MassKilograms;
import sysmlinjava.attributetypes.PowerWatts;
import sysmlinjava.attributetypes.VolumeMetersCubic;
import sysmlinjava.javaannotations.actions.Action;
import sysmlinjava.javaannotations.attributes.Attribute;
import sysmlinjava.javaannotations.ports.Port;
import sysmlinjava.ports.SysMLPort;
import sysmlinjava.states.StateBehaviorContext;
import sysmlinjavalibrary.common.objects.information.IPPacket;
import sysmlinjavalibrary.common.ports.energy.electrical.ElectricalPowerSink;
import sysmlinjavalibrary.common.ports.energy.mechanical.RackMountStructure;
import sysmlinjavalibrary.common.ports.energy.thermal.ConvectiveHeatSink;
import sysmlinjavalibrary.common.ports.information.EthernetProtocol;
import sysmlinjavalibrary.common.ports.information.InternetProtocol;

public class HighAssuranceIPEncryptor extends SysMLPort implements StateBehaviorContext
{
	@Port
	public InternetProtocol ipEncrypted;
	@Port
	public EthernetProtocol ethernetEncrypted;
	@Port
	public InternetProtocol ipDecrypted;
	@Port
	public EthernetProtocol ethernetDecrypted;
	@Port
	public ElectricalPowerSink electricalPower;
	@Port
	public ConvectiveHeatSink convectiveHeat;
	@Port
	public RackMountStructure rackMountStructuralPort;

	@Attribute
	public VolumeMetersCubic maxSize;
	@Attribute
	public MassKilograms maxWeight;
	@Attribute
	public PowerWatts maxPowerIn;
	@Attribute
	public HeatWatts maxHeatOut;
	@Attribute
	public Cost$US maxCost;

	public HighAssuranceIPEncryptor(SIPRNetRouter siprNetRouter, String name, Long id)
	{
		super(siprNetRouter, id, name);
	}

	@Override
	public ScheduledThreadPoolExecutor getExecutionThreads()
	{
		return context.get().getExecutionThreads();
	}

	@Action
	public void onDecryptedPacket(IPPacket decryptedPacket)
	{
		IPPacket encryptedPacket = encryptedOf(decryptedPacket);
		ipEncrypted.transmit(encryptedPacket);
	}

	@Action
	public void onEncryptedPacket(IPPacket encryptedPacket)
	{
		IPPacket decryptedPacket = decryptedOf(encryptedPacket);
		ipDecrypted.transmit(decryptedPacket);
	}

	private IPPacket encryptedOf(IPPacket decryptedPacket)
	{
		return new IPPacket(decryptedPacket, true);
	}

	private IPPacket decryptedOf(IPPacket encryptedPacket)
	{
		return new IPPacket(encryptedPacket, false);
	}

	@Override
	protected void createStateMachine()
	{
		stateMachine = Optional.of(new HighAssuranceIPEncryptorStateMachine(this));
	}

	@Override
	protected void createAttributes()
	{
		maxSize = new VolumeMetersCubic(0.08);
		maxWeight = new MassKilograms(0.5);
		maxPowerIn = new PowerWatts(15);
		maxHeatOut = new HeatWatts(15);
		maxCost = new Cost$US(1000);
	}

	@Override
	protected void createPorts()
	{
		super.createPorts();
		ipDecrypted = new InternetProtocol(this, 1L);
		ethernetDecrypted = new EthernetProtocol(this, 1L);
		ipEncrypted = new InternetProtocol(this, 2L);
		ethernetEncrypted = new EthernetProtocol(this, 2L);
		electricalPower = new ElectricalPowerSink(this, 0L);
		convectiveHeat = new ConvectiveHeatSink(this, 0L);
		rackMountStructuralPort = new RackMountStructure(this, 0L);
	}
}
