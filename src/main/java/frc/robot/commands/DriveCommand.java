package frc.robot.commands;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveModule.SteerRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.TunerConstants;
import frc.robot.subsystems.SwerveSubsystem;

public class DriveCommand extends Command {
    private final SwerveSubsystem swerve;
    private final XboxController controller;
    public final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric() // Create a new request
            .withDeadband(TunerConstants.MAX_SPEED * 0.1).withRotationalDeadband(TunerConstants.MAX_ANGULAR_SPEED * 0.1) // Add a 10% deadband
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
        double rot = -controller.getRightX();   // Rotation

        // Scale read values to max
        xSpeed *= frc.robot.constants.TunerConstants.MAX_SPEED;
        ySpeed *= frc.robot.constants.TunerConstants.MAX_SPEED;
        rot *= frc.robot.constants.TunerConstants.MAX_ANGULAR_SPEED;

        // Drive
        swerve.getDrivetrain().setControl(
            drive.withVelocityX(xSpeed)
                .withVelocityY(ySpeed)
                .withRotationalRate(rot)
        );
    }

    @Override
    public void end(boolean interrupted) {
        // Stop robot when the command ends
        
    }
} 