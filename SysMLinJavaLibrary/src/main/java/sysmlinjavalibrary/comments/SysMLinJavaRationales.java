package sysmlinjavalibrary.comments;

import sysmlinjava.items.SysMLItem;
import sysmlinjava.javaannotations.metadata.Rationale;
import sysmlinjava.metadata.SysMLRationale;

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
public final class SysMLinJavaRationales extends SysMLItem
{
	/**
	 * Rationale that model element lowers risk
	 */
	@Rationale
	public static final SysMLRationale lowerRisk = new SysMLRationale("Model element presents lower risk", "", 0L);
	/**
	 * Rationale that model element lowers costs
	 */
	@Rationale
	public static final SysMLRationale lowerCosts = new SysMLRationale("Model element presents lower costs", "", 0L);
	/**
	 * Rationale that model element provides for greater benefits
	 */
	@Rationale
	public static final SysMLRationale greaterBenefits = new SysMLRationale("Model element presents greater benefits", "", 0L);
	/**
	 * Rationale that model element presents lower cost/benefit ratio
	 */
	@Rationale
	public static final SysMLRationale lesserCostPerBenefits = new SysMLRationale("Model element presents lower cost/benefits", "", 0L);
	/**
	 * Rationale that model element provides higher performance (mop)
	 */
	@Rationale
	public static final SysMLRationale higherPerformance = new SysMLRationale("Model element presents higher measure of performance (MOP)", "", 0L);
	/**
	 * Rationale that model element provides greater effectiveness (moe)
	 */
	@Rationale
	public static final SysMLRationale greaterEffectiveness = new SysMLRationale("Model element presents greater measure of effectiveness (MOE)", "", 0L);
	/**
	 * Rationale that model element provides greater suitability (mos)
	 */
	@Rationale
	public static final SysMLRationale greaterSuitability = new SysMLRationale("Model element presents greater measure of suitability (MOS)", "", 0L);
}
