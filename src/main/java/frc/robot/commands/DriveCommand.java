package frc.robot.commands;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveModule.SteerRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest.FieldCentric;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.TunerConstants;
import frc.robot.subsystems.SwerveSubsystem;

public class DriveCommand extends Command {
    private final SwerveSubsystem swerve;
    private final XboxController controller;
    private boolean safetyMode = false; // Set to true to disable all movement for testing

    public final FieldCentric drive = new FieldCentric() // Create a new request
            .withDeadband(0.02) // Small deadband in m/s to prevent tiny movements
            .withRotationalDeadband(0.02) // Small deadband in rad/s to prevent tiny rotations
            .withDriveRequestType(DriveRequestType.OpenLoopVoltage) // Use open loop to avoid PID issues
            .withSteerRequestType(SteerRequestType.MotionMagicExpo);

    public DriveCommand(SwerveSubsystem swerve, XboxController controller) {
        // Constructor for the command
        this.swerve = swerve;
        this.controller = controller;
        addRequirements(swerve);
    }

    @Override
    public void execute() {
        // SAFETY MODE: If enabled, don't move the robot at all
        if (safetyMode) {
            swerve.getDrivetrain().setControl(
                    drive.withVelocityX(0)
                            .withVelocityY(0)
                            .withRotationalRate(0));
            SmartDashboard.putString("Drive Status", "SAFETY MODE - Robot Disabled");
            return;
        }

        // Read values from controller
        double xSpeed = -controller.getLeftY(); // Forward/back
        double ySpeed = -controller.getLeftX(); // Left/right
        double rot = -controller.getRightX(); // Rotation

        // Debug: Output raw controller values
        SmartDashboard.putNumber("Raw X Speed", xSpeed);
        SmartDashboard.putNumber("Raw Y Speed", ySpeed);
        SmartDashboard.putNumber("Raw Rotation", rot);

        // Apply deadband to prevent drift
        xSpeed = applyDeadband(xSpeed, TunerConstants.DEADBAND_RANGE);
        ySpeed = applyDeadband(ySpeed, TunerConstants.DEADBAND_RANGE);
        rot = applyDeadband(rot, TunerConstants.ROTATION_DEADBAND_RANGE);

        // Debug: Output values after deadband
        SmartDashboard.putNumber("After Deadband X", xSpeed);
        SmartDashboard.putNumber("After Deadband Y", ySpeed);
        SmartDashboard.putNumber("After Deadband Rot", rot);

        // Scale read values to max
        xSpeed *= TunerConstants.MAX_SPEED;
        ySpeed *= TunerConstants.MAX_SPEED;
        rot *= TunerConstants.MAX_ANGULAR_SPEED;

        // Debug: Output final commanded values
        SmartDashboard.putNumber("Final X Speed (m/s)", xSpeed);
        SmartDashboard.putNumber("Final Y Speed (m/s)", ySpeed);
        SmartDashboard.putNumber("Final Rotation (rad/s)", rot);
        SmartDashboard.putString("Drive Status", "Normal Operation");

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
        SmartDashboard.putString("Drive Status", "Command Ended");
    }
}