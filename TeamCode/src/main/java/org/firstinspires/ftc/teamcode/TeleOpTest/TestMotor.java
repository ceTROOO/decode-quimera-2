package org.firstinspires.ftc.teamcode.TeleOpTest;

import com.qualcomm.hardware.lynx.commands.core.LynxGetMotorEncoderPositionResponse;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import java.util.function.Supplier;

@TeleOp (name = "Teste do Motor", group = "TeleOp")
public class TestMotor extends LinearOpMode {


    @Override
    public void runOpMode() {
        DcMotor frontLeft = hardwareMap.get(DcMotor.class, "frontLeft"); // COLOCAR ESSE NA PORTA 0 DO CONTROL
        DcMotor frontRight = hardwareMap.get(DcMotor.class, "frontRight"); // COLOCAR ESSE NA PORTA 1 DO CONTROL
        DcMotor backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        DcMotor backRight = hardwareMap.get(DcMotor.class, "backRight");

        frontLeft.setDirection(DcMotor.Direction.FORWARD); // motor 0 no control
        frontRight.setDirection(DcMotor.Direction.FORWARD); // motor 1 no control
        backLeft.setDirection(DcMotor.Direction.FORWARD);
        backRight.setDirection(DcMotor.Direction.FORWARD);

        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        waitForStart();

        while (opModeIsActive()) {
            double drive = gamepad1.left_stick_x;
            double strafe = gamepad1.left_stick_y;
            double giro = gamepad1.right_stick_x;

            double frontLeftPower = drive + strafe + giro;
            double frontRightPower = drive - strafe - giro;
            double backLeftPower = drive - strafe + giro;
            double backRightPower = drive + strafe - giro;

            frontLeft.setPower(frontLeftPower);
            frontRight.setPower(frontRightPower);
            backLeft.setPower(backLeftPower);
            backRight.setPower(backRightPower);

            telemetry.addData ("Motor", "Esq: %.2f, Dir: %.2f", drive+giro, drive-giro);
            telemetry.addData("status", "Movendo por Encoder...");
            telemetry.addData("esq Atual", frontLeft.getCurrentPosition());
            telemetry.addData("dir Atual", frontRight.getCurrentPosition());
            telemetry.update();
            telemetry.update();

        }
    }
}