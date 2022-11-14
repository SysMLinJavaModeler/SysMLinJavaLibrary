package sysmlinjavalibrary.common.signals;

import sysmlinjava.common.SysMLSignal;
import sysmlinjavalibrary.common.objects.information.HTTPRequest;

public class HTTPRequestSignal extends SysMLSignal
{
	public HTTPRequest request;

	public HTTPRequestSignal(HTTPRequest request)
	{
		super();
		this.request = request;
	}
}
