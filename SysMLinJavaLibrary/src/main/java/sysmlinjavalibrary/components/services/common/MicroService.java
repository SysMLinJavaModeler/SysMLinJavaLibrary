package sysmlinjavalibrary.components.services.common;

import sysmlinjava.annotations.FullPort;
import sysmlinjava.blocks.SysMLBlock;
import sysmlinjavalibrary.common.ports.information.MessagingProtocol;

public class MicroService extends SysMLBlock
{
	@FullPort
	public MessagingProtocol messaging;
	
	public MicroService(String name, long id)
	{
		super(name, id);
	}
	
	@Override
	protected void createFullPorts()
	{
		messaging = new MessagingProtocol(this, 0L);
	}
}
