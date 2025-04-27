package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.Pigeon2;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.modules.FRCSwerveModule;

public class SwerveSubsystem extends SubsystemBase {
    private final FRCSwerveModule[] modules = new FRCSwerveModule[4];
    private final SwerveDriveKinematics kinematics;
    private final SwerveDriveOdometry odometry;
    private final Pigeon2 gyro;

    public SwerveSubsystem() {
        // CONSTANTS TO BE SET
        modules[0] = new FRCSwerveModule(Constants.Swerve.DRIVE_MOTOR_IDS[0], Constants.Swerve.STEER_MOTOR_IDS[0], null); // FL
        modules[1] = new FRCSwerveModule(Constants.Swerve.DRIVE_MOTOR_IDS[1], Constants.Swerve.STEER_MOTOR_IDS[1], null); // FR
        modules[2] = new FRCSwerveModule(Constants.Swerve.DRIVE_MOTOR_IDS[2], Constants.Swerve.STEER_MOTOR_IDS[2], null); // BL
        modules[3] = new FRCSwerveModule(Constants.Swerve.DRIVE_MOTOR_IDS[3], Constants.Swerve.STEER_MOTOR_IDS[3], null); // BR

        kinematics = new SwerveDriveKinematics(
            Constants.Swerve.FRONT_LEFT_LOCATION,
            Constants.Swerve.FRONT_RIGHT_LOCATION,
            Constants.Swerve.BACK_LEFT_LOCATION,
            Constants.Swerve.BACK_RIGHT_LOCATION
        );
        gyro = new Pigeon2(Constants.Swerve.PIGEON_ID);
        // Use dummy SwerveModulePosition[] for now
        odometry = new SwerveDriveOdometry(
            kinematics,
            getHeading(),
            new SwerveModulePosition[] {
                new SwerveModulePosition(),
                new SwerveModulePosition(),
                new SwerveModulePosition(),
                new SwerveModulePosition()
            },
            new Pose2d()
        );
    }

    public void drive(double xSpeed, double ySpeed, double rot, boolean fieldRelative) {
        SwerveModuleState[] states = kinematics.toSwerveModuleStates(
            fieldRelative
                ? edu.wpi.first.math.kinematics.ChassisSpeeds.fromFieldRelativeSpeeds(xSpeed, ySpeed, rot, getHeading())
                : new edu.wpi.first.math.kinematics.ChassisSpeeds(xSpeed, ySpeed, rot)
        );
        setModuleStates(states);
    }

    public void setModuleStates(SwerveModuleState[] states) {
        for (int i = 0; i < modules.length; i++) {
            modules[i].setDesiredState(states[i]);
        }
    }

    public Rotation2d getHeading() {
        return Rotation2d.fromDegrees(gyro.getYaw().getValueAsDouble());
    }

    @Override
    public void periodic() {
          odometry.update(getHeading(), new SwerveModulePosition[] {
            new SwerveModulePosition(),
            new SwerveModulePosition(),
            new SwerveModulePosition(),
            new SwerveModulePosition()
        });
    }

    public Pose2d getPose() {
        return odometry.getPoseMeters();
    }
} 