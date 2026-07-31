import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Test runner 
 */
public class BoundedStackTest {

    private static int passed = 0;
    private static int failed = 0;

    /** helper กลาง — พิมพ์ PASS/FAIL และนับผลให้เอง 
    * 
    */
    private static void check(String name, boolean condition) {
        if (condition) {
            passed++;
            System.out.println("[PASS] " + name);
        } else {
            failed++;
            System.out.println("[FAIL] " + name);
        }
    }

    public static void main(String[] args) {
        boolean assertsOn = false;
        assert assertsOn = true;
        if (!assertsOn) {
            System.out.println("WARNING: assertions disabled"
                    + " - re-run with: java -ea BoundedStackTest\n");
        }

        System.out.println("=== BoundedStack Test Suite ===\n");

        testCreators();
        testPush();
        testPop();
        testObservers();
        testProducer();
        testRepresentationExposure();

        System.out.println("\n=== Summary ===");
        System.out.println("Passed: " + passed);
        System.out.println("Failed: " + failed);
        System.out.println("Total : " + (passed + failed));
        System.out.println(failed == 0 ? "ALL TESTS PASSED" : "SOME TESTS FAILED");

        if (failed > 0) {
            System.exit(1);
        }
    }

    private static void testRepresentationExposure() {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'testRepresentationExposure'");
    }

    private static void testProducer() {
        // TODO Auto-generated method stub
       // throw new UnsupportedOperationException("Unimplemented method 'testProducer'");
    }

    private static void testObservers() {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'testObservers'");
    }

    private static void testPop() {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'testPop'");
    }

    private static void testPush() {
    System.out.println("=== testPush ===");

    BoundedStack stack = new BoundedStack(3);

    // push ตัวแรก
    stack.push("A");
    check("size after first push", stack.size() == 1);
    check("contains A", stack.contains("A"));
    check("not empty after first push", !stack.isEmpty());

    // push จนครบ capacity
    stack.push("B");
    stack.push("C");

    check("size after three pushes", stack.size() == 3);
    check("stack is full", stack.isFull());

    // push ตอนเต็ม ต้องโยน exception
    try {
        stack.push("D");
        check("push on full stack", false);
    } catch (IllegalStateException e) {
        check("push on full stack", true);
    }

    // push null ต้องโยน exception
    try {
        BoundedStack stack2 = new BoundedStack(2);
        stack2.push(null);
        check("push null", false);
    } catch (IllegalArgumentException e) {
        check("push null", true);
    }
}

    private static void testCreators() {
    System.out.println("=== testCreators ===");

    // สร้างจาก List
    BoundedStack stack = new BoundedStack(3, Arrays.asList("A", "B"));

    check("stack is not null", stack != null);
    check("initial size is 2", stack.size() == 2);
    check("contains A", stack.contains("A"));
    check("contains B", stack.contains("B"));
    check("not empty", !stack.isEmpty());
    check("not full", !stack.isFull());

    // เต็มพอดี
    BoundedStack full = new BoundedStack(2, Arrays.asList("A", "B"));
    check("full stack", full.isFull());

    // capacity = 0
    try {
        new BoundedStack(0, Arrays.asList());
        check("capacity = 0", false);
    } catch (IllegalArgumentException e) {
        check("capacity = 0", true);
    }

    // capacity ติดลบ
    try {
        new BoundedStack(-1, Arrays.asList());
        check("negative capacity", false);
    } catch (IllegalArgumentException e) {
        check("negative capacity", true);
    }

    // จำนวนข้อมูลเกิน capacity
    try {
        new BoundedStack(2, Arrays.asList("A", "B", "C"));
        check("too many items", false);
    } catch (IllegalArgumentException e) {
        check("too many items", true);
    }

    // items เป็น null
    try {
        new BoundedStack(3, null);
        check("null items", false);
    } catch (IllegalArgumentException | NullPointerException e) {
        check("null items", true);
    }
}
}