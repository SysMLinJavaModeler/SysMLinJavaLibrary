package sysmlinjavalibrary.common.events;

import sysmlinjava.events.SysMLSignalEvent;
import sysmlinjavalibrary.common.objects.information.HTTPRequest;
import sysmlinjavalibrary.common.signals.HTTPRequestSignal;

/**
 * Event for the receipt of an HTTP request
 * 
 * @author ModelerOne
 *
 */
public class HTTPRequestSignalEvent extends SysMLSignalEvent
{
	public HTTPRequestSignalEvent(HTTPRequestSignal httpRequestSignal)
	{
		super("HTTPRequest");
		((HTTPRequestSignal)signal).request = new HTTPRequest(httpRequestSignal.request);
	}

	public HTTPRequestSignalEvent(HTTPRequestSignal httpRequestSignal, Integer index)
	{
		this(httpRequestSignal);
		this.index = index;
	}

	public HTTPRequest getRequest()
	{
		return ((HTTPRequestSignal)signal).request;
	}

	@Override
	public String toString()
	{
		return String.format("HTTPRequestEvent [signal=%s]", signal);
	}

	@Override
	public void createSignal()
	{
		signal = new HTTPRequestSignal(new HTTPRequest());
	}
}
