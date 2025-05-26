package frc.robot.commands;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveModule.SteerRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest.FieldCentric;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.TunerConstants;
import frc.robot.subsystems.SwerveSubsystem;

public class DriveCommand extends Command {
    private final SwerveSubsystem swerve;
    private final XboxController controller;
    public final FieldCentric drive = new FieldCentric() // Create a new request
            .withDeadband(TunerConstants.MAX_SPEED * TunerConstants.DEADBAND_RANGE)
            .withRotationalDeadband(TunerConstants.MAX_ANGULAR_SPEED * TunerConstants.ROTATION_DEADBAND_RANGE)
            .withDriveRequestType(DriveRequestType.OpenLoopVoltage)
            .withSteerRequestType(SteerRequestType.MotionMagicExpo);

    public DriveCommand(SwerveSubsystem swerve, XboxController controller) {
        // Constructor for the command
        this.swerve = swerve;
        this.controller = controller;
        addRequirements(swerve);
    }

    @Override
    public void execute() {
        // Read values from controller
        double xSpeed = -controller.getLeftY(); // Forward/back
        double ySpeed = -controller.getLeftX(); // Left/right
        double rot = -controller.getRightX(); // Rotation

        // Apply deadband to prevent drift
        xSpeed = applyDeadband(xSpeed, TunerConstants.DEADBAND_RANGE);
        ySpeed = applyDeadband(ySpeed, TunerConstants.DEADBAND_RANGE);
        rot = applyDeadband(rot, TunerConstants.ROTATION_DEADBAND_RANGE);

        // Scale read values to max
        xSpeed *= frc.robot.constants.TunerConstants.MAX_SPEED;
        ySpeed *= frc.robot.constants.TunerConstants.MAX_SPEED;
        rot *= frc.robot.constants.TunerConstants.MAX_ANGULAR_SPEED;

        // Drive
        swerve.getDrivetrain().setControl(
                drive.withVelocityX(xSpeed)
                        .withVelocityY(ySpeed)
                        .withRotationalRate(rot));
    }

    /**
     * Apply deadband to controller input
     */
    private double applyDeadband(double value, double deadband) {
        if (Math.abs(value) < deadband) {
            return 0.0;
        }
        return (value - Math.copySign(deadband, value)) / (1.0 - deadband);
    }

    @Override
    public void end(boolean interrupted) {
        // Stop robot when the command ends
        swerve.getDrivetrain().setControl(
                drive.withVelocityX(0)
                        .withVelocityY(0)
                        .withRotationalRate(0));
    }
}