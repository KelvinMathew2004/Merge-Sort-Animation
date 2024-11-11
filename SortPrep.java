import java.util.*;

/**
   This runnable executes a sort algorithm.
   When two elements are compared, the algorithm
   pauses and updates a panel.
*/
public class SortPrep 
{
   private Double[] values;
   private ArrayComponent panel;
   private static final int DELAY = 100;
   /**
      Constructs the sorter.
      @param values the array to sort
      @param panel the panel for displaying the array
   */
   public SortPrep(Double[] values, ArrayComponent panel)
   {
      this.values = values;
      this.panel = panel;
   }

   // This is the code that is run by (any) thread instance that has had
   // an instance of Sorter passed to it.  See AnimationTester.java
   public void prepareToSort()
   {
      // Define and instantiate a comparator for Doubles.
      // Comparator is a 'funtional interface' provided by Java that
      // has default methods that can be overridden.  A Functional Interface
      // can be thought of a group of methods that are not associated
      // with any class
      Comparator<Double> comp = new
         Comparator<Double>()
         {
            public int compare(Double d1, Double d2)
            {
               // Draw the bars with the current sorting of the arrays
               // and fill the ones whose values match d1 and d2.
               panel.setValues(values, d1, d2);
               // Sleep a while and then when the sleep returns an exception
               // interrupt who ever else is running and run me again.
               try
               {
                  Thread.sleep(DELAY);
               }
               catch (InterruptedException exception)
               {
                  Thread.currentThread().interrupt();
               }
               // Return 0 if d1 and d2 are equal.
               // negative integer if d1 is less than d2
               // positive integer if d2 is greater than d2
               return (d1).compareTo(d2);
            }
         };
      int mid = values.length/2;
      Runnable r1 = new MergeSorter<>(values,0,mid,comp);
      Runnable r2 = new MergeSorter<>(values,mid+1,values.length-1,comp);
      Thread t1 = new Thread(r1,"LeftHalf");      
      Thread t2 = new Thread(r2,"RightHalf");

      t1.start();
      t2.start();
      try {
         t1.join();
         t2.join();
      } catch (InterruptedException e) {
         System.out.println("Warning: Could not join at least one thread: " + e);
      } finally {
         MergeSorter.merge(values, 0, mid, values.length-1, comp);
         panel.setValues(values, null, null);
      }
   }
      




}
