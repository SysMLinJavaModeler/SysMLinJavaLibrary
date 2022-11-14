package sysmlinjavalibrary.common.signals;

import sysmlinjava.annotations.Attribute;
import sysmlinjava.common.SysMLSignal;
import sysmlinjavalibrary.common.objects.information.SNMPResponse;

public class SNMPResponseSignal extends SysMLSignal
{
	@Attribute
	public SNMPResponse response;

	public SNMPResponseSignal(SNMPResponse response)
	{
		this.response = response ;
	}

	@Override
	public String stackNamesString()
	{
		return response.stackNamesString();
	}

	@Override
	public String toString()
	{
		return String.format("SNMPResponseSignal [name=%s, id=%s, response=%s]", name, id, response);
	}
}
