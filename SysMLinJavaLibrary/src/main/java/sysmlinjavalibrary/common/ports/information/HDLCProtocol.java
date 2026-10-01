package sysmlinjavalibrary.common.ports.information;

import sysmlinjava.annotations.SysMLHyperlink;
import sysmlinjava.common.SysMLAnything;
import sysmlinjava.events.SysMLSignalEvent;
import sysmlinjava.items.SysMLSignal;
import sysmlinjava.javaannotations.annotations.Hyperlink;
import sysmlinjava.javaannotations.requirements.RequirementReference;
import sysmlinjava.parts.SysMLPart;
import sysmlinjava.ports.SysMLPort;
import sysmlinjavalibrary.common.objects.information.HDLCFrame;
import sysmlinjavalibrary.common.signals.HDLCFrameSignal;

public class HDLCProtocol extends SysMLPort
{
	public HDLCProtocol(SysMLPart parent, Long id)
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
			result = new SysMLSignalEvent(signal, "HDLCFrameEvent", 0L);
		return result;
	}

	@Override
	protected SysMLAnything clientObjectFor(SysMLSignal signal)
	{
		SysMLAnything result = null;
		if(signal instanceof HDLCFrameSignal)
			result = ((HDLCFrameSignal)signal).frame;
		return result;
	}

	@Override
	protected SysMLSignal signalFor(SysMLAnything object)
	{
		SysMLSignal result = null;
		if(object instanceof HDLCFrame)
			result = new HDLCFrameSignal(((HDLCFrame)object));
		return result;
	}

	@Override
	protected void createSupportingInformationLinks()
	{
		protocolStandard = new SysMLHyperlink("", "file://IRS for Protocol");
	}
}
