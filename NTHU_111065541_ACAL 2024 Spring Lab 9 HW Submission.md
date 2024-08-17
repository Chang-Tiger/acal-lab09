(NTHU_111065541_張騰午)  ACAL 2024 Spring Lab 9 HW Submission 
===





[toc]

## Gitlab code link


- Gitlab link - https://course.playlab.tw/git/Tiger_Chang/lab09.git

## Homework 9


### Homework 9-1 5-stage pipelined CPU Implementation
- Please Finish the all others rv32i instrunctions，and pass the **rv32ui_SingleTest/TestALL.S** 



:::warning
Paste the **simulate RegFile result**  Here
![](https://course.playlab.tw/md/uploads/d6f7d8e3-64c6-47a2-bc8d-39b0a3067219.png)

:::
- You can use any single test，and compare to the  **rv32ui_SingleTest/golden.txt** 

### Homework 9-2 Data and Controll Hazards  
- List possible data hazard scenarios and describe how to resolve the hazard in your design.
    :::info
    依據rv32ui_SingleTest-TestDataHazard.asm文件中的指令順序
    1. **Rd of instruction 8 in WB stage is the same as Rs of instruction 11 in ID stage**  
    2. **Rd of instruction 42 in MEM stage is the same as Rs of instruction 44 in ID stage**  
    3. **Rd of instruction 58 in MEM stage is the same as Rs of instruction 60 in ID stage**  
    4. **Rd of instruction 74 in EXE stage is the same as Rs of instruction 75 in ID stage**  
    5. **Rd of instruction 89 in EXE stage is the same as Rs of instruction 90 in ID stage**  
    6. **Rd of instruction 97 in EXE stage is the same as Rs of instruction 98 in ID stage**  
    7. **Rd of instruction 106 in EXE stage is the same as Rs of instruction 107 in ID stage**  
    8. **Rd of instruction 116 in EXE stage is the same as Rs of instruction 117 in ID stage**  
    9. **Rd of instruction 123 in EXE stage is the same as Rs of instruction 124 in ID stage**  
    10. **Rd of instruction 133 in EXE stage is the same as Rs of instruction 134 in ID stage**  
    11. **Rd of instruction 134 in EXE stage is the same as Rs of instruction 135 in ID stage**  
    12. **Rd of instruction 135 in EXE stage is the same as Rs of instruction 136 in ID stage**  
    13. **Rd of instruction 136 in EXE stage is the same as Rs of instruction 137 in ID stage**  
    14. **Rd of instruction 137 in EXE stage is the same as Rs of instruction 138 in ID stage**  
    15. **Rd of instruction 138 in EXE stage is the same as Rs of instruction 139 in ID stage**  
    16. **Rd of instruction 142 in EXE stage is the same as Rs of instruction 143 in ID stage**  
    17. **Rd of instruction 144 in EXE stage is the same as Rs of instruction 145 in ID stage**  
    18. **Rd of instruction 146 in EXE stage is the same as Rs of instruction 147 in ID stage**  
    19. **Rd of instruction 147 in EXE stage is the same as Rs of instruction 148 in ID stage**  
    20. **Rd of instruction 148 in EXE stage is the same as Rs of instruction 149 in ID stage**  
    21. **Rd of instruction 150 in EXE stage is the same as Rs of instruction 151 in ID stage**  

    _
    
    - **How to resolve?**
        hazard發生時IF,ID state指令需要stall。
        如果是WB ID hazard要stall一次，MEM ID hazard要stall兩次，EXE ID hazard要stall三次

    :::
- Please Finish the all others RV32I instrunctions，and pass the **rv32ui_SingleTest/TestDataHazard.S** 

:::warning
Paste the **simulate RegFile result**  Here
![](https://course.playlab.tw/md/uploads/38220de2-95fd-4772-9387-e594b5316ac5.png)

:::
- In the result of register file，if sp(x2) is **zero**，it means you passed the test，otherwise，the value of sp(x2) is the test case that you did not passed.

:::info 
- Bonus:  data forwarding
also use **rv32ui_SingleTest/TestDataHazard.S** to test the Hardware.
:::


### Homework 9-3 Performance Counters and Performance Analysis
先驗證正確性:
![](https://course.playlab.tw/md/uploads/1adb8472-b01b-47a4-a533-c19d9e63d3a9.png)
```mipsasm=
# Caller save pop
    lw    ra, 0(sp)   # MEM[@sp-4] -> ra
    addi  sp, sp, 4
    
    #===========================
    #Load and check data
    lw    t0, 0(a0)
    lw    t0, 8(a0)
    lw    t0, 16(a0)
    lw    t0, 96(a0)
    lw    t0, 100(a0)
    #===========================
    
    # Collee save pop
    lw    s0, 4(sp)   # @s0 -> MEM[@sp-4]
    lw    s1, 0(sp)   # @s1 -> MEM[@sp-8]
    addi  sp, sp, 8
```
在mergeSort.S中加入抽5個數load到t0中看數值是否是遞增的，可以通過以上畫面觀察WB data，可發現其值是遞增
1. Complete **8.~13.** performance counters listed below.
:::info
### Performance counter - HW
8. Mem Read Stall Cycle Count
Count cycles stalled due to memory read.
9. Mem Write Stall Cycle Count
Count cycles stalled due to memory write.
10. Mem Read Request Count
Count Load-type instruction.
11. Mem Write Request Count
Count Store-type instruction.
12. Mem Read Bytes Count
Count bytes read in Load-type instruction(lw/lh/lb - all 4bytes are occupied).
13. Mem Write Bytes Count
Count bytes write in Store-type instruction(sw/sh/sb - all 4bytes are occupied).
:::
:::warning
Paste your gitlab branch URL link
https://course.playlab.tw/git/funfish111065531/lab09-group/-/tree/Tiger?ref_type=heads
:::
3. Complete **2.~4.** Performance analysis listed below.
:::info
### Performance analysis - HW
2. Average Mem Read Request Stall Cycle
Mem Read Stall Cycle Count/Mem Read Request Count
3. Average Mem Write Request Stall Cycle
Mem Write Stall Cycle Count/Mem Write Request Count
4. Total Bus bandwidth requiement (Read + Write, data)
Mem Read Bytes Count + Mem Write Bytes Count
:::
4. Run the **mergesort.S (no vector Extension instruction)** and post the **Performance count and analysis** result.

![](https://course.playlab.tw/md/uploads/30585890-dfae-46cb-b5e7-093e0926bdb3.png =150%x)

5. Explain How a 5-stage pipelined  CPU improves performance compared to a single-cycle CPU
:::warning
5-stage Pipeline把指令分成五個stage，和single cycle比起來能同時執行多個指令增加throughput，也能避免有些資源在執行某些指令時會被閒置，且每個stage執行速度都很快可以使用週期更短的clock運作。
:::

## HW 9-4 Bitmanip Extension (Group Assignment)
### Gitlab code link
- Gitlab link of your branch - 
    - https://course.playlab.tw/git/funfish111065531/lab09-group/-/tree/Tiger?ref_type=heads
- Gitlab link of your group project repo - 
    - https://course.playlab.tw/git/funfish111065531/lab09-group.git

### 硬體架構圖：
- 小組選擇的base CPU架構圖，是誰的呢?
    - 貼圖
    - whose?
:::danger
- 小組要統一一張圖喔!!!
:::



    
### 分工方式:
   - 因為硬體部分比較需要溝通寫法，太多人進行可能人多嘴雜，所以本組分為軟體與硬體兩組，一組兩人，軟體負責emulator,translate,測資的部分，我是軟體組，負責軟體前15個指令
   ![](https://course.playlab.tw/md/uploads/bca91fa4-95fd-4b01-8c1c-07a3c5b239a1.png)

### Emulator functionality - <Instruction_Name>
    > 負責的指令:
|Id | Inst | 31~25 | 24~20 | 19~15 | 14~12 | 11~7| 6~0 |
| -------- | -------- | -------- |-------- | -------- | -------- |------ | -------- |
| 1     | **CLZ**    | 0110000     |00000     | rs1     | 001     | rd     | 0010011     |
| 2     | **CTZ**     | 0110000     |00001     | rs1     | 001     | rd     | 0010011     |
| 3     | **CPOP**    | 0110000     |00010     | rs1     | 111     | rd     | 0010011      |
| 4     | **ANDN**     | 0100000     |rs2     | rs1     | 111     | rd     | 0110011    |
| 5     | **ORN**     | 0100000    |rs2     | rs1     | 110     | rd     | 0110011     |
| 6     | **XNOR**     | 0100000    |rs2     | rs1     | 100     | rd     | 0110011    |
| 7     | **MIN**     | 0000101     |rs2     | rs1     | 100     | rd     | 0110011     |
| 8     | **MAX**     | 0000101     |rs2     | rs1     | 110     | rd     | 0110011     |
| 9     | **MINU**     | 0000101     |rs2     | rs1     | 101     | rd     | 0110011     |
| 10     | **MAXU**     | 0000101    |rs2    | rs1     | 111     | rd     | 0110011     |
| 11     | **SEXTB**     | 0110000     |00100     | rs1     | 001     | rd     | 0010011     |
| 12     | **SEXTH**     | 0110000     |00101     | rs1     | 001     | rd     | 0010011     |
| 13     | **BSET**     | 0010100     |rs2     | rs1     | 001     | rd     | 0110011     |
| 14     | **BCLR**     | 0100100     |rs2     | rs1     | 001     | rd     | 0110011     |
| 15     | **BINV**     | 0110100     |rs2     | rs1     | 001     | rd     | 0110011     |




### Assembler translation - <Instruction_Name>
> **將新增的 code**(case部份就好)放在下方並加上註解, 讓TA明白你是如何完成的。

```cpp=
//emulator.h
    typedef enum
    {
        UNIMPL = 0,

        // instruction added Tiger_Chang
        CLZ, // Tiger_Chang
        CTZ,
        CPOP,
        ANDN,
        MAX,
        MAXU, // unsiged max
        MIN,  // signed min
        MINU, // unsigned min
        ORN,
        XNOR,
        SEXTB,
        SEXTH,
        BSET,
        BCLR,
        BINV,
    }
//emulator.cpp
instr_type parse_instr(char *tok)
{
    // instruction added
    if (streq(tok, "andn"))
        return ANDN;
    if (streq(tok, "max"))
        return MAX;
    if (streq(tok, "clz"))
        return CLZ;
    if (streq(tok, "ctz"))
        return CTZ;
    if (streq(tok, "cpop"))
        return CPOP;
    if (streq(tok, "maxu"))
        return MAXU;
    if (streq(tok, "min"))
        return MIN;
    if (streq(tok, "minu"))
        return MINU;
    if (streq(tok, "orn"))
        return ORN;
    if (streq(tok, "xnor"))
        return XNOR;
    if (streq(tok, "bset"))
        return BSET;
    if (streq(tok, "bclr"))
        return BCLR;
    if (streq(tok, "binv"))
        return BINV;
    if (streq(tok, "sextb"))
        return SEXTB;
    if (streq(tok, "sexth"))
        return SEXTH;
}
int parse_instr(int line, char *ftok, instr *imem, int memoff, label_loc *labels, source *src)
{
    case ANDN:
    case MAX:
    case MAXU:
    case MIN:
    case MINU:
    case ORN:
    case XNOR:
    case BINV:
    case BSET:
    case BCLR:
    {
        if (!o1 || !o2 || !o3 || o4)
            print_syntax_error(line, "Invalid format");
        i->a1.reg = parse_reg(o1, line);
        i->a2.reg = parse_reg(o2, line);
        i->a3.reg = parse_reg(o3, line);
        return 1;
    }
    case CPOP:
    case CTZ:
    case CLZ:
    case SEXTB:
    case SEXTH:
        if (!o1 || !o2 || o3 || o4)
            print_syntax_error(line, "Invalid format");
        i->a1.reg = parse_reg(o1, line);
        i->a2.reg = parse_reg(o2, line);
        return 1;
}
void execute(uint8_t *mem, instr *imem, label_loc *labels, int label_count, bool start_immediate)
{
    switch (i.op)
        {

        case ANDN:
            rf[i.a1.reg] = rf[i.a2.reg] & (~rf[i.a3.reg]);
            break;
        case MAX:
            // printf("max!!\n");
            // printf("rf[i.a2.reg] = 0x%x\n", rf[i.a2.reg]);
            // printf("rf[i.a3.reg] = 0x%x\n", rf[i.a3.reg]);
            rf[i.a1.reg] = ((int32_t)rf[i.a2.reg]) < ((int32_t)rf[i.a3.reg]) ? ((int32_t)rf[i.a3.reg]) : ((int32_t)rf[i.a2.reg]);
            // printf("rf[i.a1.reg] = 0x%x\n", rf[i.a1.reg]);
            break;
        case CPOP:
        {
            printf("cpop\n");
            int count = 0;
            uint32_t num = rf[i.a2.reg];
            while (num != 0)
            {
                if (num & 1)
                {
                    count++;
                }
                num >>= 1;
            }
            rf[i.a1.reg] = count;
            break;
        }
        case CLZ:
        {
            printf("clz\n");
            printf("rf[i.a2.reg] = 0x%x\n", rf[i.a2.reg]);
            uint32_t mask = 0x80000000;
            uint32_t num = 32;
            for (int bit = 31; bit >= 0; --bit, mask >>= 1)
            {
                if (rf[i.a2.reg] & mask)
                {
                    num = 31 - bit;
                    break;
                }
            }
            rf[i.a1.reg] = num;
            printf("rf[i.a1.reg] = 0x%x\n", rf[i.a1.reg]);
            break;
        }
        case CTZ:
        {
            uint32_t mask = 1;
            int bit;
            for (bit = 0; bit < 32; ++bit, mask <<= 1)
            {
                if (rf[i.a2.reg] & mask)
                {
                    rf[i.a1.reg] = bit;
                    break;
                }
            }
            rf[i.a1.reg] = bit;
            break;
        }
        case MAXU:
            (uint32_t) rf[i.a2.reg] > (uint32_t)rf[i.a3.reg] ? rf[i.a1.reg] = rf[i.a2.reg] : rf[i.a1.reg] = rf[i.a3.reg];
            break;
        case MIN:
            (int32_t) rf[i.a2.reg] < (int32_t)rf[i.a3.reg] ? rf[i.a1.reg] = rf[i.a2.reg] : rf[i.a1.reg] = rf[i.a3.reg];
            break;
        case MINU:
            (uint32_t) rf[i.a2.reg] < (uint32_t)rf[i.a3.reg] ? rf[i.a1.reg] = rf[i.a2.reg] : rf[i.a1.reg] = rf[i.a3.reg];
            break;
        case ORN:
            rf[i.a1.reg] = rf[i.a2.reg] | ~(rf[i.a3.reg]);
        case BCLR:
			// rs1 with a single bit cleared at the index specified in rs2
            index = rf[i.a3.reg] & 31;
            rf[i.a1.reg] = rf[i.a2.reg] & ~(1 << index);
            break;
        case BINV:
		// rs1 with a single bit inverted at the index specified in rs2
            index = rf[i.a3.reg] & 31;
            rf[i.a1.reg] = rf[i.a2.reg] ^ (1 << index);
            break;
        case BSET:
		//  rs1 with a single bit set at the index specified in rs2
            index = rf[i.a3.reg] & 31;
            rf[i.a1.reg] = rf[i.a2.reg] | (1 << index);
            break;
        case XNOR:
            rf[i.a1.reg] = ~(rf[i.a2.reg] ^ rf[i.a3.reg]);
            break;
        case SEXTB:
            if ((rf[i.a2.reg] << 24) >> 31 == 1) // test index 7
                rf[i.a1.reg] = (rf[i.a2.reg] << 24) >> 24 | (-256);
            else
                rf[i.a1.reg] = (rf[i.a2.reg] << 24) >> 24;
            break;
        case SEXTH:
            if ((rf[i.a2.reg] << 16) >> 31 == 1) // test index 15
                rf[i.a1.reg] = (rf[i.a2.reg] << 16) >> 16 | (-65536);
            else
                rf[i.a1.reg] = (rf[i.a2.reg] << 16) >> 16;
            break;
}
//translate.cpp
void translate_to_machine_code(uint8_t *mem, instr *imem, char *argv1)
{
	uint32_t inst_cnt = 0;
	bool dexit = false;

	char *path;
	copy_path(argv1, &path);

	FILE *mch_file = fopen(concat(path, "inst.hex"), "w");
	FILE *inst_file = fopen(concat(path, "inst.asm"), "w");
	FILE *data_file = fopen(concat(path, "data.hex"), "w");

	while (!dexit)
	{
		instr i = imem[inst_cnt];
		uint32_t binary = 0;
		int offset = 0;

		// follow the ISA and combine the fragment information in binary form
		switch (i.op)
		{
		// 1~15
		//  added by Tiger_Chang
		case CLZ:
			binary = (0x04 << 2) + 0x03; // opcode
			binary += i.a1.reg << 7;	 // rd
			binary += 0b001 << 12;		 // funct3
			binary += i.a2.reg << 15;
			binary += 0b00000 << 20;
			binary += 0b0110000 << 25;
			break;
		case CTZ:
			binary = (0x04 << 2) + 0x03; // opcode
			binary += i.a1.reg << 7;	 // rd
			binary += 0b001 << 12;		 // funct3
			binary += i.a2.reg << 15;
			binary += 0b00001 << 20;
			binary += 0b0110000 << 25;
			break;
		case CPOP:
			binary = (0x04 << 2) + 0x03; // opcode
			binary += i.a1.reg << 7;	 // rd
			binary += 0b111 << 12;		 // funct3
			binary += i.a2.reg << 15;
			binary += 0b00010 << 20;
			binary += 0b0110000 << 25;
			break;

		case ANDN:
			binary = (0x0C << 2) + 0x03; // opcode
			binary += i.a1.reg << 7;	 // rd
			binary += 0b111 << 12;		 // funct3
			binary += i.a2.reg << 15;	 // rs1
			binary += i.a3.reg << 20;	 // rs2
			binary += 0b0100000 << 25;	 // funct7
			break;
		case ORN:
			binary = (0x0C << 2) + 0x03; // opcode
			binary += i.a1.reg << 7;	 // rd
			binary += 0b110 << 12;		 // funct3
			binary += i.a2.reg << 15;	 // rs1
			binary += i.a3.reg << 20;	 // rs2
			binary += 0b0100000 << 25;	 // funct7
			break;
		case XNOR:
			binary = (0x0C << 2) + 0x03; // opcode
			binary += i.a1.reg << 7;	 // rd
			binary += 0b100 << 12;		 // funct3
			binary += i.a2.reg << 15;	 // rs1
			binary += i.a3.reg << 20;	 // rs2
			binary += 0b0100000 << 25;	 // funct7
			break;
		case MIN:
			binary = (0x0C << 2) + 0x03; // opcode
			binary += i.a1.reg << 7;	 // rd
			binary += 0b100 << 12;		 // funct3
			binary += i.a2.reg << 15;	 // rs1
			binary += i.a3.reg << 20;	 // rs2
			binary += 0b0000101 << 25;	 // funct7
			break;
		case MAX:
			binary = (0x0C << 2) + 0x03; // opcode
			binary += i.a1.reg << 7;	 // rd
			binary += 0b110 << 12;		 // funct3
			binary += i.a2.reg << 15;	 // rs1
			binary += i.a3.reg << 20;	 // rs2
			binary += 0b0000101 << 25;	 // funct7
			break;
		case MINU:
			binary = (0x0C << 2) + 0x03; // opcode
			binary += i.a1.reg << 7;	 // rd
			binary += 0b101 << 12;		 // funct3
			binary += i.a2.reg << 15;	 // rs1
			binary += i.a3.reg << 20;	 // rs2
			binary += 0b0000101 << 25;	 // funct7
			break;
		case MAXU:
			binary = (0x0C << 2) + 0x03; // opcode
			binary += i.a1.reg << 7;	 // rd
			binary += 0b111 << 12;		 // funct3
			binary += i.a2.reg << 15;	 // rs1
			binary += i.a3.reg << 20;	 // rs2
			binary += 0b0000101 << 25;	 // funct7
			break;

		case SEXTB:
			binary = (0x04 << 2) + 0x03; // opcode
			binary += i.a1.reg << 7;	 // rd
			binary += 0b001 << 12;		 // funct3
			binary += i.a2.reg << 15;
			binary += 0b00100 << 20;
			binary += 0b0110000 << 25;
			break;
		case SEXTH:
			binary = (0x04 << 2) + 0x03; // opcode
			binary += i.a1.reg << 7;	 // rd
			binary += 0b001 << 12;		 // funct3
			binary += i.a2.reg << 15;	 // rs1
			binary += 0b00101 << 20;
			binary += 0b0110000 << 25;
			break;
		case BSET:
			binary = (0x0C << 2) + 0x03; // opcode
			binary += i.a1.reg << 7;	 // rd
			binary += 0b001 << 12;		 // funct3
			binary += i.a2.reg << 15;	 // rs1
			binary += i.a3.reg << 20;	 // rs2
			binary += 0b0010100 << 25;	 // funct7
			break;
		case BCLR:
			binary = (0x0C << 2) + 0x03; // opcode
			binary += i.a1.reg << 7;	 // rd
			binary += 0b001 << 12;		 // funct3
			binary += i.a2.reg << 15;	 // rs1
			binary += i.a3.reg << 20;	 // rs2
			binary += 0b0100100 << 25;	 // funct7
			break;
		case BINV:
			binary = (0x0C << 2) + 0x03; // opcode
			binary += i.a1.reg << 7;	 // rd
			binary += 0b001 << 12;		 // funct3
			binary += i.a2.reg << 15;	 // rs1
			binary += i.a3.reg << 20;
			binary += 0b0110100 << 25;
			break;
        }
    }

```
## 測試結果
- 測試檔案1
    - ``lab09-group/Emulator/test_code/1-15.S
```mipsasm=
.text
.macro init
#測試每過一個case a1值+1若未通過會提前終止，
#此時a1值為到第幾個case未通過，全通過的話a1值應為16
main:
    li      sp, 10000
    li      a1, 0
CPOP_test1:
    addi    a1, a1, 1
    addi    t1,x0,0
    cpop    t2,t1
    bne     t2,x0, EXIT
    
CLZ_test2:
    addi    a1, a1, 1
    addi    t1,x0,0
    clz     t2,t1
    addi    t3,x0,32
    bne     t3,t2,EXIT

CTZ_test3:
    addi    a1, a1, 1
    addi    t1,x0,0
    ctz     t2,t1
    bne     t3,t2,EXIT

ANDN_test4:
    addi    a1, a1, 1
    addi    t1,x0 ,0xA5
    addi    t2,x0 ,0xF0
    andn    t3,t1,t2
    addi    t4,x0,5
    bne     t3,t4,EXIT
 ORN_test5:
    addi    a1, a1, 1
    addi    t1, x0,10
    addi    t2, x0,-5
    orn     t3,t1,t2
    addi    t4,x0,14
    bne     t3,t4,EXIT
XNOR_test6:
    addi    a1, a1, 1
    addi    t1, x0,10
    addi    t2, x0,-5
    xnor    t3,t1,t2
    addi    t4,x0,14
    bne     t3,t4,EXIT
MIN_test7:
    addi    a1, a1, 1
    addi    t1, x0,-1
    min     t2, t1,x0
    bne     t1,t2,EXIT
MAX_test8:
    addi    a1, a1, 1
    addi    t1, x0,-1
    max     t2, t1,x0
    bne     t2,x0,EXIT
MINU_test9:
    addi    a1, a1, 1
    addi    t1, x0,-1
    minu    t2, t1,x0
    bne     x0,t2,EXIT
MAXU_test10:
    addi    a1, a1, 1
    addi    t1, x0,-1
    maxu    t2, t1,x0
    bne     t1,t2,EXIT
SEXTB_test11:
    addi    a1, a1, 1
    addi    t1, x0,0xfe
    sextb   t0, t1
    addi    t2, x0,-2
    bne     t0,t2,EXIT
SEXTH_test12:
    addi    a1, a1, 1
    addi    t1, x0,0xfe
    sexth   t0, t1
    bne     t0,t1,EXIT

BSET_test13:
    addi    a1, a1, 1
    addi    t1, x0, 5
    addi    t2, x0, 2
    bset    t0, t1, t2
    addi    t3, x0, 5
    bne     t0, t3, EXIT

BCLR_test14:
    addi    a1, a1, 1
    addi    t1, x0,5
    addi    t2, x0,2
    bclr    t0, t1,t2
    addi    t3, x0,1
    bne     t0, t3, EXIT
 BINV_test15:
    addi    a1, a1, 1
    addi    t1, x0,5
    addi    t2, x0,2
    binv    t0, t1,t2
    addi    t3, x0,1
    bne     t0, t3, EXIT
    addi    a1, a1, 1

EXIT:
    nop
    nop
    nop
    nop
    nop
    hcf
```
- 測試結果
1-15.S
#測試每過一個case a1值+1若未通過會提前終止，
#此時a1值為到第幾個case未通過，全通過的話a1(x11)值應為16
![](https://course.playlab.tw/md/uploads/b3aaeb47-6f10-4644-8027-ca026dfefcc0.png)

- 測試檔案2
助教給的lab8
emulator/example_code/Hw4_inst.asm
- 測試結果
軟體和硬體和答案都相同

![](https://course.playlab.tw/md/uploads/7ea3b1f1-c778-4dea-a8aa-1be0e7a82a61.png)

![](https://course.playlab.tw/md/uploads/d62fbd4d-b03b-48ea-948f-5c87f4b9440d.png)




## 小組最後完成CPU架構圖
- 貼圖
維持一樣的架構，只是擴增ALU部分增加指令與操作
![](https://course.playlab.tw/md/uploads/5ebc9191-cb5d-4138-8798-b2eca13d4198.png)





