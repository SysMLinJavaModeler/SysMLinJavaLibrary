package sysmlinjavalibrary.components.communications.siprnet;

import java.util.Optional;
import sysmlinjava.annotations.FullPort;
import sysmlinjava.annotations.Reception;
import sysmlinjava.annotations.Value;
import sysmlinjava.ports.SysMLFullPort;
import sysmlinjava.valuetypes.Cost$US;
import sysmlinjava.valuetypes.HeatWatts;
import sysmlinjava.valuetypes.MassKilograms;
import sysmlinjava.valuetypes.PowerWatts;
import sysmlinjava.valuetypes.VolumeMetersCubic;
import sysmlinjavalibrary.common.objects.information.IPPacket;
import sysmlinjavalibrary.common.ports.energy.electrical.ElectricalPowerSink;
import sysmlinjavalibrary.common.ports.energy.mechanical.RackMountStructure;
import sysmlinjavalibrary.common.ports.energy.thermal.ConvectiveHeatSink;
import sysmlinjavalibrary.common.ports.information.EthernetProtocol;
import sysmlinjavalibrary.common.ports.information.InternetProtocol;

public class HighAssuranceIPEncryptor extends SysMLFullPort
{
	@FullPort
	public InternetProtocol ipEncrypted;
	@FullPort
	public EthernetProtocol ethernetEncrypted;
	@FullPort
	public InternetProtocol ipDecrypted;
	@FullPort
	public EthernetProtocol ethernetDecrypted;
	@FullPort
	public ElectricalPowerSink electricalPower;
	@FullPort
	public ConvectiveHeatSink convectiveHeat;
	@FullPort
	public RackMountStructure rackMountStructuralPort;
	
	@Value
	public VolumeMetersCubic maxSize;
	@Value
	public MassKilograms maxWeight;
	@Value
	public PowerWatts maxPowerIn;
	@Value
	public HeatWatts maxHeatOut;
	@Value
	public Cost$US maxCost; 

	public HighAssuranceIPEncryptor(SIPRNetRouter siprNetRouter, Long id)
	{
		super(siprNetRouter, id);
	}

	@Reception
	public void onDecryptedPacket(IPPacket decryptedPacket)
	{
		IPPacket encryptedPacket = encryptedOf(decryptedPacket);
		ipEncrypted.transmit(encryptedPacket);
	}

	@Reception
	public void onEncryptedPacket(IPPacket encryptedPacket)
	{
		IPPacket decryptedPacket = decryptedOf(encryptedPacket);
		ipDecrypted.transmit(decryptedPacket);
	}

	private IPPacket encryptedOf(IPPacket decryptedPacket)
	{
		return decryptedPacket;
	}

	private IPPacket decryptedOf(IPPacket encryptedPacket)
	{
		return encryptedPacket;
	}

	@Override
	protected void createStateMachine()
	{
		stateMachine = Optional.of(new HighAssuranceIPEncryptorStateMachine(this));
	}

	@Override
	protected void createValues()
	{
		maxSize = new VolumeMetersCubic(0.08);
		maxWeight = new MassKilograms(0.5);
		maxPowerIn = new PowerWatts(15);
		maxHeatOut = new HeatWatts(15);
		maxCost = new Cost$US(1000);
	}
	
	@Override
	protected void createFullPorts()
	{
		super.createFullPorts();
		ipDecrypted = new InternetProtocol(this, 1L);
		ethernetDecrypted = new EthernetProtocol(this, 1L);
		ipEncrypted = new InternetProtocol(this, 0L);
		ethernetEncrypted = new EthernetProtocol(this, 0L);
		electricalPower = new ElectricalPowerSink(this, 0L);
		convectiveHeat = new ConvectiveHeatSink(this, 0L);
		rackMountStructuralPort = new RackMountStructure(this, 0L);
	}
}
