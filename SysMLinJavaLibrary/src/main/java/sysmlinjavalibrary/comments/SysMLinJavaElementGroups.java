package sysmlinjavalibrary.comments;

import static sysmlinjava.quantitykinds.SysMLinJavaQuantityKinds.AbsorbedDose;
import static sysmlinjava.quantitykinds.SysMLinJavaQuantityKinds.AbsorbedDoseRate;
import static sysmlinjava.quantitykinds.SysMLinJavaQuantityKinds.Acceleration;
import static sysmlinjava.quantitykinds.SysMLinJavaQuantityKinds.ActivityRadioNuclide;
import static sysmlinjava.quantitykinds.SysMLinJavaQuantityKinds.AmountOfSubstance;
import static sysmlinjava.quantitykinds.SysMLinJavaQuantityKinds.AngularAcceleration;
import static sysmlinjava.quantitykinds.SysMLinJavaQuantityKinds.AngularVelocity;
import static sysmlinjava.quantitykinds.SysMLinJavaQuantityKinds.Area;
import static sysmlinjava.quantitykinds.SysMLinJavaQuantityKinds.Capacitance;
import static sysmlinjava.quantitykinds.SysMLinJavaQuantityKinds.Concentration;
import static sysmlinjava.quantitykinds.SysMLinJavaQuantityKinds.Coordinate;
import static sysmlinjava.quantitykinds.SysMLinJavaQuantityKinds.Current;
import static sysmlinjava.quantitykinds.SysMLinJavaQuantityKinds.CurrentDensity;
import static sysmlinjava.quantitykinds.SysMLinJavaQuantityKinds.Density;
import static sysmlinjava.quantitykinds.SysMLinJavaQuantityKinds.Direction;
import static sysmlinjava.quantitykinds.SysMLinJavaQuantityKinds.Distance;
import static sysmlinjava.quantitykinds.SysMLinJavaQuantityKinds.DoseEquivalent;
import static sysmlinjava.quantitykinds.SysMLinJavaQuantityKinds.ElectricCharge;
import static sysmlinjava.quantitykinds.SysMLinJavaQuantityKinds.ElectricChargeDensity;
import static sysmlinjava.quantitykinds.SysMLinJavaQuantityKinds.ElectricConductance;
import static sysmlinjava.quantitykinds.SysMLinJavaQuantityKinds.ElectricCurrent;
import static sysmlinjava.quantitykinds.SysMLinJavaQuantityKinds.ElectricFieldStrength;
import static sysmlinjava.quantitykinds.SysMLinJavaQuantityKinds.ElectricFluxDensity;
import static sysmlinjava.quantitykinds.SysMLinJavaQuantityKinds.ElectricPotential;
import static sysmlinjava.quantitykinds.SysMLinJavaQuantityKinds.ElectricResistance;
import static sysmlinjava.quantitykinds.SysMLinJavaQuantityKinds.Energy;
import static sysmlinjava.quantitykinds.SysMLinJavaQuantityKinds.EnergydDnsity;
import static sysmlinjava.quantitykinds.SysMLinJavaQuantityKinds.Exposure;
import static sysmlinjava.quantitykinds.SysMLinJavaQuantityKinds.Flow;
import static sysmlinjava.quantitykinds.SysMLinJavaQuantityKinds.Force;
import static sysmlinjava.quantitykinds.SysMLinJavaQuantityKinds.Frequency;
import static sysmlinjava.quantitykinds.SysMLinJavaQuantityKinds.HeatCapacity;
import static sysmlinjava.quantitykinds.SysMLinJavaQuantityKinds.HeatFluxDensity;
import static sysmlinjava.quantitykinds.SysMLinJavaQuantityKinds.Illuminance;
import static sysmlinjava.quantitykinds.SysMLinJavaQuantityKinds.Inductance;
import static sysmlinjava.quantitykinds.SysMLinJavaQuantityKinds.Information;
import static sysmlinjava.quantitykinds.SysMLinJavaQuantityKinds.Jerk;
import static sysmlinjava.quantitykinds.SysMLinJavaQuantityKinds.LatentHeat;
import static sysmlinjava.quantitykinds.SysMLinJavaQuantityKinds.Length;
import static sysmlinjava.quantitykinds.SysMLinJavaQuantityKinds.Luminance;
import static sysmlinjava.quantitykinds.SysMLinJavaQuantityKinds.LuminousFlux;
import static sysmlinjava.quantitykinds.SysMLinJavaQuantityKinds.LuminousIntensity;
import static sysmlinjava.quantitykinds.SysMLinJavaQuantityKinds.MagenticFluxDensity;
import static sysmlinjava.quantitykinds.SysMLinJavaQuantityKinds.MagneticFieldStrength;
import static sysmlinjava.quantitykinds.SysMLinJavaQuantityKinds.MagneticFlux;
import static sysmlinjava.quantitykinds.SysMLinJavaQuantityKinds.Mass;
import static sysmlinjava.quantitykinds.SysMLinJavaQuantityKinds.MolarEnergy;
import static sysmlinjava.quantitykinds.SysMLinJavaQuantityKinds.MolarHeatCapacity;
import static sysmlinjava.quantitykinds.SysMLinJavaQuantityKinds.MomentOfForce;
import static sysmlinjava.quantitykinds.SysMLinJavaQuantityKinds.Money;
import static sysmlinjava.quantitykinds.SysMLinJavaQuantityKinds.Permeability;
import static sysmlinjava.quantitykinds.SysMLinJavaQuantityKinds.Permittivity;
import static sysmlinjava.quantitykinds.SysMLinJavaQuantityKinds.PlaneAngle;
import static sysmlinjava.quantitykinds.SysMLinJavaQuantityKinds.Position;
import static sysmlinjava.quantitykinds.SysMLinJavaQuantityKinds.Potential;
import static sysmlinjava.quantitykinds.SysMLinJavaQuantityKinds.Power;
import static sysmlinjava.quantitykinds.SysMLinJavaQuantityKinds.Pressure;
import static sysmlinjava.quantitykinds.SysMLinJavaQuantityKinds.Quantity;
import static sysmlinjava.quantitykinds.SysMLinJavaQuantityKinds.Radiance;
import static sysmlinjava.quantitykinds.SysMLinJavaQuantityKinds.RadiantIntensity;
import static sysmlinjava.quantitykinds.SysMLinJavaQuantityKinds.SolidAngle;
import static sysmlinjava.quantitykinds.SysMLinJavaQuantityKinds.SpecificEnergy;
import static sysmlinjava.quantitykinds.SysMLinJavaQuantityKinds.SpecificHeatCapacity;
import static sysmlinjava.quantitykinds.SysMLinJavaQuantityKinds.SpecificVolume;
import static sysmlinjava.quantitykinds.SysMLinJavaQuantityKinds.Speed;
import static sysmlinjava.quantitykinds.SysMLinJavaQuantityKinds.SurfaceTension;
import static sysmlinjava.quantitykinds.SysMLinJavaQuantityKinds.Temperature;
import static sysmlinjava.quantitykinds.SysMLinJavaQuantityKinds.ThermalConductivity;
import static sysmlinjava.quantitykinds.SysMLinJavaQuantityKinds.ThermodynamicTemperature;
import static sysmlinjava.quantitykinds.SysMLinJavaQuantityKinds.Time;
import static sysmlinjava.quantitykinds.SysMLinJavaQuantityKinds.Torque;
import static sysmlinjava.quantitykinds.SysMLinJavaQuantityKinds.Velocity;
import static sysmlinjava.quantitykinds.SysMLinJavaQuantityKinds.Viscosity;
import static sysmlinjava.quantitykinds.SysMLinJavaQuantityKinds.Volume;
import static sysmlinjava.quantitykinds.SysMLinJavaQuantityKinds.WaveNumber;
import static sysmlinjava.quantitykinds.SysMLinJavaQuantityKinds.Weight;
import static sysmlinjava.quantitykinds.SysMLinJavaQuantityKinds.Work;
import static sysmlinjava.units.SysMLinJavaUnits.*;

import java.util.List;

import sysmlinjava.attributetypes.BBoolean;
import sysmlinjava.attributetypes.IInteger;
import sysmlinjava.attributetypes.RReal;
import sysmlinjava.attributetypes.SString;
import sysmlinjava.javaannotations.metadata.ElementFilter;
import sysmlinjava.metadata.SysMLElementGroup;
import sysmlinjava.parts.SysMLPart;

/**
 * SysMLinJava collection of commonly used SysML element groups.
 * 
 * @author ModelerOne
 *
 */
public final class SysMLinJavaElementGroups extends SysMLPart
{
	/**
	 * Group of elements that are base attribute types
	 */
	@ElementFilter
	public static SysMLElementGroup sysMLinJavaBaseAttributeTypes = new SysMLElementGroup(true, List.of(RReal.class, IInteger.class, BBoolean.class, SString.class), List.of(), "SysMLinJavaBaseValueTypes", 0L);

	/**
	 * Group of elements that are SysML quantityKind types
	 */
	@ElementFilter
	public static SysMLElementGroup sysMLinJavaQuantityKinds = new SysMLElementGroup(true, List.of(),
		List.of(Length, Mass, Time, ElectricCurrent, ThermodynamicTemperature, AmountOfSubstance, LuminousIntensity, PlaneAngle, SolidAngle, Area, Volume, Speed, Acceleration, Jerk, WaveNumber, Density, SpecificVolume, CurrentDensity,
			MagneticFieldStrength, Concentration, Luminance, Frequency, Force, Pressure, Energy, Power, ElectricCharge, ElectricPotential, Capacitance, ElectricResistance, ElectricConductance, MagneticFlux, MagenticFluxDensity, Inductance,
			Temperature, LuminousFlux, Illuminance, ActivityRadioNuclide, AbsorbedDose, DoseEquivalent, MomentOfForce, SurfaceTension, HeatFluxDensity, HeatCapacity, SpecificHeatCapacity, SpecificEnergy, ThermalConductivity, EnergydDnsity,
			ElectricFieldStrength, ElectricChargeDensity, ElectricFluxDensity, Permittivity, Permeability, MolarEnergy, MolarHeatCapacity, Exposure, AbsorbedDoseRate, AngularVelocity, AngularAcceleration, RadiantIntensity, Radiance,
			Coordinate, Current, Direction, Information, LatentHeat, Distance, Money, Potential, Position, Quantity, Flow, Torque, Velocity, Viscosity, Weight, Work), "SysMLinJavaQuantityKinds", 0L);

	/**
	 * Group of elements that are SysML unit types
	 */
	@ElementFilter
	public static SysMLElementGroup sysMLinJavaUnits = new SysMLElementGroup(true, List.of(),
		List.of(Meter, Kilogram, Second, Ampere, Kelvin, Mole, Candela, Radian, Steradian, SquareMeter, CubicMeter, MeterPerSecond, MeterPerSecondSquared, ReciprocalMeter, KilogramPerMeterCubed, CubicMeterPerKilogram, AmperePerSquareMeter,
			AmperePerMeter, MolePerMeterCubed, CandelaPerMeterSquared, Hertz, KiloHertz, MegaHertz, GigaHertz, Newton, Pascal, Joule, Watt, Coulomb, Volt, Farad, Ohm, Siemens, Weber, Tesla, Henry, DegreeCelsius, Lumen, Lux, Becquerel, Gray,
			Sievert, PascalSecond, NewtonMeter, NewtonPerMeter, WattsPerMeterSquared, JoulePerKelvin, JoulePerKilogramKelvin, JoulePerKilogram, WattsPerMeterKelvin, JoulePerMeterCubed, VoltPerMeter, CoulombPerMeterCubed,
			CoulombPerMeterSquared, FaradPerMeter, HenryPerMeter, JoulePerMole, JoulePerMoleKelvin, CoulombPerKilogram, GrayPerSecond, RadianPerSecond, RadianPerSecondSquared, WattsPerSteradian, WattsPerSquareMeterSteradian, Numeric, Each,
			Percent, Meters, Millimeters, Centimeters, Kilometers, Miles, Feet, CentimetersSquare, MetersSquare, KilometersSquare, InchesSquare, FeetSquare, MilesSquare, CentimetersCubic, MetersCubic, KilometersCubic, InchesCubic,
			FeetCubic, MilesCubic, Liters, Gallons, Nanoseconds, Milliseconds, Seconds, Minutes, Hours, FeetPerSecond, RevolutionsPerMinute, KilometersPerHour, MilesPerHour, MilesPerHourDegrees, Knots, KilometersPerHourPerSecond,
			MilesPerHourPerSecond, MetersPerSecondCubed, MegabytesPerSecond, RadiansPerSecond, Watts, KiloWatts, MegaWatts, Revolutions, Volts, Kilovolts, Megavolts, Amps, Milliamps, Grams, MilliGrams, Kilograms, Ounces, Pounds, Tons,
			Kilotons, Megatons, Newtons, Joules, Degrees, Radians, Bytes, KiloBytes, MegaBytes, GigaBytes, TeraBytes, PetaBytes, Logical, Object, Characters, BitsPerSecond, Enumerated, DollarsUS, Euros, QuantityPerSecond, NewtonMeters,
			PoundFeet, NewtonsPerMeterSquare, NewtonsPerMeterSquareSeconds, PoundsPerInchSquare, KilogramsPerMeterCubic, PoundsPerFootCubic, KiloWattHours, KiloWattHoursPerKilometer, VoltAmperes, DegreesC, DegreesF, KilojoulesPerKilogram,
			Point, KeyValuePair, MetersCubicPerHour, MetersCubicPerSecond), "SysMLinJavaUnits", 0L);
}