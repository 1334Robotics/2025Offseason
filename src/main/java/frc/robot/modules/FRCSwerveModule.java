package frc.robot.modules;

import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.swerve.SwerveModule.ModuleRequest;
import com.ctre.phoenix6.swerve.SwerveModuleConstants;
import com.ctre.phoenix6.swerve.SwerveModule;

import edu.wpi.first.math.kinematics.SwerveModuleState;

public class FRCSwerveModule {
    private final SwerveModule<TalonFX, TalonFX, CANcoder> module;


    public FRCSwerveModule(int driveMotorId, int steerMotorId, SwerveModuleConstants<?, ?, ?> constants) {
        // This assumes you have set up SwerveModuleConstants elsewhere
        this.module = new SwerveModule(driveMotorId, steerMotorId, constants);
    }

    public void setDesiredState(SwerveModuleState desiredState) {
        ModuleRequest request = new ModuleRequest();
        request.State = desiredState; // WPILib SwerveModuleState is accepted
        module.apply(request);
    }

    public SwerveModuleState getState() {
        // Returns angle, proto, speedMetersPerSecond, struct
        return this.module.getCurrentState();
    }
} 