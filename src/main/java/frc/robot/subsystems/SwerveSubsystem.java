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
       
    }
        
    public SwerveDrivetrain<TalonFX, TalonFX, CANcoder> getDrivetrain() {
        return drivetrain;
    }
} 