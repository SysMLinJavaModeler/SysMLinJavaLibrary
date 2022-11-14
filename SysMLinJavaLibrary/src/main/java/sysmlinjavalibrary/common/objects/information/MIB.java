package sysmlinjavalibrary.common.objects.information;

import java.util.ArrayList;
import java.util.List;
import sysmlinjava.analysis.common.StackedProtocolObject;
import sysmlinjava.annotations.Attribute;
import sysmlinjava.common.SysMLClass;
import sysmlinjava.valuetypes.InstantMilliseconds;

/**
 * The {@code MIB} is a SysMLinJava model of a MIB object as used in the SNMP
 * protocol. It is a (n over)simplified simulation of the real MIB, but provides
 * the ability to send text-based MIB data via the SNMP protocol. The {@code MIB} also implements the
 * {@code StackedProtocolObject} interface.
 * 
 * @author ModelerOne
 *
 */
public class MIB extends SysMLClass implements StackedProtocolObject
{
	public static final String format = "system=%s, state=%s";

	@Attribute
	public InstantMilliseconds time;
	@Attribute
	public String dataString;

	public MIB(InstantMilliseconds time, String system, String state)
	{
		super();
		this.time = time;
		dataString = String.format(format, system, state);
	}

	public MIB()
	{
		this.time = InstantMilliseconds.now();
		dataString = String.format(format, "not specified", "not speccified");
	}

	public List<String> getDataStrings()
	{
		List<String> result = new ArrayList<>();
		int commaIndex = dataString.indexOf(", ");
		String serviceString = dataString.substring(0, commaIndex);
		String stateString = dataString.substring(commaIndex + 2, dataString.length());
		result.add(serviceString.split("=")[1]);
		result.add(stateString.split("=")[1]);
		return result;
	}

	@Override
	public String toString()
	{
		return String.format("MIB [time=%s, dataString=%s]", time, dataString);
	}
}
