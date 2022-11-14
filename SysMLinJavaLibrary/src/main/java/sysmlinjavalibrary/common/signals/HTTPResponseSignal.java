package sysmlinjavalibrary.common.signals;

import sysmlinjava.common.SysMLSignal;
import sysmlinjavalibrary.common.objects.information.HTTPResponse;

public class HTTPResponseSignal extends SysMLSignal
{
	public HTTPResponse response;

	public HTTPResponseSignal(HTTPResponse response)
	{
		super();
		this.response = response;
	}
}
