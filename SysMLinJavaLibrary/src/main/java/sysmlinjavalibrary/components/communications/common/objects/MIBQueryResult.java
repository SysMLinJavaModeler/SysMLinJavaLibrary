package sysmlinjavalibrary.components.communications.common.objects;

import java.util.List;
import sysmlinjava.annotations.Attribute;
import sysmlinjava.common.SysMLClass;
import sysmlinjavalibrary.common.objects.information.MIB;

public class MIBQueryResult extends SysMLClass
{
	@Attribute
	public List<MIB> result;

	public MIBQueryResult(List<MIB> result)
	{
		super();
		this.result = result;
	}
}