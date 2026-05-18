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
        DcMotorEx frontLeft = hardwareMap.get(DcMotorEx.class, "frontLeft"); // COLOCAR ESSE NA PORTA 0 DO CONTROL
        DcMotorEx frontRight = hardwareMap.get(DcMotorEx.class, "frontRight"); // COLOCAR ESSE NA PORTA 1 DO CONTROL
        DcMotorEx backLeft = hardwareMap.get(DcMotorEx.class, "backLeft"); //COLOCAR ESSE NA PORTA 2 DO CONTROL
        DcMotorEx backRight = hardwareMap.get(DcMotorEx.class, "backRight"); //COLOCAR ESSE NA PORTA 3 DO CONTROL

        frontLeft.setDirection(DcMotorEx.Direction.FORWARD); // motor 0 no control
        frontRight.setDirection(DcMotorEx.Direction.FORWARD); // motor 1 no control
        backLeft.setDirection(DcMotorEx.Direction.FORWARD); // motor 2 no control
        backRight.setDirection(DcMotorEx.Direction.FORWARD); // motor 3 no control

        frontLeft.setVelocity(2488.88);
        backLeft.setVelocity(2488.88);
        backRight.setVelocity(2488.88);

        frontLeft.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);

        frontRight.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        frontLeft.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);


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