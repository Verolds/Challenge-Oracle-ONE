import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Conversor_Moneda extends JFrame implements ActionListener {

    private final JTextField cantidad;
    private final JTextField resultado;

    private final JComboBox<String> tipo_moneda;
    private final JComboBox<String> moneda_deseada;

    private final JButton calcular;
    private final JButton intercambio;
    private final JButton borrar;

    private static final String monedas[] = {"Peso mexicano", "Dólar estadounidense", "Euros", "Libras Esterlinas", "Yen Japonés", "Won surcoreano"};


    @Override
    public void actionPerformed(ActionEvent e) {
    }

    //constructor
    public Conversor_Moneda() {

        setLayout(new FlowLayout());
        setTitle("Conversor");
        setBounds(300, 300, 250, 220);
        setResizable(false);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.gray);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //Tipo de Moneda
        this.tipo_moneda = new JComboBox<String>(monedas);
        add(tipo_moneda);

        //Campo de texto
        this.cantidad = new JTextField(10);
        add(cantidad);

        //Boton de intercambio
        this.intercambio = new JButton("<->");

        intercambio.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evento) {

                        //se limpia el campo de texto donde sale el resultado
                        resultado.setText("");

                        Pattern patron = Pattern.compile("\\d+\\.?\\d*");
                        //se hace una relacion entre el patron y el dato que se quiera comparar
                        Matcher match1 = patron.matcher(cantidad.getText().toString());

                        //auxiliar para guardar el indice seleccionado del valor original
                        int original = tipo_moneda.getSelectedIndex();
                        //se obtiene el indice de la unidad a convertir
                        int convertir = moneda_deseada.getSelectedIndex();
                        //se pone en el valor original el indice del valor deseado deseada
                        tipo_moneda.setSelectedIndex(moneda_deseada.getSelectedIndex());
                        //se pone en el valor deseaso el auxiliar que tenia el indice del original
                        moneda_deseada.setSelectedIndex(original);
                        //se declara la variable que guardara la cantidad a convertir, asi como el resultado
                        double c;
                        double r;

                        if (match1.matches()) {
                            c = Float.parseFloat(cantidad.getText());
                            r = convertir(convertir, original, c);
                            resultado.setText(String.valueOf(r));
                        }
                    }
                });

        add(intercambio);

        //Moneda deseada
        moneda_deseada = new JComboBox<String>(monedas);
        moneda_deseada.setSelectedIndex(1);
        add(moneda_deseada);

        //Boton calcular
        this.calcular = new JButton("Calcular");

        calcular.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evento) {
                        //Se limpia el campo de texto del resultado
                        resultado.setText("");

                        Pattern patron = Pattern.compile("\\d+\\.?\\d*");
                        Matcher match = patron.matcher(cantidad.getText().toString());

                        int original = tipo_moneda.getSelectedIndex();
                        int convertir = moneda_deseada.getSelectedIndex();
                        double c;
                        double r;

                        if (match.matches()) {
                            c = Float.parseFloat(cantidad.getText());
                            r = convertir(original, convertir, c);
                            resultado.setText(String.valueOf(r));
                        } else {
                            JOptionPane.showMessageDialog(null, "OCURRIO UN ERROR POR FAVOR INTENTALO DE NUEVO");
                        }
                    }
                });
        add(calcular);

        //campo de texto resultado
        this.resultado = new JTextField(15);
        resultado.setEditable(false);
        add(resultado);

        this.borrar = new JButton("Borrar");
        borrar.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evento) {
                        //Se limpia el campo de texto
                        cantidad.setText("");
                        resultado.setText("");
                    }
                });
        add(borrar);
    }

    //FUNCION CONVERITR MONEDA
    public double convertir(int indexOG, int indexFIN, double c) {

        double[] valor = {1, 0.053, 0.05, 0.044, 7.06, 69.03};
        double r;
        double aux;

        // Convertimos el valor al equivalente en pesos mexicanos
        aux = c * valor[indexFIN];
        //el resultado se obtiene dividiendo
        r = aux / valor[indexOG];
        r = Math.round(r * 1000) / 1000d;
        return r;
    }
}
