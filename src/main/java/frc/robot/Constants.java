// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.geometry.Translation2d;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
  }

  public static final class Swerve {
        // CAN IDs for drive and steer motors (FL, FR, BL, BR)
        public static final int[] DRIVE_MOTOR_IDS = {1, 3, 5, 7};
        public static final int[] STEER_MOTOR_IDS = {2, 4, 6, 8};
        public static final int PIGEON_ID = 9;

        // Physical locations of the modules relative to robot center (meters)
        public static final Translation2d FRONT_LEFT_LOCATION = new Translation2d(0.381, 0.381);
        public static final Translation2d FRONT_RIGHT_LOCATION = new Translation2d(0.381, -0.381);
        public static final Translation2d BACK_LEFT_LOCATION = new Translation2d(-0.381, 0.381);
        public static final Translation2d BACK_RIGHT_LOCATION = new Translation2d(-0.381, -0.381);

        // Swerve drive kinematics constants
        public static final double WHEEL_DIAMETER_METERS = 0.1016;
        public static final double DRIVE_GEAR_RATIO = 6.75;
        public static final double STEER_GEAR_RATIO = 12.8;
        public static final double MAX_SPEED_METERS_PER_SECOND = 4.5;
        public static final double MAX_ANGULAR_SPEED_RADIANS_PER_SECOND = Math.PI;
    }
    public static final int XBOX_CONTROLLER_PORT = 0;
}
