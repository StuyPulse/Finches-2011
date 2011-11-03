package Code.graphics.GestureProgrammer.GestureProgrammerUtils;

import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Polygon;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

/**
 * @author Alex Styler (astyler@gmail.com)
 */

public class FinchSimViewPanel extends JPanel
   {
   private final int pixelSize = 300;
   private final int middle = pixelSize / 2;

   //temp
   private double left = 0.0;
   private double right = 0.0;
   private Color beakColor = Color.BLACK;

   private final Polygon finchBody = new Polygon(new int[]{middle, middle + 39, middle - 39}, new int[]{middle - 40, middle + 30, middle + 30}, 3);
   private final Polygon leftWheel = new Polygon(new int[]{middle - 30, middle - 30, middle - 40, middle - 40}, new int[]{middle + 20, middle + 40, middle + 40, middle + 20}, 4);
   private final Polygon rightWheel = new Polygon(new int[]{middle + 30, middle + 30, middle + 40, middle + 40}, new int[]{middle + 20, middle + 40, middle + 40, middle + 20}, 4);
   private final Polygon beak = new Polygon(new int[]{middle, middle + 10, middle - 10}, new int[]{middle - 40, middle - 24, middle - 24}, 3);

   public FinchSimViewPanel()
      {
      super(true);

      Dimension size = new Dimension(pixelSize, pixelSize);
      this.setMinimumSize(size);
      this.setMaximumSize(size);
      this.setSize(size);
      this.setBorder(new LineBorder(Color.BLACK, 1));
      }

   public void paintComponent(Graphics g)
      {
      //draw background
      g.setColor(Color.white);
      g.fillRect(0, 0, pixelSize, pixelSize);

      //draw finch body
      g.setColor(Color.black);
      g.drawPolygon(finchBody);
      g.fillPolygon(leftWheel);
      g.fillPolygon(rightWheel);

      //draw beak
      g.setColor(beakColor);
      g.fillPolygon(beak);

      //draw wheel velocities
      g.setColor(Color.magenta);
      int fullLength = -1;

      int left = (int)(this.left * fullLength);
      int offset = left < 0 ? -5 : 5;
      g.drawLine(middle - 35, middle + 30, middle - 35, middle + 30 + left);
      g.drawLine(middle - 35, middle + 30 + left, middle - 40, middle + 30 + left - offset);
      g.drawLine(middle - 35, middle + 30 + left, middle - 30, middle + 30 + left - offset);

      int right = (int)(this.right * fullLength);
      offset = right < 0 ? -5 : 5;
      g.drawLine(middle + 35, middle + 30, middle + 35, middle + 30 + right);
      g.drawLine(middle + 35, middle + 30 + right, middle + 40, middle + 30 + right - offset);
      g.drawLine(middle + 35, middle + 30 + right, middle + 30, middle + 30 + right - offset);
      }

   public void update(double left, double right, Color beak)
      {
      this.left = left;
      this.right = right;
      this.beakColor = beak;
      this.repaint();
      }
   }
