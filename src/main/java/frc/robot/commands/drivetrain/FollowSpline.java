// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.drivetrain;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import frc.robot.subsystems.Drivetrain;
import java.util.List;

/**
 * Command to make drivetrain follow splines.
 *
 * @apiNote THIS IS A BAD IMPLEMENTATION AND SHOULD BE UPDATED.
 */
public class FollowSpline extends CommandBase {

    private final Drivetrain drivetrain;

    private RamseteCommand command;

    /**
     * The Constructor.
     *
     * @param drivetrain The robot's drivetrain.
     */
    public FollowSpline(Drivetrain drivetrain) {
        super();

        this.drivetrain = drivetrain;
        addRequirements(drivetrain);
    }

    @Override
    public void initialize() {
        double[] pid = drivetrain.getPid();

        // Add kinematics to ensure max speed is actually obeyed
        // Apply the voltage constraint
        TrajectoryConfig config = new TrajectoryConfig(2.5, 2.5)
                // Add kinematics to ensure max speed is actually obeyed
                .setKinematics(Drivetrain.DriveKinimatics)
                // Apply the voltage constraint
                .addConstraint(drivetrain.getVoltageConstraint());

        // scaled by 3 for testing so i dont break my walls
        // Start at the origin facing the +X direction
        // Pass through these two interior waypoints, making an 's' curve path
        // End 3 meters straight ahead of where we started, facing forward
        // Pass config
        Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
                // Start at the origin facing the +X direction
                new Pose2d(0, 0, new Rotation2d(0)),
                // Pass through these two interior waypoints, making an 's' curve path
                List.of(new Translation2d(3, 1).div(3), new Translation2d(6, -1).div(3)),
                // End 3 meters straight ahead of where we started, facing forward
                new Pose2d(new Translation2d(9, 0).div(3), new Rotation2d(0)),
                // Pass config
                config);

        command =
                new RamseteCommand(
                        trajectory,
                        drivetrain::getPose,
                        new RamseteController(2, 0.7),
                        drivetrain.getFeedForward(),
                        Drivetrain.DriveKinimatics,
                        drivetrain::getWheelSpeeds,
                        new PIDController(pid[0], pid[1], pid[2]),
                        new PIDController(pid[0], pid[1], pid[2]),
                        drivetrain::setMotorVolts,
                        drivetrain);

        drivetrain.setPose(trajectory.getInitialPose());

        command.initialize();
    }

    @Override
    public void execute() {
        command.execute();
    }

    @Override
    public boolean isFinished() {
        return command.isFinished();
    }

    @Override
    public void end(boolean interrupted) {
        if (interrupted) {
            command.cancel();
        }
    }
}
