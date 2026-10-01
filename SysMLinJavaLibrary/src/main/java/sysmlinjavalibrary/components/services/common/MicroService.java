package sysmlinjavalibrary.components.services.common;

import sysmlinjava.javaannotations.ports.Port;
import sysmlinjava.parts.SysMLPart;
import sysmlinjavalibrary.common.ports.information.MessagingProtocol;

public class MicroService extends SysMLPart
{
	@Port
	public MessagingProtocol messaging;
	
	public MicroService(String name, long id)
	{
		super(name, id);
	}
	
	@Override
	protected void createPorts()
	{
		messaging = new MessagingProtocol(this, 0L);
	}
}
