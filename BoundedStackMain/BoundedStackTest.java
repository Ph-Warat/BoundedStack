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

    /**
     * helper กลาง — พิมพ์ PASS/FAIL และนับผลให้เอง
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
        System.out.println("\n=== testRepresentationExposure ===");

        // ขาออก: แก้ list ที่ได้จาก elements() ต้องไม่กระทบ rep
        BoundedStack s = new BoundedStack(4);
        s.push("A");

        List<String> got = s.elements();
        got.clear();
        check("clearing result of elements() does not affect stack",
                s.size() == 1);

        got = s.elements();
        got.add("injected");
        check("adding to result of elements() does not affect stack",
                s.size() == 1 && !s.contains("injected"));

        // สองครั้งต้องเป็นคนละ object
        check("elements() returns a fresh list each call",
                s.elements() != s.elements());

        // ขาเข้า: แก้ list ที่ส่งให้ constructor ต้องไม่กระทบ rep
        List<String> input = new ArrayList<String>(Arrays.asList("A", "B"));
        BoundedStack p = new BoundedStack(4, input);

        input.clear();
        check("clearing constructor argument does not affect stack",
                p.size() == 2);

        input.add("injected");
        check("adding to constructor argument does not affect stack",
                !p.contains("injected"));
    }

    /**
     * ทดสอบการสร้าง BoundedStack ใหม่จาก stack เดิม โดยใช้ shuffled()
     */
    private static void testProducer() {
        System.out.println("=== testProducer ===");
        BoundedStack original = new BoundedStack(4);
        original.push("A");
        original.push("B");
        original.push("C");
        original.push("D");
        BoundedStack shuffled = original.shuffled();

        check("shuffled has the same size", shuffled.size() == original.size());

        List<String> a = new ArrayList<String>(original.elements());
        List<String> b = new ArrayList<String>(shuffled.elements());
        Collections.sort(a);
        Collections.sort(b);
        check("shuffled contains exactly the same songs", a.equals(b));

        check("shuffled does not mutate the original",
                original.elements().equals(Arrays.asList("A", "B", "C", "D")));

        // mutate ตัวใหม่ต้องไม่กระทบตัวเดิม
        shuffled.push("E");
        check("mutating the result does not affect the original",
                original.size() == 4);

        // boundary: shuffle เพลย์ลิสต์ว่างต้องไม่พัง
        BoundedStack emptyShuffled = new BoundedStack(4).shuffled();
        check("shuffling an empty stack is safe", emptyShuffled.size() == 0);
    }

    private static void testObservers() {
        System.out.println("\n=== testObservers ===");

        BoundedStack s = new BoundedStack(3);
        check("size reports 0", s.size() == 0);
        check("isEmpty reports true", s.isEmpty());
        check("isFull reports false", !s.isFull());

        boolean peekThrew = false;
        try {
            s.peek();
        } catch (IllegalStateException e) {
            peekThrew = true;
        }
        check("peek throws IllegalStateException on empty stack", peekThrew);

        int before = s.size();
        s.size();
        s.contains("A");
        s.elements();
        check("observers have no side effects", s.size() == before);
    }

    private static void testPop() {
        System.out.println("=== testPop ===");

        BoundedStack stack = new BoundedStack(3);

        // เตรียมข้อมูล
        stack.push("A");
        stack.push("B");
        stack.push("C");

        // pop ตัวบนสุด
        String value = stack.pop();
        check("pop returns top element", value.equals("C"));
        check("size after pop", stack.size() == 2);
        check("stack not full after pop", !stack.isFull());

        // pop อีกครั้ง
        value = stack.pop();
        check("second pop returns B", value.equals("B"));
        check("size after second pop", stack.size() == 1);

        // pop จนว่าง
        value = stack.pop();
        check("third pop returns A", value.equals("A"));
        check("stack is empty", stack.isEmpty());

        // pop ตอนว่าง
        try {
            stack.pop();
            check("pop empty stack", false);
        } catch (IllegalStateException e) {
            check("pop empty stack", true);
        }
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