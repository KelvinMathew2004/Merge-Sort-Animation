import java.util.*;

/**
 * This runnable executes a sort algorithm.
 * When two elements are compared, the algorithm
 * pauses and updates a panel.
 */
public class SortPrep {
   private Double[] values;
   private ArrayComponent panel;
   private static final int DELAY = 100;

   /**
    * Constructs the sorter.
    * 
    * @param values the array to sort
    * @param panel  the panel for displaying the array
    */
   public SortPrep(Double[] values, ArrayComponent panel) {
      this.values = values;
      this.panel = panel;
   }

   // This is the code that is run by (any) thread instance that has had
   // an instance of Sorter passed to it. See AnimationTester.java
   public void prepareToSort() {
      // Define and instantiate a comparator for Doubles.
      // Comparator is a 'funtional interface' provided by Java that
      // has default methods that can be overridden. A Functional Interface
      // can be thought of a group of methods that are not associated
      // with any class
      Comparator<Double> comp = new Comparator<Double>() {
         public int compare(Double d1, Double d2) {
            // Draw the bars with the current sorting of the arrays
            // and fill the ones whose values match d1 and d2.
            panel.setValues(values, d1, d2);
            // -- We want to slow down the computation so that the user can watch and follow
            // the sorting in the
            // -- animation. So this code puts 'my' thread to sleep for a while (DELAY)
            // Use Thread.sleep() to put my thread to sleep for a while. When the sleep
            // finishes because my
            // thread gets an interrupt, catch the interrupt and stop whatever thread is
            // currently running
            // (via yet another interrupt) and then I resume execution when I am rescheduled
            // (ie: the OS runs me again).
            try {
               Thread.sleep(DELAY);
            } catch (InterruptedException exception) {
               Thread.currentThread().interrupt();
            }
            // Here we are defining what the comparison does which is the
            // standard way Doubles are compared and ordered. This

            // Return 0 if d1 and d2 are equal.
            // negative integer if d1 is less than d2
            // positive integer if d2 is greater than d2
            return (d1).compareTo(d2);
         }
      };
      int mid = values.length / 2; // 15
      int quart = mid / 2; // 7
      Runnable r1 = new MergeSorter(values, 0, quart, comp); // 0 to 7 so 8 elements
      Runnable r2 = new MergeSorter(values, quart+1, mid, comp); // 8 to 15 so 8 elements
      Runnable r3 = new MergeSorter(values, mid+1, mid+quart, comp); // 16 to 22 so 7 elements
      Runnable r4 = new MergeSorter(values, mid+quart + 1, values.length - 1, comp); // 23 to 29 so 7 elements
      Thread t1 = new Thread(r1, "1st Quarter");
      Thread t2 = new Thread(r2, "2nd Quarter");
      Thread t3 = new Thread(r3, "3rd Quarter");
      Thread t4 = new Thread(r4, "4th Quarter");

      t1.start();
      t2.start();
      t3.start();
      t4.start();
      try {
         t1.join();
         t2.join();
         t3.join();
         t4.join();
      } catch (InterruptedException e) {
         System.out.println("Warning: Could not join at least one thread: " + e);
      } finally {
         MergeSorter.merge(values, 0, quart, mid, comp); // 0 to 7 to 15
         panel.setValues(values, null, null);

         MergeSorter.merge(values, mid+1, mid+quart, values.length - 1, comp); // 15 to 22 to 29
         panel.setValues(values, null, null);

         MergeSorter.merge(values, 0, mid, values.length - 1, comp); // 0 to 15 to 29
         // Do the final draw of the animation, making sure none of the bars
         // are marked (colored)
         panel.setValues(values, null, null);
      }
   }

}
