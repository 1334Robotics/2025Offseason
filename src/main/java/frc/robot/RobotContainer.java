package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.SwerveSubsystem;
import frc.robot.commands.DriveCommand;

public class RobotContainer {
    private final SwerveSubsystem swerveSubsystem = new SwerveSubsystem();
    private final XboxController driverController = new XboxController(Constants.XBOX_CONTROLLER_PORT);

    public RobotContainer() {
        swerveSubsystem.setDefaultCommand(new DriveCommand(swerveSubsystem, driverController));
    }

    public Command getAutonomousCommand() {
        // Return a simple do-nothing command for now
        return null;
    }
} 