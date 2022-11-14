package sysmlinjavalibrary.components.communications.common.objects;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import sysmlinjava.annotations.Attribute;
import sysmlinjava.common.SysMLClass;
import sysmlinjavalibrary.common.objects.information.MIB;

public class MIBQuery extends SysMLClass
{
	@Attribute
	public Optional<Boolean> first;
	@Attribute
	public Optional<Boolean> last;
	@Attribute
	public Optional<Instant> allAfter;
	@Attribute
	public Optional<Instant> allBefore;
	@Attribute
	public Optional<Boolean> all;

	public Optional<Function<List<MIB>, List<MIB>>> perFunction;
}