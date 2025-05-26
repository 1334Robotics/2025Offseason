package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.SwerveSubsystem;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.ExampleCommand;
import frc.robot.constants.Constants;

public class RobotContainer {
    private final SwerveSubsystem swerveSubsystem = new SwerveSubsystem();
    private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
    private final XboxController driverController = new XboxController(
            Constants.OperatorConstants.kDriverControllerPort); // TODO Default uses CommandXboxController, could
                                                                // replace with the other constructor

    public RobotContainer() {
        swerveSubsystem.setDefaultCommand(new DriveCommand(swerveSubsystem, driverController));
        configureBindings();
    }

    private void configureBindings() {
        new Trigger(m_exampleSubsystem::exampleCondition)
                .onTrue(new ExampleCommand(m_exampleSubsystem));
    }

    public Command getAutonomousCommand() {
        // Return a simple do-nothing command for now
        return null;
    }

    public void stopRobot() {
        // Stop the swerve drivetrain
        swerveSubsystem.stop();
    }
}