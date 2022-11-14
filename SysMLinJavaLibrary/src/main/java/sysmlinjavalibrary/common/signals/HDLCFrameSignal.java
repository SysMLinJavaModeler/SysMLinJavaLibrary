package sysmlinjavalibrary.common.signals;

import java.util.Optional;
import sysmlinjava.annotations.Attribute;
import sysmlinjava.common.SysMLSignal;
import sysmlinjavalibrary.common.objects.information.HDLCFrame;

public class HDLCFrameSignal extends SysMLSignal
{
	@Attribute
	public HDLCFrame frame;

	public HDLCFrameSignal(HDLCFrame frame)
	{
		super();
		this.frame = frame;
	}

	@Override
	public String stackNamesString()
	{
		return stackNamesString(this, Optional.empty());
	}
}
