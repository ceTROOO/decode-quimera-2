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

    int ticksAlvoDesejado = 1000;

    @Override
    public void runOpMode() {
        DcMotor frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        DcMotor frontRight = hardwareMap.get(DcMotor.class, "frontRight");

        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        frontRight.setDirection(DcMotor.Direction.REVERSE);

        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        waitForStart();

        while (opModeIsActive()) {
            double drive = gamepad1.left_stick_x;
            double strafe = gamepad1.left_stick_y;
            double giro = gamepad1.right_stick_x;

            if (gamepad1.a){
                 frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                 frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

                 frontLeft.setTargetPosition(ticksAlvoDesejado);
                 frontRight.setTargetPosition(ticksAlvoDesejado);



                frontRight.setPower(0.5);
                frontLeft.setPower(0.5);

                while (opModeIsActive() && (frontLeft.isBusy() && frontRight.isBusy())) {
                    telemetry.addData("status", "Movendo por Encoder...");
                    telemetry.addData("esq Atual", frontLeft.getCurrentPosition());
                    telemetry.addData("dir Atual", frontRight.getCurrentPosition());
                    telemetry.update();
                }
            }frontLeft.setPower(0);
            frontRight.setPower(0);

            frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            double frontLeftPower = drive + strafe + giro;
            double frontRightPower = drive - strafe - giro;

            frontLeft.setPower(frontLeftPower);
            frontRight.setPower(frontRightPower);

            telemetry.addData ("Motor", "Esq: %.2f, Dir: %.2f", drive+giro, drive-giro);
            telemetry.update();

        }
    }
}