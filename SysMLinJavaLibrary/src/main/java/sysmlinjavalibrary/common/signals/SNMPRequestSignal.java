package sysmlinjavalibrary.common.signals;

import sysmlinjava.annotations.Attribute;
import sysmlinjava.common.SysMLSignal;
import sysmlinjavalibrary.common.objects.information.SNMPRequest;

public class SNMPRequestSignal extends SysMLSignal
{
	@Attribute
	public SNMPRequest request;

	public SNMPRequestSignal(SNMPRequest request)
	{
		this.request = request;
	}

	@Override
	public String stackNamesString()
	{
		return request.stackNamesString();
	}

	@Override
	public String toString()
	{
		return String.format("SNMPRequestSignal [request=%s]", request);
	}
}
