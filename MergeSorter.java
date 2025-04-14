import java.util.*;

public class MergeSorter implements Runnable {
   /**
    * @param a    the array to sort
    * @param from the first index of the range to sort
    * @param to   the last index of the range to sort
    * @param comp the comparator to compare array elements
    */
   private Double[] a;
   private int from;
   private int to;
   private Comparator<Double> comp;

   // The most complicated part of this constructor is problably the
   // Comparator<? super E> comp parameter. The ? super E means that
   // the comparator will work with generic type E but any of its parents.
   // The use follows the Java Generic PECS rule:
   // https://howtodoinjava.com/java/generics/java-generics-what-is-pecs-producer-extends-consumer-super/
   public MergeSorter(Double[] a, int from, int to,
         Comparator<Double> comp) {
      this.a = a;
      this.from = from;
      this.to = to;
      this.comp = comp;
   }

   public void run() {
      mergeSort(a, from, to, comp);
   }

   public void mergeSort(Double[] a, int from, int to,
         Comparator<Double> comp) {
      // Here's the base case which checks for the simplist case.
      // It is also the case when the recursion stops going deeper and returns.
      // In this case, we cannot divide the work further, so we return.
      if (from == to)
         return;
      int mid = (from + to) / 2;
      // Sort the first and the second half
      // Start sorting and then redraw.
      // NOTE: The 'work' of this recursion is being done in comp (the Comparator)
      // which

      mergeSort(a, from, mid, comp);
      mergeSort(a, mid + 1, to, comp);
      merge(a, from, mid, to, comp);
   }

   /**
    * Merges two adjacent subranges of an array
    * 
    * @param a    the array with entries to be merged
    * @param from the index of the first element of the
    *             first range
    * @param mid  the index of the last element of the
    *             first range
    * @param to   the index of the last element of the
    *             second range
    * @param comp the comparator to compare array elements
    */
   public static void merge(Double[] givenArray,
         int from, int mid, int to, Comparator<Double> comp) {
      // size is the size of the range of the array to be merged
      int size = to - from + 1;

      // Merge both halves into a temporary array b
      Double[] tempArray = new Double[size];

      // Set i1 to be the first element to be considered in the _from_ range
      // Set i2 to be the first element to be considered in the _to_ range
      // Set j to be the first open position in the temporary array tempArray
      int i1 = from;
      int i2 = mid + 1;
      int j = 0;

      // As long as neither i1 nor i2 past the end, move
      // the smaller element into b
      while (i1 <= mid && i2 <= to) {
         if (comp.compare(givenArray[i1], givenArray[i2]) < 0) {
            tempArray[j] = givenArray[i1];
            i1++;
         } else {
            tempArray[j] = givenArray[i2];
            i2++;
         }
         j++;
      }

      // Note that only one of the two while loops
      // below is executed

      // Copy any remaining entries of the first half
      while (i1 <= mid) {
         tempArray[j] = givenArray[i1];
         i1++;
         j++;
      }

      // Copy any remaining entries of the second half
      while (i2 <= to) {
         tempArray[j] = givenArray[i2];
         i2++;
         j++;
      }

      // Copy back from the temporary array
      for (j = 0; j < size; j++)
         givenArray[from + j] = tempArray[j];
   }
}