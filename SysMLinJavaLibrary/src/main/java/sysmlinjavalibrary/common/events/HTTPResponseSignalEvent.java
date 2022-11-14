package sysmlinjavalibrary.common.events;

import sysmlinjava.events.SysMLSignalEvent;
import sysmlinjavalibrary.common.objects.information.HTTPResponse;
import sysmlinjavalibrary.common.signals.HTTPResponseSignal;

/**
 * Event for the receipt of an HTTP response
 * 
 * @author ModelerOne
 *
 */
public class HTTPResponseSignalEvent extends SysMLSignalEvent
{
	public HTTPResponseSignalEvent(HTTPResponseSignal httpResponseSignal)
	{
		super("HTTPResponse");
		((HTTPResponseSignal)signal).response = new HTTPResponse(httpResponseSignal.response);
	}

	public HTTPResponse getResponse()
	{
		return ((HTTPResponseSignal)signal).response;
	}

	@Override
	public String toString()
	{
		return String.format("HTTPResponseEvent [signal=%s]", signal);
	}

	@Override
	public void createSignal()
	{
		signal = new HTTPResponseSignal(new HTTPResponse());
	}
}
