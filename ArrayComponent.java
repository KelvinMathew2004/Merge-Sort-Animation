import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

/**
 * This component draws an array and sortElements two elements in the
 * array.
 */
public class ArrayComponent extends JComponent {
   // values is an array of doubles each of which represents the
   // length of a bar that will be drawn on the display panel;
   private Double[] values;
   // sortElement1 and sortElement2 represent the array bars that will
   // filled rather than just draw in outline.
   private Double sortElement1;
   private Double sortElement2;

   // The 'synchornized' keyword ensures that only one thread can execute this
   // method at a time.
   // This prevents multiple threads from simultaneously attempting to paint the
   // panel,
   // which could lead to race conditions yielding wierd graphics being drawn.
   // We only have one thread doing the drawing so, in this application, the
   // concern is moot.
   // Calling repaint () in setValues() invokes paintComponent()
   @Override
   public synchronized void paintComponent(Graphics g) {
      if (values == null)
         return;
      // Create a graphics context for drawing. And then determine the width of the
      // bars that represent the lengths of the values in values[].
      // The code also decides whether to fill a rectangle (meaning it's being sorted)
      // or just draw it.
      Graphics2D g2 = (Graphics2D) g;
      int width = getWidth() / values.length;
      for (int i = 0; i < values.length; i++) {
         Double valueToBeDrawn = values[i];
         Rectangle2D bar = new Rectangle2D.Double(width * i, 0, width, valueToBeDrawn);
         // If either of the current bars being drawn is one
         // of the elements currently being sorted, fill the bar with
         // color rather than just draw its outlines.
         if (valueToBeDrawn == sortElement1 || valueToBeDrawn == sortElement2)
            g2.fill(bar);
         else
            g2.draw(bar);
      }
   }

   /**
    * Sets the values to be painted.
    * 
    * @param values       the array of values to display
    * @param sortElement1 the first element of the pair currenlty being sorted
    * @param sortElement2 the second element of the pair currenlty being sorted
    */
   public synchronized void setValues(Double[] values, Double sortElement1, Double sortElement2) {
      // Create a 'shallow' copy of the original values array.
      // The copy contains references to the same Double objects
      // as in the original array.
      this.values = (Double[]) values.clone();
      this.sortElement1 = sortElement1;
      this.sortElement2 = sortElement2;
      // Repaint() invokes the paintComponent() method defined above.
      repaint();
   }
}