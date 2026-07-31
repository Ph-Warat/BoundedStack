# รายงานชิ้นงาน BoundedStack

## รายวิชา
Software Construction

---
## ชื่อผู้จัดทำ
- ชื่อ: พงษ์ภัทร สนธิชัย รหัสนิสิต: 6821651523

- ชื่อ: วรัตม์ ขันทะเขตร์ รหัสนิสิต: 6821651736

---

# 1. วัตถุประสงค์

พัฒนา Abstract Data Type (ADT) ชนิด BoundedStack ซึ่งเป็น Stack ที่มีจำนวนสมาชิกสูงสุดตามค่าที่กำหนด พร้อมทั้งสร้างชุดทดสอบ (Test Runner) เพื่อทดสอบการทำงานของทุก operation และตรวจสอบ Representation Exposure

---

# 2. การออกแบบ ADT

## Data Representation

```java
private final List<String> elements;
private final int capacity;
```

- elements ใช้เก็บข้อมูลใน Stack
- capacity เป็นจำนวนสมาชิกสูงสุดของ Stack

---

## Abstraction Function (AF)

แทน Stack ของข้อมูลชนิด String ที่มีขนาดไม่เกิน capacity โดยสมาชิกตัวสุดท้ายของ List ถือเป็น Top ของ Stack

---

## Representation Invariant (RI)

- elements ต้องไม่เป็น null
- capacity > 0
- จำนวนสมาชิกต้องไม่เกิน capacity

ตรวจสอบทุกครั้งด้วย

```java
checkRep();
```



---

# 3. Operations

## Constructors

### BoundedStack(int capacity)

สร้าง Stack ว่าง

Precondition

- capacity > 0

Postcondition

- ได้ Stack ว่างที่สามารถเก็บข้อมูลได้ตาม capacity

---

### BoundedStack(int capacity, List<String> items)

สร้าง Stack จากข้อมูลเริ่มต้น

Precondition

- capacity > 0
- items != null
- items.size() ≤ capacity

Postcondition

- Stack มีข้อมูลเหมือน items

---

## push(String s)

หน้าที่

เพิ่มข้อมูลบนสุดของ Stack

Precondition

- s ไม่เป็น null
- Stack ยังไม่เต็ม

Postcondition

- สมาชิกเพิ่มขึ้น 1 ตัว
- s เป็น Top ของ Stack

Exception

- IllegalArgumentException
- IllegalStateException

---

## pop()

หน้าที่

นำข้อมูลบนสุดออกจาก Stack

Precondition

- Stack ต้องไม่ว่าง

Postcondition

- สมาชิกลดลง 1 ตัว

Return

- ข้อมูลที่ถูกนำออก

Exception

- IllegalStateException

---

## peek()

คืนข้อมูลบนสุดโดยไม่ลบออก

Precondition

- Stack ต้องไม่ว่าง

---

## size()

คืนจำนวนสมาชิกปัจจุบัน

---

## isEmpty()

คืนค่า true เมื่อ Stack ว่าง

---

## isFull()

คืนค่า true เมื่อจำนวนสมาชิกเท่ากับ capacity

---

## contains(String s)

ตรวจสอบว่ามีข้อมูลอยู่ใน Stack หรือไม่

---

## elements()

คืนสำเนาของ List ภายใน

ใช้ Defensive Copy เพื่อป้องกัน Representation Exposure

---

## shuffled()

สร้าง BoundedStack ตัวใหม่ที่มีสมาชิกเดิมแต่เรียงลำดับแบบสุ่ม

Stack เดิมจะไม่เปลี่ยนแปลง

---

# 4. Representation Exposure

มีการป้องกันทั้งขาเข้าและขาออก

## Constructor

ใช้

```java
new ArrayList<>(items)
```

เพื่อคัดลอกข้อมูลก่อนเก็บไว้ใน Stack

---

## elements()

คืนค่า

```java
new ArrayList<>(elements)
```



---

# 5. การทดสอบ

Test Runner ถูกแบ่งออกเป็น

- testCreators()
- testPush()
- testPop()
- testObservers()
- testProducer()
- testRepresentationExposure()

โดยมี helper

```java
check(...)
```


---

## รายการที่ทดสอบ

### Constructor

- สร้าง Stack ปกติ
- capacity = 0
- capacity ติดลบ
- items เป็น null
- items มากกว่า capacity

### push()

- push ปกติ
- push จนเต็ม
- push null
- push ตอนเต็ม

### pop()

- pop ปกติ
- pop จนว่าง
- pop Stack ว่าง

### Observer

- size()
- isEmpty()
- isFull()
- contains()
- elements()
- peek()

### Producer

- shuffled() คืน Object ใหม่
- สมาชิกครบถ้วน
- Stack เดิมไม่เปลี่ยน
- shuffle Stack ว่าง

### Representation Exposure

ตรวจสอบทั้ง

- Constructor
- elements()

ว่าไม่สามารถแก้ไขข้อมูลภายใน Stack ได้

---

# 6. สรุป

ชิ้นงานสามารถทำงานได้ครบตามข้อกำหนดของ ADT BoundedStack โดยมีการกำหนด Abstraction Function และ Representation Invariant อย่างชัดเจน ใช้ Defensive Copy เพื่อป้องกัน Representation Exposure และมีชุดทดสอบที่ครอบคลุมทั้งกรณีปกติและกรณีเกิด Exception พร้อมแสดงผลการทดสอบและสรุปผลอัตโนมัติ
