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

    private static void testCreators() {
        System.out.println("=== testCreators ===");

        // สร้างจาก List
        BoundedStack stack = new BoundedStack(3, Arrays.asList("Ronaldo", "Messi"));

        check("stack is not null", stack != null);
        check("initial size is 2", stack.size() == 2);
        check("contains Ronaldo", stack.contains("Ronaldo"));
        check("contains Messi", stack.contains("Messi"));
        check("not empty", !stack.isEmpty());
        check("not full", !stack.isFull());

        // เต็มพอดี
        BoundedStack full = new BoundedStack(2, Arrays.asList("Ronaldo", "Messi"));
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
            new BoundedStack(2, Arrays.asList("Ronaldo", "Messi", "Neymar"));
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

    private static void testPush() {
        System.out.println("=== testPush ===");

        BoundedStack stack = new BoundedStack(3);

        // push ปกติ
        stack.push("Ronaldo");
        check("size after first push", stack.size() == 1);
        check("contains Ronaldo", stack.contains("Ronaldo"));

        stack.push("Messi");
        stack.push("Neymar");

        check("size after three pushes", stack.size() == 3);
        check("stack is full", stack.isFull());

        // push ตอนเต็ม
        try {
            stack.push("Iniesta");
            check("push on full stack", false);
        } catch (IllegalStateException e) {
            check("push on full stack", true);
        }

        // push null
        try {
            BoundedStack stack2 = new BoundedStack(2);
            stack2.push(null);
            check("push null", false);
        } catch (IllegalArgumentException e) {
            check("push null", true);
        }
    }

    private static void testPop() {
        System.out.println("=== testPop ===");

        BoundedStack stack = new BoundedStack(3);

        // เตรียมข้อมูล
        stack.push("Ronaldo");
        stack.push("Messi");
        stack.push("Neymar");

        // pop ตัวบนสุด
        String value = stack.pop();
        check("pop returns top element", value.equals("Neymar"));
        check("size after pop", stack.size() == 2);

        // pop อีกครั้ง
        value = stack.pop();
        check("second pop returns Messi", value.equals("Messi"));

        // pop จนว่าง
        value = stack.pop();
        check("third pop returns Ronaldo", value.equals("Ronaldo"));
        check("stack is empty", stack.isEmpty());

        // pop ตอนว่าง
        try {
            stack.pop();
            check("pop empty stack", false);
        } catch (IllegalStateException e) {
            check("pop empty stack", true);
        }
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
        s.contains("Ronaldo");
        s.elements();
        check("observers have no side effects", s.size() == before);
    }

    /**
     * ทดสอบการสร้าง BoundedStack ใหม่จาก stack เดิม โดยใช้ shuffled()
     */
    private static void testProducer() {
        System.out.println("=== testProducer ===");

        BoundedStack original = new BoundedStack(5);
        original.push("Ronaldo");
        original.push("Messi");
        original.push("Neymar");
        original.push("Iniesta");

        BoundedStack shuffled = original.shuffled();

        check("producer creates a new object", shuffled != original);
        check("shuffled has the same size", shuffled.size() == original.size());

        List<String> a = new ArrayList<>(original.elements());
        List<String> b = new ArrayList<>(shuffled.elements());
        Collections.sort(a);
        Collections.sort(b);

        check("shuffled contains same elements", a.equals(b));

        check("original unchanged",
                original.elements().equals(Arrays.asList("Ronaldo", "Messi", "Neymar", "Iniesta")));

        shuffled.push("Yamal");

        check("mutating shuffled does not affect original",
                original.size() == 4 && shuffled.size() == 5);

        BoundedStack empty = new BoundedStack(4);
        BoundedStack emptyShuffled = empty.shuffled();

        check("shuffle empty stack", emptyShuffled.size() == 0);
    }

    private static void testRepresentationExposure() {
        System.out.println("\n=== testRepresentationExposure ===");

        // ขาออก: แก้ list ที่ได้จาก elements() ต้องไม่กระทบ rep
        BoundedStack s = new BoundedStack(4);
        s.push("Ronaldo");

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
        List<String> input = new ArrayList<String>(Arrays.asList("Ronaldo", "Messi"));
        BoundedStack p = new BoundedStack(4, input);

        input.clear();
        check("clearing constructor argument does not affect stack",
                p.size() == 2);

        input.add("injected");
        check("adding to constructor argument does not affect stack",
                !p.contains("injected"));
    }

}