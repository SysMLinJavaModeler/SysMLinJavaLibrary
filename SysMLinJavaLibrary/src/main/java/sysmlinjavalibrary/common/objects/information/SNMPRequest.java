package sysmlinjavalibrary.common.objects.information;

import sysmlinjava.analysis.common.StackedProtocolObject;
import sysmlinjava.annotations.Attribute;
import sysmlinjava.common.SysMLClass;
import sysmlinjava.valuetypes.InstantMilliseconds;

/**
 * The {@code SNMPRequest} is the SysMLinJava model of a SNMP request for use in
 * a SNMP protocol network/device management communications. It is a simplified
 * model of the request which contains a {@code MIB} and a time. The
 * {@code SNMPRequest} also implements the {@code StackedProtocolObject}
 * interface.
 * 
 * @author ModelerOne
 *
 */
public class SNMPRequest extends SysMLClass implements StackedProtocolObject
{
	@Attribute
	public InstantMilliseconds time;
	@Attribute
	public MIB mib;

	public SNMPRequest(InstantMilliseconds time, MIB mib)
	{
		super();
		this.time = time;
		this.mib = mib;
	}

	public SNMPRequest()
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
		return String.format("SNMPRequest [time=%s, mib=%s]", time, mib);
	}

}
