package sysmlinjavalibrary.components.communications.common.objects;

import java.util.List;
import sysmlinjava.common.SysMLAnything;
import sysmlinjava.javaannotations.attributes.Attribute;
import sysmlinjavalibrary.common.objects.information.MIB;

public class MIBQueryResult extends SysMLAnything
{
	@Attribute
	public List<MIB> result;

	public MIBQueryResult(List<MIB> result)
	{
		super();
		this.result = result;
	}
}