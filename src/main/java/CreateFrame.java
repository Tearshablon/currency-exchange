import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class CreateFrame {

    private JFrame myWindow;
    private JPanel panel;
    private JTextField jTextArea;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JButton button = new JButton("Нажми для перевевода");
    private String[] item = {"USD(Американский доллар)","EUR(Евро)", "RUB(Рубль)"};
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private String choise1 = "USD";
    private String choise2 = "USD";
    private ConnectWithServer connectWithServer = new ConnectWithServer();
    private Double num;

    public void createFrameMethod() {

        myWindow = new JFrame("Курс валют");
        panel = new JPanel();
        jTextArea = new JTextField(); // поле ввода денег
        jLabel1 = new JLabel("Выбирите у.е. из которых вы хотите перевести деньги:", SwingConstants.CENTER);
        jLabel2 = new JLabel("Выбирите у.е. в которые вы хотите перевести деньги:", SwingConstants.CENTER);
        jLabel3 = new JLabel("Введите сумму (в строке ниже должны быть ТОЛЬКО ЦИФРЫ):", SwingConstants.CENTER); //метки
        jLabel4 = new JLabel("Ваша сумма:", SwingConstants.CENTER); //метки
        myWindow.add(panel);
        panel.setLayout(new GridLayout(8, 1));
        myWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myWindow.setSize(700, 500);
        myWindow.setVisible(true);
    }

    public void frameListener() {

        comboBox1 = new JComboBox(item);
        comboBox1.setEditable(false); //запрещаем пользоваелю вводить свой вариант
        comboBox1.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                String str = (String) comboBox1.getSelectedItem().toString();
                setChoise1(str);
            }
        });
        comboBox2 = new JComboBox(item);
        comboBox2.setEditable(false); // запрещает самостоятельно писать свой вариант
        comboBox2.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                String str = (String) comboBox2.getSelectedItem().toString();
                setChoise2(str);
            }
        });
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!choise1.equals(choise2)) {
                    try {
                        connectWithServer.setQuery("http://api.fixer.io/latest?base=".
                                concat(choise1).
                                concat("&symbols=").
                                concat(choise2).
                                concat("\""));
                        num = Double.parseDouble(jTextArea.getText());
                        connectWithServer.connect();
                        String str = String.valueOf(num * connectWithServer.getRateToFrame());
                        jLabel4.setText(str);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }else {
                    jLabel4.setText("Выбирите разные валюты");
                }
            }
        });
        panel.add(jLabel1);
        panel.add(comboBox1);
        panel.add(jLabel2);
        panel.add(comboBox2);
        panel.add(jLabel3);
        panel.add(jTextArea);
        panel.add(button);
        panel.add(jLabel4);
        myWindow.getContentPane().add(panel);
    }
    public JComboBox getComboBox1() {
        return comboBox1;
    }

    public JComboBox getComboBox2() {
        return comboBox2;
    }

    public String getChoise1() {
        return choise1;
    }

    public void setChoise1(String choise1) {
        this.choise1 = choise1;
    }

    public String getChoise2() {
        return choise2;
    }

    public void setChoise2(String choise2) {
        this.choise2 = choise2;
    }

}