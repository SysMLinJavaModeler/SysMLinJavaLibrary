package sysmlinjavalibrary.common.ports.information;

import sysmlinjava.annotations.comments.Hyperlink;
import sysmlinjava.annotations.requirements.RequirementReference;
import sysmlinjava.blocks.SysMLBlock;
import sysmlinjava.comments.SysMLHyperlink;
import sysmlinjava.common.SysMLClass;
import sysmlinjava.common.SysMLSignal;
import sysmlinjava.events.SysMLSignalEvent;
import sysmlinjava.ports.SysMLFullPort;
import sysmlinjavalibrary.common.events.HDLCFrameEvent;
import sysmlinjavalibrary.common.objects.information.HDLCFrame;
import sysmlinjavalibrary.common.signals.HDLCFrameSignal;

public class HDLCProtocol extends SysMLFullPort
{
	public HDLCProtocol(SysMLBlock parent, Long id)
	{
		super(parent, id);
	}

	@RequirementReference
	@Hyperlink
	public SysMLHyperlink protocolStandard;

	@Override
	protected SysMLSignalEvent eventFor(SysMLSignal signal)
	{
		SysMLSignalEvent result = null;
		if(signal instanceof HDLCFrameSignal)
			result = new HDLCFrameEvent(((HDLCFrameSignal)signal).frame);
		return result;
	}

	@Override
	protected SysMLClass clientObjectFor(SysMLSignal signal)
	{
		SysMLClass result = null;
		if(signal instanceof HDLCFrameSignal)
			result = ((HDLCFrameSignal)signal).frame;
		return result;
	}

	@Override
	protected SysMLSignal signalFor(SysMLClass object)
	{
		SysMLSignal result = null;
		if(object instanceof HDLCFrame)
			result = new HDLCFrameSignal(((HDLCFrame)object));
		return result;
	}

	@Override
	protected void createHyperlinks()
	{
		protocolStandard = new SysMLHyperlink("", "file://IRS for Protocol");
	}

}
