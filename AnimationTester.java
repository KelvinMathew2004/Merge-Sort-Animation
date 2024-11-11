import java.awt.*;
import javax.swing.*;

/**
   This program animates a sort algorithm.
*/
public class AnimationTester {
   // Default array and JFrame sizes
   private static final int VALUES_LENGTH = 30;
   private static final int FRAME_WIDTH = 300;
   private static final int FRAME_HEIGHT = 300;
   public static void main(String[] args)
   {
      // Create the JFrame.  Tell it the program to exit when the JFrame is closed.
      JFrame frame = new JFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      // Create a panel.  See ArrayComponent.java for details.
      // Add the panel to the frame.
      ArrayComponent panel = new ArrayComponent();
      frame.add(panel, BorderLayout.CENTER);

      // Set the default size of the frame and make it Visible.
      frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
      frame.setVisible(true);

      // Create an array of doubles with a default size of VALUES_LENGTH.
      // Then iterate through each array element and assign it a value 
      // equal to a random value (between 0 and 1) times the height of the panel.
      // This will create an 'array' of bars of different lengths.
      Double[] values = new Double[VALUES_LENGTH];
      for (int i = 0; i < values.length; i++)
         values[i] = Math.random() * panel.getHeight();

      // Create a Runnable that uses the run() method in the Sorter class.
      // Create a thread and assign it the runnable (the "unit of work")
      // And the start the thread. 
      SortPrep s = new SortPrep(values, panel);
      s.prepareToSort();
   }

}
