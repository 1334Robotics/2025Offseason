package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.swerve.SwerveDrivetrain;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.TunerConstants;

public class SwerveSubsystem extends SubsystemBase {
    private final SwerveDrivetrain<TalonFX, TalonFX, CANcoder> drivetrain;

    public SwerveSubsystem() {
        drivetrain = TunerConstants.createDrivetrain();
    }

    @Override
    public void periodic() {
        // Periodic method - can be used for telemetry or other regular updates
        // The drivetrain handles its own periodic updates internally
    }

    public SwerveDrivetrain<TalonFX, TalonFX, CANcoder> getDrivetrain() {
        return drivetrain;
    }

    /**
     * Stop the drivetrain by setting all velocities to zero
     */
    public void stop() {
        drivetrain.setControl(
                new com.ctre.phoenix6.swerve.SwerveRequest.FieldCentric()
                        .withVelocityX(0)
                        .withVelocityY(0)
                        .withRotationalRate(0));
    }
}