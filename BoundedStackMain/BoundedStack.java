
import java.util.*;
/**
 * BoundedStack คือคลาสที่ใช้สำหรับสร้าง stack ที่มีขนาดจำกัด
 * elements คือ list ของ string ที่เก็บข้อมูลใน stack
 * capacity คือขนาดสูงสุดของ stack
 * 
 */
public class BoundedStack {

    // ===== representation =====
    // Abstraction Function:
    // AF(elements,capacity) คือ abstract function ที่ใช้ในการอธิบายความสัมพันธ์ระหว่าง elements
    // RI
    // 1. elements ต้องไม่เป็น null
    // 2. capacity ต้องเป็นค่าบวก
    // 3. ขนาดของ elements ต้องไม่เกิน capacity

    private final List<String> elements;
    private final int capacity;

    /**
     * 
     * checkRep() เป็นเมธอดที่ใช้ตรวจสอบ representation invariant ของคลาส BoundedStack
     */
    private void checkRep() {
        assert elements != null : "elements must not be null";
        assert capacity > 0 : "capacity must be positive";
        assert elements.size() <= capacity : "elements size must not exceed capacity";
    }    
    /**
     * สร้าง BoundedStack ใหม่
     * @param capacity ขนาดสูงสุดของ stack
     * @param items รายการ element ที่จะเริ่มต้น stack
     */
    public BoundedStack(int capacity, List<String> items) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive");
        }

        if (items.size() > capacity) {
            throw new IllegalArgumentException("Too many items");
        }

        this.capacity = capacity;
        this.elements = new ArrayList<>(items);

        checkRep();
    }

    /**
     * สร้าง BoundedStack ใหม่ โดยใช้ capacity ที่กำหนดและเริ่มต้นด้วย stack ว่าง
     * @param capacity
     */
    public BoundedStack(int capacity) {
    this(capacity, new ArrayList<>());
    }
    /**
     * เพิ่ม element ลงใน stack
     * @param s element ที่จะเพิ่ม
     */
    public void push(String s) {
    if (s == null) {
        throw new IllegalArgumentException("Element cannot be null");
    }

    if (isFull()) {
        throw new IllegalStateException("Stack is full");
    }

    elements.add(s);
    checkRep();
}
    /**
     * นำ element ออกจาก stack
     * @return element ที่ถูกนำออกจาก stack
     */
    public String pop(){
        if (elements.isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        return elements.remove(elements.size() - 1);
    }
    /**
     * ตรวจสอบว่า stack ว่างหรือไม่
     * @return true ถ้า stack ว่าง, false ถ้าไม่ว่าง
     */
    public boolean isEmpty() {
        return elements.isEmpty();
    }

    /**
     * รับคืน element ทั้งหมดใน stack
     * @return รายการ element ทั้งหมดใน stack
     */
    public List<String> elements() {
        return new ArrayList<>(elements);
    }
    /**
     * ตรวจสอบขนาดของ stack
     * @return ขนาดของ stack
     */
    public int size() {
        return elements.size();
    }
    /**
     * ตรวจสอบว่ามี element ที่ระบุอยู่ใน stack
     * @param s element ที่จะตรวจสอบ
     * @return true ถ้ามี element นั้นอยู่ใน stack, false ถ้าไม่มี
     */
    public boolean contains(String s) {
        return elements.contains(s);
    }
    public boolean isFull() {
        return elements.size() == capacity;
    }
}