package frc.robot.constants;

import com.ctre.phoenix6.configs.CANcoderConfiguration;
import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.Pigeon2Configuration;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.StaticFeedforwardSignValue;
import com.ctre.phoenix6.swerve.SwerveDrivetrain;
import com.ctre.phoenix6.swerve.SwerveDrivetrainConstants;
import com.ctre.phoenix6.swerve.SwerveModuleConstants;
import com.ctre.phoenix6.swerve.SwerveModuleConstants.ClosedLoopOutputType;
import com.ctre.phoenix6.swerve.SwerveModuleConstants.DriveMotorArrangement;
import com.ctre.phoenix6.swerve.SwerveModuleConstants.SteerFeedbackType;
import com.ctre.phoenix6.swerve.SwerveModuleConstants.SteerMotorArrangement;
import com.ctre.phoenix6.swerve.SwerveModuleConstantsFactory;

import edu.wpi.first.units.Units;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.units.measure.LinearVelocity;
import edu.wpi.first.units.measure.MomentOfInertia;
import edu.wpi.first.units.measure.Voltage;

public final class TunerConstants {
    private static final Slot0Configs steerGains = new Slot0Configs()
        .withKP(100).withKI(0).withKD(0.5) // TODO
        .withKS(0.1).withKV(1.91).withKA(0) // TODO
        .withStaticFeedforwardSign(StaticFeedforwardSignValue.UseClosedLoopSign);

    private static final Slot0Configs driveGains = new Slot0Configs()
        .withKP(0.1).withKI(0).withKD(0) // TODO
        .withKS(0).withKV(0.124); // TODO

    private static final ClosedLoopOutputType kSteerClosedLoopOutput = ClosedLoopOutputType.Voltage;
    private static final ClosedLoopOutputType kDriveClosedLoopOutput = ClosedLoopOutputType.Voltage;

    private static final DriveMotorArrangement kDriveMotorType = DriveMotorArrangement.TalonFX_Integrated;
    private static final SteerMotorArrangement kSteerMotorType = SteerMotorArrangement.TalonFX_Integrated;
    private static final SteerFeedbackType kSteerFeedbackType = SteerFeedbackType.FusedCANcoder;

    private static final Current kSlipCurrent = Units.Amps.of(120.0); // TODO

    // Could remove ?
    private static final TalonFXConfiguration driveInitialConfigs = new TalonFXConfiguration();
    private static final TalonFXConfiguration steerInitialConfigs = new TalonFXConfiguration()
        .withCurrentLimits(
            new CurrentLimitsConfigs()
                .withStatorCurrentLimit(Units.Amps.of(60)) // TODO
                .withStatorCurrentLimitEnable(true)
        );
    private static final CANcoderConfiguration encoderInitialConfigs = new CANcoderConfiguration();
    private static final Pigeon2Configuration pigeonConfigs = null; // TODO

    public static final String kCANBus = "canivore"; // TODO
    public static final int kPigeonId = 1; // TODO

    public static final LinearVelocity kSpeedAt12Volts = Units.MetersPerSecond.of(4.69); // TODO
    public static final double MAX_SPEED = edu.wpi.first.math.util.Units.feetToMeters(14.5);
    public static final double MAX_ANGULAR_SPEED = Math.PI;
    private static final double kCoupleRatio = 3.8181818181818183; // TODO
    private static final double kDriveGearRatio = 6.75;
    private static final double kSteerGearRatio = 21.429;
    private static final Distance kWheelRadius = Units.Inches.of(2.167); //TODO

    private static final boolean kInvertLeftSide = false; // TODO
    private static final boolean kInvertRightSide = true; // TODO

    private static final MomentOfInertia kSteerInertia = Units.KilogramSquareMeters.of(0.01); // TODO
    private static final MomentOfInertia kDriveInertia = Units.KilogramSquareMeters.of(0.01); // TODO
    private static final Voltage kSteerFrictionVoltage = Units.Volts.of(0.2); // TODO
    private static final Voltage kDriveFrictionVoltage = Units.Volts.of(0.2); // TODO

    // Construct drivetrain constants
    public static final SwerveDrivetrainConstants DrivetrainConstants = new SwerveDrivetrainConstants()
        .withCANBusName(kCANBus)
        .withPigeon2Id(kPigeonId)
        .withPigeon2Configs(pigeonConfigs);
    
    // Construct module factory
    private static final SwerveModuleConstantsFactory<TalonFXConfiguration, TalonFXConfiguration, CANcoderConfiguration> ConstantCreator =
        new SwerveModuleConstantsFactory<TalonFXConfiguration, TalonFXConfiguration, CANcoderConfiguration>()
            .withDriveMotorGearRatio(kDriveGearRatio)
            .withSteerMotorGearRatio(kSteerGearRatio)
            .withWheelRadius(kWheelRadius)
            .withSteerMotorGains(steerGains)
            .withDriveMotorGains(driveGains)
            .withSteerMotorClosedLoopOutput(kSteerClosedLoopOutput)
            .withDriveMotorClosedLoopOutput(kDriveClosedLoopOutput)
            .withSpeedAt12Volts(kSpeedAt12Volts)
            .withFeedbackSource(kSteerFeedbackType)
            .withSteerInertia(kSteerInertia)
            .withDriveInertia(kDriveInertia)
            // values below are not mandatory, could be removed
            .withCouplingGearRatio(kCoupleRatio)
            .withSlipCurrent(kSlipCurrent)
            .withDriveMotorType(kDriveMotorType)
            .withSteerMotorType(kSteerMotorType)
            .withDriveMotorInitialConfigs(driveInitialConfigs)
            .withSteerMotorInitialConfigs(steerInitialConfigs)
            .withEncoderInitialConfigs(encoderInitialConfigs)
            .withSteerFrictionVoltage(kSteerFrictionVoltage)
            .withDriveFrictionVoltage(kDriveFrictionVoltage);

    // Front left TODO
    private static final int kFrontLeftDriveMotorId = 3;
    private static final int kFrontLeftSteerMotorId = 2;
    private static final int kFrontLeftEncoderId = 1;
    private static final Angle kFrontLeftEncoderOffset = Units.Rotations.of(0.15234375);
    private static final boolean kFrontLeftSteerMotorInverted = true;
    private static final boolean kFrontLeftEncoderInverted = false;
    private static final Distance kFrontLeftXPos = Units.Inches.of(10);
    private static final Distance kFrontLeftYPos = Units.Inches.of(10);

    // Front right TODO
    private static final int kFrontRightDriveMotorId = 1;
    private static final int kFrontRightSteerMotorId = 0;
    private static final int kFrontRightEncoderId = 0;
    private static final Angle kFrontRightEncoderOffset = Units.Rotations.of(-0.4873046875);
    private static final boolean kFrontRightSteerMotorInverted = true;
    private static final boolean kFrontRightEncoderInverted = false;
    private static final Distance kFrontRightXPos = Units.Inches.of(10);
    private static final Distance kFrontRightYPos = Units.Inches.of(-10);

    // Back left TODO
    private static final int kBackLeftDriveMotorId = 7;
    private static final int kBackLeftSteerMotorId = 6;
    private static final int kBackLeftEncoderId = 3;
    private static final Angle kBackLeftEncoderOffset = Units.Rotations.of(-0.219482421875);
    private static final boolean kBackLeftSteerMotorInverted = true;
    private static final boolean kBackLeftEncoderInverted = false;
    private static final Distance kBackLeftXPos = Units.Inches.of(-10);
    private static final Distance kBackLeftYPos = Units.Inches.of(10);

    // Back right TODO
    private static final int kBackRightDriveMotorId = 5;
    private static final int kBackRightSteerMotorId = 4;
    private static final int kBackRightEncoderId = 2;
    private static final Angle kBackRightEncoderOffset = Units.Rotations.of(0.17236328125);
    private static final boolean kBackRightSteerMotorInverted = true;
    private static final boolean kBackRightEncoderInverted = false;
    private static final Distance kBackRightXPos = Units.Inches.of(-10);
    private static final Distance kBackRightYPos = Units.Inches.of(-10);

    // Create each module
    public static final SwerveModuleConstants<TalonFXConfiguration, TalonFXConfiguration, CANcoderConfiguration> FrontLeft =
        ConstantCreator.createModuleConstants(
            kFrontLeftSteerMotorId, kFrontLeftDriveMotorId, kFrontLeftEncoderId, kFrontLeftEncoderOffset,
            kFrontLeftXPos, kFrontLeftYPos, kInvertLeftSide, kFrontLeftSteerMotorInverted, kFrontLeftEncoderInverted
        );
    public static final SwerveModuleConstants<TalonFXConfiguration, TalonFXConfiguration, CANcoderConfiguration> FrontRight =
        ConstantCreator.createModuleConstants(
            kFrontRightSteerMotorId, kFrontRightDriveMotorId, kFrontRightEncoderId, kFrontRightEncoderOffset,
            kFrontRightXPos, kFrontRightYPos, kInvertRightSide, kFrontRightSteerMotorInverted, kFrontRightEncoderInverted
        );
    public static final SwerveModuleConstants<TalonFXConfiguration, TalonFXConfiguration, CANcoderConfiguration> BackLeft =
        ConstantCreator.createModuleConstants(
            kBackLeftSteerMotorId, kBackLeftDriveMotorId, kBackLeftEncoderId, kBackLeftEncoderOffset,
            kBackLeftXPos, kBackLeftYPos, kInvertLeftSide, kBackLeftSteerMotorInverted, kBackLeftEncoderInverted
        );
    public static final SwerveModuleConstants<TalonFXConfiguration, TalonFXConfiguration, CANcoderConfiguration> BackRight =
        ConstantCreator.createModuleConstants(
            kBackRightSteerMotorId, kBackRightDriveMotorId, kBackRightEncoderId, kBackRightEncoderOffset,
            kBackRightXPos, kBackRightYPos, kInvertRightSide, kBackRightSteerMotorInverted, kBackRightEncoderInverted
        );
    
    // Helper to create a drivetrain
    public static SwerveDrivetrain<TalonFX, TalonFX, CANcoder> createDrivetrain() {
        return new SwerveDrivetrain<>(
            TalonFX::new,
            TalonFX::new,
            CANcoder::new,
            DrivetrainConstants,
            FrontLeft,
            FrontRight,
            BackLeft,
            BackRight
        );
    }
} 