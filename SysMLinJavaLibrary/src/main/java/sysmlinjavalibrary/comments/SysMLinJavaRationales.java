package sysmlinjavalibrary.comments;

import sysmlinjava.annotations.comments.Rationale;
import sysmlinjava.blocks.SysMLBlock;
import sysmlinjava.comments.SysMLRationale;

/**
 * Collection of {@code SysMLRationale}s. These rationales can be used to
 * specify/initialize a {@code SysMLRationale} variable in a block, interface block, or
 * other SysMLinJava model element. Initialization is performed in the block or
 * interface block's {@code createRationales()} method as follows:<br>
 * 
 * <pre>
 * {@code
	public class MyBlock extends SysMLBlock
	{
		public void createRationales()
		{
			myGeneralRationale = SysMLinJavaRationales.lowerRisk;
			mySpecificRationale = new SysMLRationale("Lowers risk because of better supply chain");
		}
 	}}
 * </pre>
 * 
 * @author ModelerOne
 *
 */
public final class SysMLinJavaRationales extends SysMLBlock
{
	@Rationale
	public static final SysMLRationale lowerRisk = new SysMLRationale("Model element presents lower risk");
	@Rationale
	public static final SysMLRationale lowerCosts = new SysMLRationale("Model element presents lower costs");
	@Rationale
	public static final SysMLRationale greaterBenefits = new SysMLRationale("Model element presents greater benefits");
	@Rationale
	public static final SysMLRationale lesserCostPerBenefits = new SysMLRationale("Model element presents lower cost/benefits");
	@Rationale
	public static final SysMLRationale higherPerformance = new SysMLRationale("Model element presents higher measure of performance (MOP)");
	@Rationale
	public static final SysMLRationale greaterEffectiveness = new SysMLRationale("Model element presents greater measure of effectiveness (MOE)");
	@Rationale
	public static final SysMLRationale greaterSuitability = new SysMLRationale("Model element presents greater measure of suitability (MOS)");
}
