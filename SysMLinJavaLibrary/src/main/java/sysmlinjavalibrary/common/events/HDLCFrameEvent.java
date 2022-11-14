package sysmlinjavalibrary.common.events;

import sysmlinjava.events.SysMLSignalEvent;
import sysmlinjavalibrary.common.objects.information.HDLCFrame;
import sysmlinjavalibrary.common.objects.information.IPPacket;
import sysmlinjavalibrary.common.signals.HDLCFrameSignal;

public class HDLCFrameEvent extends SysMLSignalEvent
{
	public HDLCFrameEvent(HDLCFrame frame)
	{
		super("HDLCFrame");
		((HDLCFrameSignal)signal).frame.sourceID = frame.sourceID;
		((HDLCFrameSignal)signal).frame.destinationID = frame.destinationID;
		((HDLCFrameSignal)signal).frame.data= new IPPacket(frame.data);
	}

	@Override
	public void createSignal()
	{
		new HDLCFrameSignal(new HDLCFrame());
	}
}
