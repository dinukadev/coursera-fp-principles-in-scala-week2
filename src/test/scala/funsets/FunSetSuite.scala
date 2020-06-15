package funsets

import org.junit._

/**
  * This class is a test suite for the methods in object FunSets.
  *
  * To run this test suite, start "sbt" then run the "test" command.
  */
class FunSetSuite {

  import FunSets._



  /**
    * When writing tests, one would often like to re-use certain values for multiple
    * tests. For instance, we would like to create an Int-set and have multiple test
    * about it.
    *
    * Instead of copy-pasting the code for creating the set into every test, we can
    * store it in the test class using a val:
    *
    * val s1 = singletonSet(1)
    *
    * However, what happens if the method "singletonSet" has a bug and crashes? Then
    * the test methods are not even executed, because creating an instance of the
    * test class fails!
    *
    * Therefore, we put the shared values into a separate trait (traits are like
    * abstract classes), and create an instance inside each test method.
    *
    */

  trait TestSets {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)
    val s4 = singletonSet(1)
  }

  @Test def `contains is implemented`: Unit = {
    new TestSets {
      assert(contains(s1, 1))
    }
  }

  /**
    * This test is currently disabled (by using @Ignore) because the method
    * "singletonSet" is not yet implemented and the test would fail.
    *
    * Once you finish your implementation of "singletonSet", remvoe the
    *
    * @Ignore annotation.
    */
  @Test def `singleton set one contains one`: Unit = {

    /**
      * We create a new instance of the "TestSets" trait, this gives us access
      * to the values "s1" to "s3".
      */
    new TestSets {
      /**
        * The string argument of "assert" is a message that is printed in case
        * the test fails. This helps identifying which assertion failed.
        */
      assert(contains(s1, 1), "Singleton")
      assert(contains(s2, 2), "Singleton1")
      assert(contains(s3, 3), "Singleton2")
    }
  }

  @Test def `union contains all elements of each set`: Unit = {
    new TestSets {
      val s = union(s1, s2)
      assert(contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")
    }
  }

  @Test def `intersect contains unique elements of each set`: Unit = {
    new TestSets {
      val s = intersect(s1, s4)
      assert(contains(s, 1), "Intersect 1")
      val ss = intersect(s2, s3)
      assert(!exists(ss, x => x==2), "Intersect 2")

    }
  }

  @Test def `difference of each set`: Unit = {

    new TestSets {
      val s = diff(s3, s1)
      assert(contains(s, 3), "Difference")

    }
  }

  @Test def `filter of set`: Unit = {

    new TestSets {
      val s = filter(s1, x => x > 0)
      assert(contains(s, 1), "filter")

    }
  }

  @Test def `for all of set`: Unit = {

    new TestSets {
      val s = forall(s1, x => x > 0)
      assert(s, "filter")

    }
  }

  @Test def `map of set`: Unit = {

    new TestSets {
      val s = map(s1, x=> x+1)
      assert(contains(s, 2), "map")

    }
  }

  @Test def `toString of set`: Unit = {

    new TestSets {
      val s = FunSets.toString(s1)
      println(s)
      assert(s=="{1}", "toString")

    }
  }


  @Rule def individualTestTimeout = new org.junit.rules.Timeout(10 * 1000)
}
