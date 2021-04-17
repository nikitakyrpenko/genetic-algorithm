package drawing;

import domain.Individual;
import domain.Population;
import providers.FitnessFunctionProvider;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.imageio.ImageIO;
import javax.swing.*;


public class Plotter extends JComponent {

    private static double MAX_X = 2 * Math.PI;
    private List<Individual> individuals = new ArrayList<Individual>();
    private Func func;
    private JFrame frame = new JFrame();
    private String title;
    private int width = 800;
    private int height = 800;

    public void setFunction(Func func) {
        this.func = func;
    }

    public void setTitle(String t) {
        this.title = t;
    }

    public void setIndividuals(ArrayList<Individual> individuals) {
        this.individuals = individuals;

        /*Individual individual = individuals.get(0);

        Function<Individual, Double> fitnessFunction = individual.getFitnessFunction();

        Double y = fitnessFunction.apply(individual);*/
    }

    public void capture() {
        Component c = this.frame.getContentPane();
        BufferedImage image = new BufferedImage(c.getWidth(), c.getHeight(), BufferedImage.TYPE_INT_RGB);
        c.paint(image.getGraphics());
        String pattern = "dd-mm-yyyy_HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date());
        try {
            ImageIO.write(image, "png", new File("snapshot_" + date + ".png"));
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void draw() {
        this.frame.setSize(this.width, this.height);
        this.frame.setTitle(this.title);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setLocationRelativeTo(null);
        this.frame.add(this);
        this.frame.setVisible(true);
    }

    private void drawIndividual(Graphics2D g, int x, int y) {
        int r = 7;
        x = x-(r/2);
        y = y-(r/2);
        Color c = g.getColor();
        g.setColor(Color.RED);
        g.drawOval(x,y,r,r);
        g.setColor(c);
    }

    public void paintComponent(Graphics g) {
        int w = this.getWidth()/2;
        int h = this.getHeight()/2;

        Graphics2D g1 = (Graphics2D) g;
        g1.setStroke(new BasicStroke(2));
        g1.setColor(Color.lightGray);
        g1.drawLine(0,h,w*2,h);
        g1.drawLine(w,0,w,h*2);
        g1.drawString("0", w - 10, h + 13);

        g1.setStroke(new BasicStroke(2));
        g1.setColor(Color.black);

        Polygon p = new Polygon();

        final double SCALE = w / MAX_X;

        for (int x = -w; x <= w; ++x) {
            int X = w + x;
            int Y = h - (int)Math.round(SCALE * this.func.fx(x / SCALE));
            p.addPoint(X, Y);
        }

        for (Integer x : this.individuals) {
            int X = w + x;
            int Y = h - (int)Math.round(SCALE * this.func.fx(x / SCALE));
            this.drawIndividual(g1, X, Y);
        }

        g1.drawPolyline(p.xpoints, p.ypoints, p.npoints);
    }
}