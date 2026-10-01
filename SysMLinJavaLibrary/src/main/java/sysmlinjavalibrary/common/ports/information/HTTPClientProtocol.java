package sysmlinjavalibrary.common.ports.information;

import java.util.Optional;
import java.util.concurrent.ArrayBlockingQueue;
import sysmlinjava.common.SysMLAnything;
import sysmlinjava.events.SysMLSignalEvent;
import sysmlinjava.items.SysMLSignal;
import sysmlinjava.javaannotations.actions.Action;
import sysmlinjava.parts.SysMLPart;
import sysmlinjava.ports.SysMLPort;
import sysmlinjavalibrary.common.objects.information.HTTPRequest;
import sysmlinjavalibrary.common.objects.information.HTTPResponse;
import sysmlinjavalibrary.common.signals.HTTPRequestSignal;
import sysmlinjavalibrary.common.signals.HTTPResponseSignal;

public class HTTPClientProtocol extends SysMLPort
{
	/**
	 * Queue for receiving HTTP responses asynchronously with transmit of HTTP
	 * requests
	 */
	public ArrayBlockingQueue<HTTPResponse> responseQueue;

	/**
	 * Constructor for protocol that operates asynchronously from user (transmit()
	 * caller)
	 * 
	 * @param contextBlock block in whose context the port resides
	 * @param id           unique ID
	 * @param name         name of the protocol
	 */
	public HTTPClientProtocol(SysMLPart contextBlock, Long id, String name)
	{
		super(contextBlock, id);
		eventContext = Optional.of(this);
		responseQueue = new ArrayBlockingQueue<>(10);
	}

	/**
	 * Sends specified HTTP request and waits to receive corresponding HTTP
	 * response, thereby simulating the sequential HTTP request/response protocol.
	 * 
	 * @param httpRequest HTTP request to be transmitted
	 * @return HTTP response received for the transmitted HTTP request
	 */
	@Action
	public HTTPResponse send(HTTPRequest httpRequest)
	{
		HTTPResponse result = null;
		transmit(httpRequest);
		try
		{
			result = responseQueue.take();
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Handler for the receipt of the HTTP response. This reception is called by the
	 * {@code HTTPClientProtocol}'s state machine operating asyncronously to the
	 * {@code send} operation. It queues the response to the {@code responseQueue}
	 * on which the {@code send()} operation waits to receive the response.
	 * 
	 * @param response the HTTP response received.
	 */
	@Action
	public void onHTTPResponse(HTTPResponse response)
	{
		responseQueue.add(response);
	}

	@Override
	protected SysMLSignalEvent eventFor(SysMLSignal signal)
	{
		SysMLSignalEvent result = null;
		if (signal instanceof HTTPResponseSignal)
			result = new SysMLSignalEvent(signal, "HTTPResponseEvent", 0L);
		else
			logger.severe("unexpected signal type: " + signal.getClass().getSimpleName() + ", i.e. not a HTTPResponseSignal");
		return result;
	}

	@Override
	protected SysMLSignal signalFor(SysMLAnything object)
	{
		SysMLSignal result = null;
		if (object instanceof HTTPRequest)
		{
			HTTPRequest httpRequest = (HTTPRequest)object;
			result = new HTTPRequestSignal(httpRequest);
		}
		else
			logger.severe("unexpected object type: " + object.getClass().getSimpleName() + ", i.e. not a HTTPRequest");
		return result;
	}

	@Override
	protected void createStateMachine()
	{
		stateMachine = Optional.of(new HTTPClientProtocolStateMachine(this));
	}
}
