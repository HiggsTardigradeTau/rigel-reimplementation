package frc.robot.commands.drivetrain;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import frc.robot.subsystems.Drivetrain;

/**
 * Command to have drivetrain follow a trajectory.
 */
public class FollowTrajectory extends CommandBase {

    private final Drivetrain drivetrain;
    private final Trajectory trajectory;

    private RamseteCommand command;

    /**
     * The command Constructor.
     *
     * @param drivetrain The robot's drivetrain
     * @param trajectory The trajectory for the robot to follow.
     */
    public FollowTrajectory(Drivetrain drivetrain, Trajectory trajectory) {
        super();

        this.drivetrain = drivetrain;
        this.trajectory = trajectory;

        addRequirements(drivetrain);
    }

    @Override
    public void initialize() {
        double[] pid = drivetrain.getPid();

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
