package janela;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

public class Janela {
    private static int count = 0;
    private static int interval = 1000; // 1 segundo por padrão
    private static boolean isRunning = true;
    private static Timer timer;

    public static void main(String[] args) {
        // Cria a janela
        JFrame frame = new JFrame("Contador");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);

        // Cria o painel
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1));

        // Cria o label para mostrar o número
        JLabel label = new JLabel(String.valueOf(count), SwingConstants.CENTER);
        label.setFont(new Font("Serif", Font.BOLD, 32));
        panel.add(label);

        // Cria o painel de botões
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        // Botão de resetar
        JButton resetButton = new JButton("Resetar");
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                count = 0;
                label.setText(String.valueOf(count));
            }
        });
        buttonPanel.add(resetButton);

        // Botão de acelerar o incremento
        JButton increaseSpeedButton = new JButton("Acelerar");
        increaseSpeedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (interval > 100) {
                    interval -= 100; // Acelera o incremento
                    resetTimer(label);
                }
            }
        });
        buttonPanel.add(increaseSpeedButton);

        // Botão de diminuir o incremento
        JButton decreaseSpeedButton = new JButton("Desacelerar");
        decreaseSpeedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                interval += 100; // Desacelera o incremento
                resetTimer(label);
            }
        });
        buttonPanel.add(decreaseSpeedButton);

        // Botão de pausar
        JButton pauseButton = new JButton("Pausar");
        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isRunning = !isRunning;
                if (isRunning) {
                    resetTimer(label);
                    pauseButton.setText("Pausar");
                } else {
                    if (timer != null) {
                        timer.cancel();
                    }
                    pauseButton.setText("Retomar");
                }
            }
        });
        buttonPanel.add(pauseButton);

        panel.add(buttonPanel);
        frame.add(panel);

        // Mostra a janela
        frame.setVisible(true);

        // Inicializa o Timer
        resetTimer(label);
    }

    // Reinicializa o timer com o novo intervalo
    private static void resetTimer(JLabel label) {
        if (timer != null) {
            timer.cancel();
        }
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (isRunning) {
                    count++;
                    // Garante que a atualização da interface seja feita na Event Dispatch Thread
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            label.setText(String.valueOf(count));
                        }
                    });
                }
            }
        }, 0, interval);
    }
}  
