j <reset_vector>
li ra,0
li sp,0
li gp,0
li tp,0
li t0,0
li t1,0
li t2,0
li s0,0
li s1,0
li a0,0
li a1,0
li a2,0
li a3,0
li a4,0
li a5,0
li a6,0
li a7,0
li s2,0
li s3,0
li s4,0
li s5,0
li s6,0
li s7,0
li s8,0
li s9,0
li s10,0
li s11,0
li t3,0
li t4,0
li t5,0
li t6,0
li gp,0
li a0,1
slli a0,a0,0x1f
bltz a0,<test_2>
nop 
li gp,1
li a7,93
li a0,0
li gp,2
li ra,0
li sp,1
bltu ra,sp,<test_2+0x18>
bne zero,gp,<fail>
bne zero,gp,<test_3>
bltu ra,sp,<test_2+0x14>
bne zero,gp,<fail>
li gp,3
li ra,-2
li sp,-1
bltu ra,sp,<test_3+0x18>
bne zero,gp,<fail>
bne zero,gp,<test_4>
bltu ra,sp,<test_3+0x14>
bne zero,gp,<fail>
li gp,4
li ra,0
li sp,-1
bltu ra,sp,<test_4+0x18>
bne zero,gp,<fail>
bne zero,gp,<test_5>
bltu ra,sp,<test_4+0x14>
bne zero,gp,<fail>
li gp,5
li ra,1
li sp,0
bltu ra,sp,<test_5+0x14>
bne zero,gp,<test_5+0x18>
bne zero,gp,<fail>
bltu ra,sp,<test_5+0x14>
li gp,6
li ra,-1
li sp,-2
bltu ra,sp,<test_6+0x14>
bne zero,gp,<test_6+0x18>
bne zero,gp,<fail>
bltu ra,sp,<test_6+0x14>
li gp,7
li ra,-1
li sp,0
bltu ra,sp,<test_7+0x14>
bne zero,gp,<test_7+0x18>
bne zero,gp,<fail>
bltu ra,sp,<test_7+0x14>
li gp,8
lui ra,0x80000
lui sp,0x80000
addi sp,sp,-1
bltu ra,sp,<test_8+0x18>
bne zero,gp,<test_8+0x1c>
bne zero,gp,<fail>
bltu ra,sp,<test_8+0x18>
li gp,9
li tp,0
lui ra,0xf0000
lui sp,0xf0000
addi sp,sp,-1
bltu ra,sp,<fail>
addi tp,tp,1
li t0,2
bne tp,t0,<test_9+0x8>
li gp,10
li tp,0
lui ra,0xf0000
lui sp,0xf0000
addi sp,sp,-1
nop 
bltu ra,sp,<fail>
addi tp,tp,1
li t0,2
bne tp,t0,<test_10+0x8>
li gp,11
li tp,0
lui ra,0xf0000
lui sp,0xf0000
addi sp,sp,-1
nop 
nop 
bltu ra,sp,<fail>
addi tp,tp,1
li t0,2
bne tp,t0,<test_11+0x8>
li gp,12
li tp,0
lui ra,0xf0000
nop 
lui sp,0xf0000
addi sp,sp,-1
bltu ra,sp,<fail>
addi tp,tp,1
li t0,2
bne tp,t0,<test_12+0x8>
li gp,13
li tp,0
lui ra,0xf0000
nop 
lui sp,0xf0000
addi sp,sp,-1
nop 
bltu ra,sp,<fail>
addi tp,tp,1
li t0,2
bne tp,t0,<test_13+0x8>
li gp,14
li tp,0
lui ra,0xf0000
nop 
nop 
lui sp,0xf0000
addi sp,sp,-1
bltu ra,sp,<fail>
addi tp,tp,1
li t0,2
bne tp,t0,<test_14+0x8>
li gp,15
li tp,0
lui ra,0xf0000
lui sp,0xf0000
addi sp,sp,-1
bltu ra,sp,<fail>
addi tp,tp,1
li t0,2
bne tp,t0,<test_15+0x8>
li gp,16
li tp,0
lui ra,0xf0000
lui sp,0xf0000
addi sp,sp,-1
nop 
bltu ra,sp,<fail>
addi tp,tp,1
li t0,2
bne tp,t0,<test_16+0x8>
li gp,17
li tp,0
lui ra,0xf0000
lui sp,0xf0000
addi sp,sp,-1
nop 
nop 
bltu ra,sp,<fail>
addi tp,tp,1
li t0,2
bne tp,t0,<test_17+0x8>
li gp,18
li tp,0
lui ra,0xf0000
nop 
lui sp,0xf0000
addi sp,sp,-1
bltu ra,sp,<fail>
addi tp,tp,1
li t0,2
bne tp,t0,<test_18+0x8>
li gp,19
li tp,0
lui ra,0xf0000
nop 
lui sp,0xf0000
addi sp,sp,-1
nop 
bltu ra,sp,<fail>
addi tp,tp,1
li t0,2
bne tp,t0,<test_19+0x8>
li gp,20
li tp,0
lui ra,0xf0000
nop 
nop 
lui sp,0xf0000
addi sp,sp,-1
bltu ra,sp,<fail>
addi tp,tp,1
li t0,2
bne tp,t0,<test_20+0x8>
li gp,21
li ra,1
bltu zero,ra,<test_21+0x1c>
addi ra,ra,1
addi ra,ra,1
addi ra,ra,1
addi ra,ra,1
addi ra,ra,1
addi ra,ra,1
li t2,3
bne ra,t2,<fail>
bne zero,gp,<pass>
nop 
beqz gp,<fail+0x4>
mv a0,gp
slli gp,gp,0x1
ori gp,gp,1
li a7,93
j <pass_fail_end>
nop 
li gp,1
li a7,93
li a0,0
nop 
nop 
nop 
nop 
nop 
hcf
