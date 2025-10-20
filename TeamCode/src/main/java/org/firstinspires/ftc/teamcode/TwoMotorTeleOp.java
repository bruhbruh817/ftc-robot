package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Simple two-motor drive (motors at ports 0 and 1).
 * Left stick Y  → forward / backward
 * Right stick X → turn
 */

@TeleOp(name = "TwoMotor: Basic", group = "Linear OpMode")
public class TwoMotorTeleOp extends LinearOpMode {

    private DcMotor leftMotor  = null;  // port 0
    private DcMotor rightMotor = null;  // port 1

    @Override
    public void runOpMode() {

        // Match names to your Robot Configuration
        leftMotor  = hardwareMap.get(DcMotor.class, "motor0");
        rightMotor = hardwareMap.get(DcMotor.class, "motor1");

        // Reverse one side so both push forward when stick ↑
        leftMotor.setDirection(DcMotor.Direction.REVERSE);
        rightMotor.setDirection(DcMotor.Direction.FORWARD);

        // Stop cleanly when no power
        leftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            // Read joysticks
            double drive = -gamepad1.left_stick_y; // forward/back
            double turn  =  gamepad1.right_stick_x; // rotate

            // Mix drive + turn for tank-style differential
            double leftPower  = drive + turn;
            double rightPower = drive - turn;

            // Normalize so max |power| ≤ 1
            double max = Math.max(Math.abs(leftPower), Math.abs(rightPower));
            if (max > 1.0) {
                leftPower  /= max;
                rightPower /= max;
            }

            // Send power
            leftMotor.setPower(leftPower);
            rightMotor.setPower(rightPower);

            telemetry.addData("Left / Right", "%4.2f  %4.2f", leftPower, rightPower);
            telemetry.update();
        }
    }
}
