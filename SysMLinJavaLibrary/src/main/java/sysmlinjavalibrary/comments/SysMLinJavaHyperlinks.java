package sysmlinjavalibrary.comments;

import sysmlinjava.annotations.comments.Hyperlink;
import sysmlinjava.blocks.SysMLBlock;
import sysmlinjava.comments.SysMLHyperlink;

/**
 * Collection of {@code SysMLHyperlink}s. These hyperlinks can be used to
 * specify/initialize a {@code SysMLHyperlink} variable in a block, interface
 * block, or other SysMLinJava model element. Initialization is performed in the
 * block or interface block's {@code createHyperlinks()} method as follows:<br>
 * 
 * <pre>
 * {
 * 	&#64;code
 * 	public class MyBlock extends SysMLBlock
 * 	{
 * 		public void createHyperlinks()
 * 		{
 * 			myEthernetHyperlink = SysMLinHyperlink.Ethernet;
 * 			myAlternateEthernetHyperlink = new SysMLHyperlink("IEEE802.3bp - Industrial Gigabit Ethernet", "");
 * 		}
 * 	}
 * }
 * </pre>
 * 
 * @author ModelerOne
 *
 */
public final class SysMLinJavaHyperlinks extends SysMLBlock
{
	@Hyperlink
	public static SysMLHyperlink Ethernet = new SysMLHyperlink("IEEE802.3 - Ethernet", "");
	@Hyperlink
	public static SysMLHyperlink IP = new SysMLHyperlink("IP - Internet Protocol", "");
	@Hyperlink
	public static SysMLHyperlink TCP = new SysMLHyperlink("TCP - Transmission Control Protocol", "");
	@Hyperlink
	public static SysMLHyperlink UDP = new SysMLHyperlink("UDP - User Datagram Protocol", "https://tools.ietf.org/html/rfc768");
	@Hyperlink
	public static SysMLHyperlink HTTP = new SysMLHyperlink("HTTP - HyperText Transfer Protocol", "");
	@Hyperlink
	public static SysMLHyperlink SNMP = new SysMLHyperlink("SNMP - Simple Network Management Protocol", "");
	@Hyperlink
	public static SysMLHyperlink DDS = new SysMLHyperlink("DDS - Data Distribution Service Messaging Protocol", "");
	@Hyperlink
	public static SysMLHyperlink MILSTD456 = new SysMLHyperlink("MIL-STD 456 - MilStd Radar Messaging Protocol", "");
	@Hyperlink
	public static SysMLHyperlink MILSTD789 = new SysMLHyperlink("MIL-STD 789 - MilStd Strike Messaging Protocol", "");
	@Hyperlink
	public static SysMLHyperlink MILSTD8888 = new SysMLHyperlink("MIL-STD 8888 - MilStd Strike Target Positions Protocol", "");
	@Hyperlink
	public static SysMLHyperlink DataLink = new SysMLHyperlink("Data Link - A data link protocol", "");
	@Hyperlink
	public static SysMLHyperlink GPS = new SysMLHyperlink("GPS - GPS user interface messaging protocol", "");
	@Hyperlink
	public static SysMLHyperlink TDMA = new SysMLHyperlink("TDMA - Time division multiplexed protocol", "");
	@Hyperlink
	public static SysMLHyperlink PSK = new SysMLHyperlink("PSK - Phase-shift keyed protocol", "");
	@Hyperlink
	public static SysMLHyperlink HF = new SysMLHyperlink("HF - High frequency band", "");
	@Hyperlink
	public static SysMLHyperlink PC = new SysMLHyperlink("PC - Personal computer interface protocol", "");
	@Hyperlink
	public static SysMLHyperlink Desktop = new SysMLHyperlink("Desktop - Personal computer desktop interface protocol", "");
	@Hyperlink
	public static SysMLHyperlink Application = new SysMLHyperlink("App - Personal computer desktop application interface protocol", "");

	@Hyperlink
	public static SysMLHyperlink sysmlinjava = new SysMLHyperlink("SysMLinJava", "www.sysmlinjava.com");
	@Hyperlink
	public static SysMLHyperlink sysml = new SysMLHyperlink("OMG SysML", "http://www.omgsysml.org/specifications.htm");
}
