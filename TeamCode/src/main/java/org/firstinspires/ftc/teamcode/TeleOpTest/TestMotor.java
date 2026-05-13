package org.firstinspires.ftc.teamcode.TeleOpTest;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import java.util.function.Supplier;

@TeleOp (name = "Teste dos Motores", group = "TeleOp")

public class TestMotor extends LinearOpMode {


    @Override
    public void runOpMode() {
        DcMotor frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        DcMotor frontRight = hardwareMap.get(DcMotor.class, "frontRight");

        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        frontRight.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();

        while (opModeIsActive()) {
            double drive = gamepad1.left_stick_x;
            double strafe = gamepad1.left_stick_y;
            double giro = gamepad1.right_stick_x;

            double frontLeftPower = drive + strafe + giro;
            double frontRightPower = drive - strafe - giro;

            frontLeft.setPower(frontLeftPower);
            frontRight.setPower(frontRightPower);

            telemetry.addData ("Motor", "Esq: %.2f, Dir: %.2f", drive+giro, drive-giro);
            telemetry.update();

        }
    }
}