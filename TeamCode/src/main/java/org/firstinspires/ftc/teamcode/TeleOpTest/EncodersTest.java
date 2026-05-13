package org.firstinspires.ftc.teamcode.TeleOpTest;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp (name = "Teste do Motor", group = "TeleOp")
public class EncodersTest extends LinearOpMode {

    // --- CONFIGURAÇÕES DA FÓRMULA (Ajuste conforme suas peças) ---
    static final double TICKS_POR_REVOLUCAO = 537.7; // Ex: Motor GoBILDA 5203 (19.2:1)
    static final double DIAMETRO_RODA_MM = 96.0;     // Ex: Roda Mecanum GoBILDA de 96mm
    static final double REDUCAO_EXTERNA = 1.0;       // 1.0 se engrenagem for direta (1:1)

    // Cálculo final dos Ticks por Milímetro
    static final double TICKS_PER_MM = (TICKS_POR_REVOLUCAO * REDUCAO_EXTERNA) / (DIAMETRO_RODA_MM * Math.PI);

    @Override
    public void runOpMode() {
        DcMotor frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        DcMotor frontRight = hardwareMap.get(DcMotor.class, "frontRight");

        // Configuração inicial de direção
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        frontRight.setDirection(DcMotor.Direction.REVERSE);

        // Inicializa e zera os encoders
        resetarEncoders(frontLeft, frontRight);

        waitForStart();

        while (opModeIsActive()) {
            double drive = gamepad1.left_stick_x;
            double strafe = gamepad1.left_stick_y;
            double giro = gamepad1.right_stick_x;

            // Se pressionar A, anda 500 milímetros (50 centímetros) para frente de forma controlada
            if (gamepad1.a){
                moverPorEncoder(500, 0.5, frontLeft, frontRight);
            }

            // Controle Manual (TeleOp)
            double frontLeftPower = drive + strafe + giro;
            double frontRightPower = drive - strafe - giro;

            frontLeft.setPower(frontLeftPower);
            frontRight.setPower(frontRightPower);

            // Telemetria com a posição real atual dos motores em Ticks
            telemetry.addData("Ticks Atual Esq", frontLeft.getCurrentPosition());
            telemetry.addData("Ticks Atual Dir", frontRight.getCurrentPosition());
            telemetry.update();
        }
    }

    // Função auxiliar para resetar os encoders e voltar ao modo manual
    private void resetarEncoders(DcMotor... motores) {
        for (DcMotor motor : motores) {
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }

    // Função que aplica a fórmula e move o robô rigidamente até a distância alvo
    private void moverPorEncoder(double distanciaMM, double velocidade, DcMotor motorEsq, DcMotor motorDir) {
        // Aplicação da fórmula matemática


        // Define novos alvos somando à posição atual

        // Altera o modo para executar até o alvo
        motorEsq.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorDir.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // Liga os motores com a velocidade limite
        motorEsq.setPower(velocidade);
        motorDir.setPower(velocidade);

        // Segura o código aqui dentro até que ambos os motores cheguem no alvo
        while (opModeIsActive() && (motorEsq.isBusy() && motorDir.isBusy())) {

            telemetry.addData("Posição Esq", motorEsq.getCurrentPosition());
            telemetry.addData("Posição Dir", motorDir.getCurrentPosition());
            telemetry.update();
        }

        // Desliga a força física do RUN_TO_POSITION
        motorEsq.setPower(0);
        motorDir.setPower(0);

        // Restaura o modo normal para você voltar a dirigir no controle voluntariamente
        motorEsq.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorDir.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
}
