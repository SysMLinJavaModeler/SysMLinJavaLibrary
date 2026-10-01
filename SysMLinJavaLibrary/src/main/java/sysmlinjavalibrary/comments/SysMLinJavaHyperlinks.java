package sysmlinjavalibrary.comments;

import sysmlinjava.annotations.SysMLHyperlink;
import sysmlinjava.javaannotations.annotations.Hyperlink;
import sysmlinjava.parts.SysMLPart;

/**
 * Collection of {@code SysMLHyperlinkText}s. These hyperlinks can be used to
 * specify/initialize a {@code SysMLHyperlinkText} variable in a block, interface
 * block, or other SysMLinJava model element. Initialization is performed in the
 * block or interface block's {@code createHyperlinkTexts()} method as follows:<br>
 * 
 * <pre>
 * {
 * 	&#64;code
 * 	public class MyBlock extends SysMLBlock
 * 	{
 * 		public void createHyperlinkTexts()
 * 		{
 * 			myEthernetHyperlinkText = SysMLinHyperlinkText.Ethernet;
 * 			myAlternateEthernetHyperlinkText = new SysMLHyperlinkText("IEEE802.3bp - Industrial Gigabit Ethernet", "");
 * 		}
 * 	}
 * }
 * </pre>
 * 
 * @author ModelerOne
 *
 */
public final class SysMLinJavaHyperlinks extends SysMLPart
{
	/**
	 * Hyperlink to ethernet standard information
	 */
	@Hyperlink
	public static SysMLHyperlink Ethernet = new SysMLHyperlink("IEEE802.3 - Ethernet", "");
	/**
	 * Hyperlink to IP standard information
	 */
	@Hyperlink
	public static SysMLHyperlink IP = new SysMLHyperlink("IP - Internet Protocol", "");
	/**
	 * Hyperlink to TCP standard information
	 */
	@Hyperlink
	public static SysMLHyperlink TCP = new SysMLHyperlink("TCP - Transmission Control Protocol", "");
	/**
	 * Hyperlink to UDP standard information
	 */
	@Hyperlink
	public static SysMLHyperlink UDP = new SysMLHyperlink("UDP - User Datagram Protocol", "https://tools.ietf.org/html/rfc768");
	/**
	 * Hyperlink to HTTP standard information
	 */
	@Hyperlink
	public static SysMLHyperlink HTTP = new SysMLHyperlink("HTTP - HyperText Transfer Protocol", "");
	/**
	 * Hyperlink to SNMP standard information
	 */
	@Hyperlink
	public static SysMLHyperlink SNMP = new SysMLHyperlink("SNMP - Simple Network Management Protocol", "");
	/**
	 * Hyperlink to DDS standard information
	 */
	@Hyperlink
	public static SysMLHyperlink DDS = new SysMLHyperlink("DDS - Data Distribution Service Messaging Protocol", "");
	/**
	 * Hyperlink to hypothetical military standard information
	 */
	@Hyperlink
	public static SysMLHyperlink MILSTD456 = new SysMLHyperlink("MIL-STD 456 - MilStd Radar Messaging Protocol", "");
	/**
	 * Hyperlink to hypotheticaL military standard information
	 */
	@Hyperlink
	public static SysMLHyperlink MILSTD789 = new SysMLHyperlink("MIL-STD 789 - MilStd Strike Messaging Protocol", "");
	/**
	 * Hyperlink to hypotheticaL military standard information
	 */
	@Hyperlink
	public static SysMLHyperlink MILSTD8888 = new SysMLHyperlink("MIL-STD 8888 - MilStd Strike Target Positions Protocol", "");
	/**
	 * Hyperlink to hypotheticaL data link standard information
	 */
	@Hyperlink
	public static SysMLHyperlink DataLink = new SysMLHyperlink("Data Link - A data link protocol", "");
	/**
	 * Hyperlink to hypotheticaL GPS standard information
	 */
	@Hyperlink
	public static SysMLHyperlink GPS = new SysMLHyperlink("GPS - GPS user interface messaging protocol", "");
	/**
	 * Hyperlink to hypotheticaL TDMA standard information
	 */
	@Hyperlink
	public static SysMLHyperlink TDMA = new SysMLHyperlink("TDMA - Time division multiplexed protocol", "");
	/**
	 * Hyperlink to hypotheticaL PSK standard information
	 */
	@Hyperlink
	public static SysMLHyperlink PSK = new SysMLHyperlink("PSK - Phase-shift keyed protocol", "");
	/**
	 * Hyperlink to hypotheticaL HF standard information
	 */
	@Hyperlink
	public static SysMLHyperlink HF = new SysMLHyperlink("HF - High frequency band", "");
	/**
	 * Hyperlink to hypotheticaL PC standard information
	 */
	@Hyperlink
	public static SysMLHyperlink PC = new SysMLHyperlink("PC - Personal computer interface protocol", "");
	/**
	 * Hyperlink to hypotheticaL PC desktop standard information
	 */
	@Hyperlink
	public static SysMLHyperlink Desktop = new SysMLHyperlink("Desktop - Personal computer desktop interface protocol", "");
	/**
	 * Hyperlink to hypotheticaL PC application standard information
	 */
	@Hyperlink
	public static SysMLHyperlink Application = new SysMLHyperlink("App - Personal computer desktop application interface protocol", "");

	/**
	 * Hyperlink to SysMLinJava information
	 */
	@Hyperlink
	public static SysMLHyperlink sysmlinjava = new SysMLHyperlink("SysMLinJava", "www.sysmlinjava.com");
	/**
	 * Hyperlink to SysML standard information
	 */
	@Hyperlink
	public static SysMLHyperlink sysml = new SysMLHyperlink("OMG SysML", "http://www.omgsysml.org/specifications.htm");
}
