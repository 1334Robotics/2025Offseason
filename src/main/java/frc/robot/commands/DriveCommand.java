package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.subsystems.SwerveSubsystem;

public class DriveCommand extends Command {
    private final SwerveSubsystem swerve;
    private final XboxController controller;

    public DriveCommand(SwerveSubsystem swerve, XboxController controller) {
        this.swerve = swerve;
        this.controller = controller;
        addRequirements(swerve);
    }

    @Override
    public void execute() {
        double xSpeed = -controller.getLeftY(); // Forward/back
        double ySpeed = -controller.getLeftX(); // Left/right
        double rot = -controller.getRightX();   // Rotation
        // Scale speeds to max
        xSpeed *= frc.robot.Constants.Swerve.MAX_SPEED_METERS_PER_SECOND;
        ySpeed *= frc.robot.Constants.Swerve.MAX_SPEED_METERS_PER_SECOND;
        rot *= frc.robot.Constants.Swerve.MAX_ANGULAR_SPEED_RADIANS_PER_SECOND;
        swerve.drive(xSpeed, ySpeed, rot, true); // Field-oriented
    }

    @Override
    public void end(boolean interrupted) {
        swerve.drive(0, 0, 0, true);
    }
} 