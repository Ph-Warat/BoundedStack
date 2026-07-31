# BoundedStack

## รายวิชา
Software Construction

---
## ชื่อผู้จัดทำ
- ชื่อ: พงษ์ภัทร สนธิชัย รหัสนิสิต: 6821651523

- ชื่อ: วรัตม์ ขันทะเขตร์ รหัสนิสิต: 6821651736


## Concept

`BoundedStack` คือ ADT ที่เก็บข้อมูลชนิด `String` ในรูปแบบ Stack (LIFO: Last In First Out) โดยสามารถกำหนดจำนวนสมาชิกสูงสุด (`capacity`) ได้ตั้งแต่ตอนสร้างวัตถุ หาก Stack เต็มจะไม่สามารถเพิ่มข้อมูลได้ และหาก Stack ว่างจะไม่สามารถนำข้อมูลออกได้

ตัวอย่างการใช้งาน

```java
BoundedStack stack = new BoundedStack(3);

stack.push("Ronaldo");
stack.push("Messi");

stack.peek();     // Messi
stack.pop();      // Messi
stack.size();     // 1
```

---

# AF (Abstraction Function)

```
AF(elements, capacity)
=
Stack ของข้อความตามลำดับใน elements
โดยสมาชิกตัวสุดท้ายของ List เป็น Top ของ Stack
```

ตัวอย่าง

```
elements = ["Ronaldo","Messi","Neymar"]

Top
 ↓
Neymar
```

หมายความว่า `pop()` จะคืน `"Neymar"` ก่อน

---

# RI (Representation Invariant)

ข้อมูลภายในต้องเป็นจริงตลอดเวลา

- `elements` ต้องไม่เป็น `null`
- `capacity > 0`
- `elements.size() <= capacity`

มีการตรวจสอบผ่าน `checkRep()` หลังจากสร้าง Object และหลังจากมีการเปลี่ยนแปลงข้อมูลใน Stack

---

# Rep Exposure

มีการป้องกัน Representation Exposure ดังนี้

- field ทั้งหมดเป็น `private final`
- Constructor ที่รับ `List<String>` จะสร้าง `ArrayList` ใหม่ ไม่เก็บ reference เดิม
- เมธอด `elements()` คืนค่าเป็น `new ArrayList<>(elements)` ทุกครั้ง

ดังนั้นหากผู้ใช้แก้ไข List ที่ส่งเข้า Constructor หรือ List ที่ได้จาก `elements()` จะไม่ส่งผลต่อข้อมูลภายใน Stack

---

# Method

## Creator

### `BoundedStack(int capacity)`

สร้าง Stack ว่าง

**Requires**

- `capacity > 0`

**Effects**

สร้าง Stack ว่างที่มีความจุเท่ากับ `capacity`

**Throws**

- `IllegalArgumentException`

---

### `BoundedStack(int capacity, List<String> items)`

สร้าง Stack จากข้อมูลเริ่มต้น

**Requires**

- `capacity > 0`
- `items != null`
- `items.size() <= capacity`

**Effects**

สร้าง Stack ใหม่และคัดลอกข้อมูลจาก `items`

**Throws**

- `IllegalArgumentException`

---

## Mutator

### `push(String s)`

เพิ่มข้อมูลไว้บนสุดของ Stack

**Requires**

- `s != null`
- Stack ต้องไม่เต็ม

**Effects**

เพิ่มข้อมูลลงใน Stack

**Throws**

- `IllegalArgumentException`
- `IllegalStateException`

---

### `pop()`

นำข้อมูลบนสุดออกจาก Stack

**Requires**

- Stack ต้องไม่ว่าง

**Effects**

ลบสมาชิกตัวบนสุดออก

**Returns**

ข้อมูลที่ถูกนำออก

**Throws**

- `IllegalStateException`

---

## Observer

### `size()`

คืนจำนวนสมาชิกใน Stack

---

### `isEmpty()`

คืนค่า `true` เมื่อ Stack ว่าง

---

### `isFull()`

คืนค่า `true` เมื่อ Stack เต็ม

---

### `contains(String s)`

ตรวจสอบว่ามีข้อมูลนี้อยู่ใน Stack หรือไม่

---

### `peek()`

คืนข้อมูลบนสุดโดยไม่ลบออก

**Requires**

- Stack ต้องไม่ว่าง

**Throws**

- `IllegalStateException`

---

### `elements()`

คืนสำเนาของข้อมูลทั้งหมดใน Stack

ผู้ใช้สามารถแก้ไข List ที่คืนกลับได้ โดยไม่กระทบข้อมูลภายใน

---

## Producer

### `shuffled()`

สร้าง `BoundedStack` ตัวใหม่ที่มีข้อมูลชุดเดิมแต่เรียงลำดับแบบสุ่ม

- Stack เดิมไม่เปลี่ยนแปลง
- ใช้ `Collections.shuffle()`

---

# ตาราง Exception

| Method | เงื่อนไข | Exception |
|---------|----------|-----------|
| `BoundedStack(int)` | `capacity <= 0` | `IllegalArgumentException` |
| `BoundedStack(int,List)` | `capacity <= 0` | `IllegalArgumentException` |
| | `items == null` | `IllegalArgumentException` |
| | `items.size() > capacity` | `IllegalArgumentException` |
| `push()` | `s == null` | `IllegalArgumentException` |
| | Stack เต็ม | `IllegalStateException` |
| `pop()` | Stack ว่าง | `IllegalStateException` |
| `peek()` | Stack ว่าง | `IllegalStateException` |

---

# Test Cases

ชุดทดสอบประกอบด้วย

- Constructor
- push()
- pop()
- Observer Methods
- shuffled()
- Representation Exposure

โดย Test Runner สามารถแสดงผล

- PASS
- FAIL
- Summary

พร้อมนับจำนวน Test ที่ผ่านและไม่ผ่านอัตโนมัติ

---

# การคอมไพล์และรัน

```bash
javac BoundedStack.java BoundedStackTest.java

java -ea BoundedStackTest
```

เปิด `-ea` เพื่อให้ `assert` และ `checkRep()` ทำงาน

---

# โครงสร้างไฟล์

```
.
├── BoundedStack.java
├── BoundedStackTest.java
└── README.md
```
