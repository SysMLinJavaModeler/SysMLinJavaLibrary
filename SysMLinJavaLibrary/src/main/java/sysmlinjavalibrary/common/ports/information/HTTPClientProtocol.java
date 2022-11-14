package sysmlinjavalibrary.common.ports.information;

import java.util.Optional;
import java.util.concurrent.ArrayBlockingQueue;
import sysmlinjava.annotations.Operation;
import sysmlinjava.annotations.Reception;
import sysmlinjava.blocks.SysMLBlock;
import sysmlinjava.common.SysMLClass;
import sysmlinjava.common.SysMLSignal;
import sysmlinjava.events.SysMLSignalEvent;
import sysmlinjava.ports.SysMLFullPort;
import sysmlinjavalibrary.common.events.HTTPResponseSignalEvent;
import sysmlinjavalibrary.common.objects.information.HTTPRequest;
import sysmlinjavalibrary.common.objects.information.HTTPResponse;
import sysmlinjavalibrary.common.signals.HTTPRequestSignal;
import sysmlinjavalibrary.common.signals.HTTPResponseSignal;

public class HTTPClientProtocol extends SysMLFullPort
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
	public HTTPClientProtocol(SysMLBlock contextBlock, Long id, String name)
	{
		super(contextBlock, id);
		this.eventContextBlock = Optional.of(this);
		responseQueue = new ArrayBlockingQueue<>(10);
	}

	/**
	 * Sends specified HTTP request and waits to receive corresponding HTTP
	 * response, thereby simulating the sequential HTTP request/response protocol.
	 * 
	 * @param httpRequest HTTP request to be transmitted
	 * @return HTTP response received for the transmitted HTTP request
	 */
	@Operation
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
	@Reception
	public void onHTTPResponse(HTTPResponse response)
	{
		responseQueue.add(response);
	}

	@Override
	protected SysMLSignalEvent eventFor(SysMLSignal signal)
	{
		SysMLSignalEvent result = null;
		if (signal instanceof HTTPResponseSignal)
		{
			HTTPResponseSignal httpResponseSignal = (HTTPResponseSignal)signal;
			result = new HTTPResponseSignalEvent(httpResponseSignal);
		}
		else
			logger.severe("unexpected signal type: " + signal.getClass().getSimpleName() + ", i.e. not a HTTPResponseSignal");
		return result;
	}

	@Override
	protected SysMLSignal signalFor(SysMLClass object)
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
