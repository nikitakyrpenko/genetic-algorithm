package drawing;

import domain.Individual;
import domain.utils.fitness.FitnessFunctionDescription;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import javax.imageio.ImageIO;
import javax.swing.*;


public class Plotter extends JComponent {

    private double maxX;
    private double minX;
    private double maxY = 0;
    private double minY = 0;
    private Function<Double, Double> func;
    private List<Individual> individuals;
    private JFrame frame;
    private String title = "Function";
    private int width = 800;
    private int height = 800;

    public void setFunction(FitnessFunctionDescription f) {
        Function<Individual, Double>func = f.getFitnessFunction();
        this.func = num -> {
            double[] chromosome = {num};
            Individual i = new Individual(chromosome, func);
            return func.apply(i);
        };
        this.maxX = f.getHigh();
        this.minX = f.getLow();
    }

    public void setYBounds(double min, double max) {
        this.minY = min;
        this.maxY = max;
    }

    public void setTitle(String t) {
        this.title = t;
    }

    public void setIndividuals(List<Individual> individuals) {
        this.individuals = individuals;
    }

    public void capture() {
        if (this.frame == null) { return; }
        Component c = this.frame.getContentPane();
        BufferedImage image = new BufferedImage(c.getWidth(), c.getHeight(), BufferedImage.TYPE_INT_RGB);
        c.paint(image.getGraphics());
        String pattern = "dd-mm-yyyy_HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date());
        try {
            ImageIO.write(image, "png", new File("screenshots/" + this.title + "_" + date + ".png"));
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void draw() {
        this.frame = new JFrame();
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
        int W = this.getWidth();
        int H = this.getHeight();
        int h = H/2;
        double scaleX = W / (Math.abs(this.maxX) + Math.abs(this.minX));
        double scaleY = 1.0;
        int minXAbs = (int)Math.round(Math.abs(this.minX) * scaleX);
        if (this.minY != 0 || this.maxY != 0) {
            scaleY = H / (Math.abs(this.maxY) + Math.abs(this.minY));
            h = (int)Math.round(Math.abs(this.maxY) * scaleY);
        }

        Graphics2D g1 = (Graphics2D) g;
        g1.setStroke(new BasicStroke(2));
        g1.setColor(Color.lightGray);
        g1.drawLine(0,h,W,h);
        g1.drawLine(minXAbs,0,minXAbs,H);
        g1.drawString("0", minXAbs - 10, h + 13);

        g1.setStroke(new BasicStroke(2));
        g1.setColor(Color.black);

        Polygon p = new Polygon();

        for (int x = 0; x <= W; x+=2) {
            double X = this.minX + x;
            int y = h - (int)(Math.round(scaleY * this.func.apply(X / scaleX)));
            p.addPoint(x, y);
        }

        for (Individual i : this.individuals) {
            double x = i.getChromosome()[0] * scaleX;
            int Y = h - (int)Math.round(scaleY * this.func.apply(x / scaleX));
            this.drawIndividual(g1, (int)Math.round(x), Y);
        }

        g1.drawPolyline(p.xpoints, p.ypoints, p.npoints);
    }

    public void close() {
        this.frame.dispose();
        this.frame = null;
    }
}