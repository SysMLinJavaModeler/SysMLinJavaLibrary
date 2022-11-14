package sysmlinjavalibrary.common.objects.information;

import sysmlinjava.analysis.common.StackedProtocolObject;
import sysmlinjava.annotations.Attribute;
import sysmlinjava.common.SysMLClass;
import sysmlinjava.valuetypes.InstantMilliseconds;

/**
 * The {@code SNMPResponse} is the SysMLinJava model of a SNMP response for use
 * in SNMP protocol network/device management communications. It is a simplified
 * model of the response which contains a {@code MIB} and a time. The
 * {@code SNMPResponse} also implements the {@code StackedProtocolObject}
 * interface.
 * 
 * @author ModelerOne
 *
 */
public class SNMPResponse extends SysMLClass implements StackedProtocolObject
{
	@Attribute
	public InstantMilliseconds time;
	@Attribute
	public MIB mib;

	public SNMPResponse(InstantMilliseconds time, MIB mib)
	{
		super();
		this.time = time;
		this.mib = mib;
	}

	public SNMPResponse()
	{
		super();
		this.time = InstantMilliseconds.now();
		this.mib = new MIB();
	}

	@Override
	public String stackNamesString()
	{
		return String.format("%s(state=%s)", getClass().getSimpleName(), mib.getDataStrings().get(1));
	}

	@Override
	public String toString()
	{
		return String.format("SNMPResponse [time=%s, mib=%s]", time, mib);
	}
}
