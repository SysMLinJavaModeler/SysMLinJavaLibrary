package sysmlinjavalibrary.comments;

import java.util.List;
import java.util.Optional;
import sysmlinjava.annotations.comments.ElementGroup;
import sysmlinjava.blocks.SysMLBlock;
import sysmlinjava.comments.SysMLElementGroup;
import sysmlinjava.quantitykinds.SysMLinJavaQuantityKinds;
import sysmlinjava.units.SysMLUnits;
import sysmlinjava.valuetypes.BBoolean;
import sysmlinjava.valuetypes.IInteger;
import sysmlinjava.valuetypes.RReal;
import sysmlinjava.valuetypes.SString;

/**
 * SysMLinJava collection of commonly used SysML element groups.
 * 
 * @author ModelerOne
 *
 */
public final class SysMLinJavaElementGroups extends SysMLBlock
{
		@ElementGroup
		public static SysMLElementGroup sysMLinJavaBaseValueTypes = new SysMLElementGroup("SysMLinJavaBaseValueTypes", "SysMLinJava's base valueTypes", "Basic (primitive) types of SysMLValueTypes", Optional.of(List.of(RReal.class, IInteger.class, BBoolean.class, SString.class)), Optional.empty());
		@ElementGroup
		public static SysMLElementGroup sysMLinJavaUnits = new SysMLElementGroup("SysMLinJavaUnits", "SysMLinJava's units", "Instances of the SysMLUnit", Optional.empty(), Optional.of(List.of(SysMLUnits.instances)));
		@ElementGroup
		public static SysMLElementGroup sysMLinJavaQuantityKinds = new SysMLElementGroup("SysMLinJavaQuantityKinds", "SysMLinJava's quantityKinds", "Instance of the SysMLQuantityKind", Optional.empty(), Optional.of(List.of(SysMLinJavaQuantityKinds.instances)));
}