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
        // KP is proportional gain, affects how much power steer has, KI is integral gain, does something with affecting long term error? KD is derivitive gain, reduces overshoot
        // KS/KV/KA are feedforward constants
        .withKP(20).withKI(0).withKD(0)
        .withKS(0.1).withKV(1.91).withKA(0)
        .withStaticFeedforwardSign(StaticFeedforwardSignValue.UseClosedLoopSign);

    private static final Slot0Configs driveGains = new Slot0Configs()
        .withKP(0.1).withKI(0).withKD(0)
        .withKS(0).withKV(0.124);

    // Sets the PID controller to use voltage as the output type for the closed loop control, instead of either torque or velocity
    private static final ClosedLoopOutputType kSteerClosedLoopOutput = ClosedLoopOutputType.Voltage;
    private static final ClosedLoopOutputType kDriveClosedLoopOutput = ClosedLoopOutputType.Voltage;

    // Motor and encoder types
    private static final DriveMotorArrangement kDriveMotorType = DriveMotorArrangement.TalonFX_Integrated;
    private static final SteerMotorArrangement kSteerMotorType = SteerMotorArrangement.TalonFX_Integrated;
    private static final SteerFeedbackType kSteerFeedbackType = SteerFeedbackType.FusedCANcoder;

    // Slip current is the current at which the motor will slip, used to detect when the wheel is slipping
    private static final Current kSlipCurrent = Units.Amps.of(120.0); // TODO

    
    private static final TalonFXConfiguration driveInitialConfigs = new TalonFXConfiguration()
        // Affects how much current the drive/steer motor can draw. Used to prevent overheating or damage
        .withCurrentLimits(
            new CurrentLimitsConfigs()
                .withStatorCurrentLimit(Units.Amps.of(40))
                .withStatorCurrentLimitEnable(true)
        );;
    private static final TalonFXConfiguration steerInitialConfigs = new TalonFXConfiguration()
        .withCurrentLimits(
            new CurrentLimitsConfigs()
                .withStatorCurrentLimit(Units.Amps.of(20))
                .withStatorCurrentLimitEnable(true)
        );
    private static final CANcoderConfiguration encoderInitialConfigs = new CANcoderConfiguration();
    private static final Pigeon2Configuration pigeonConfigs = null;

    public static final String kCANBus = "CANivore";
    public static final int kPigeonId = 2;

    public static final LinearVelocity kSpeedAt12Volts = Units.MetersPerSecond.of(4.69); // TODO
    public static final double MAX_SPEED = edu.wpi.first.math.util.Units.feetToMeters(14.5);
    public static final double MAX_ANGULAR_SPEED = Math.PI;
    public static final double DEADBAND_RANGE = 0.1;
    public static final double ROTATION_DEADBAND_RANGE = 0.1;
    private static final double kDriveGearRatio = 6.75;
    private static final double kSteerGearRatio = 21.429;
    private static final Distance kWheelRadius = Units.Inches.of(2);

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
            .withSlipCurrent(kSlipCurrent)
            .withDriveMotorType(kDriveMotorType)
            .withSteerMotorType(kSteerMotorType)
            .withDriveMotorInitialConfigs(driveInitialConfigs)
            .withSteerMotorInitialConfigs(steerInitialConfigs)
            .withEncoderInitialConfigs(encoderInitialConfigs)
            .withSteerFrictionVoltage(kSteerFrictionVoltage)
            .withDriveFrictionVoltage(kDriveFrictionVoltage);

    // Front left
    private static final int kFrontLeftDriveMotorId = 8;
    private static final int kFrontLeftSteerMotorId = 7;
    private static final int kFrontLeftEncoderId = 6;
    private static final Angle kFrontLeftEncoderOffset = Units.Degrees.of(354.375);
    private static final boolean kFrontLeftDriveMotorInverted = true;
    private static final boolean kFrontLeftSteerMotorInverted = false;
    private static final boolean kFrontLeftEncoderInverted = false;
    private static final Distance kFrontLeftXPos = Units.Inches.of(14.5);
    private static final Distance kFrontLeftYPos = Units.Inches.of(14.5);

    // Front right
    private static final int kFrontRightDriveMotorId = 14;
    private static final int kFrontRightSteerMotorId = 13;
    private static final int kFrontRightEncoderId = 5;
    private static final Angle kFrontRightEncoderOffset = Units.Degrees.of(96.767578);
    private static final boolean kFrontRightDriveMotorInverted = true;
    private static final boolean kFrontRightSteerMotorInverted = false;
    private static final boolean kFrontRightEncoderInverted = false;
    private static final Distance kFrontRightXPos = Units.Inches.of(14.5);
    private static final Distance kFrontRightYPos = Units.Inches.of(-14.5);

    // Back left
    private static final int kBackLeftDriveMotorId = 10;
    private static final int kBackLeftSteerMotorId = 9;
    private static final int kBackLeftEncoderId = 3;
    private static final Angle kBackLeftEncoderOffset = Units.Degrees.of(335.830078);
    private static final boolean kBackLeftDriveMotorInverted = true;
    private static final boolean kBackLeftSteerMotorInverted = false;
    private static final boolean kBackLeftEncoderInverted = false;
    private static final Distance kBackLeftXPos = Units.Inches.of(-14.5);
    private static final Distance kBackLeftYPos = Units.Inches.of(14.5);

    // Back right
    private static final int kBackRightDriveMotorId = 12;
    private static final int kBackRightSteerMotorId = 11;
    private static final int kBackRightEncoderId = 4;
    private static final Angle kBackRightEncoderOffset = Units.Degrees.of(35.507813);
    private static final boolean kBackRightDriveMotorInverted = true;
    private static final boolean kBackRightSteerMotorInverted = false;
    private static final boolean kBackRightEncoderInverted = false;
    private static final Distance kBackRightXPos = Units.Inches.of(-14.5);
    private static final Distance kBackRightYPos = Units.Inches.of(-14.5);

    // Create each module
    public static final SwerveModuleConstants<TalonFXConfiguration, TalonFXConfiguration, CANcoderConfiguration> FrontLeft =
        ConstantCreator.createModuleConstants(
            kFrontLeftSteerMotorId, kFrontLeftDriveMotorId, kFrontLeftEncoderId, kFrontLeftEncoderOffset,
            kFrontLeftXPos, kFrontLeftYPos, kFrontLeftDriveMotorInverted, kFrontLeftSteerMotorInverted, kFrontLeftEncoderInverted
        );
    public static final SwerveModuleConstants<TalonFXConfiguration, TalonFXConfiguration, CANcoderConfiguration> FrontRight =
        ConstantCreator.createModuleConstants(
            kFrontRightSteerMotorId, kFrontRightDriveMotorId, kFrontRightEncoderId, kFrontRightEncoderOffset,
            kFrontRightXPos, kFrontRightYPos, kFrontRightDriveMotorInverted, kFrontRightSteerMotorInverted, kFrontRightEncoderInverted
        );
    public static final SwerveModuleConstants<TalonFXConfiguration, TalonFXConfiguration, CANcoderConfiguration> BackLeft =
        ConstantCreator.createModuleConstants(
            kBackLeftSteerMotorId, kBackLeftDriveMotorId, kBackLeftEncoderId, kBackLeftEncoderOffset,
            kBackLeftXPos, kBackLeftYPos, kBackLeftDriveMotorInverted, kBackLeftSteerMotorInverted, kBackLeftEncoderInverted
        );
    public static final SwerveModuleConstants<TalonFXConfiguration, TalonFXConfiguration, CANcoderConfiguration> BackRight =
        ConstantCreator.createModuleConstants(
            kBackRightSteerMotorId, kBackRightDriveMotorId, kBackRightEncoderId, kBackRightEncoderOffset,
            kBackRightXPos, kBackRightYPos, kBackRightDriveMotorInverted, kBackRightSteerMotorInverted, kBackRightEncoderInverted
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