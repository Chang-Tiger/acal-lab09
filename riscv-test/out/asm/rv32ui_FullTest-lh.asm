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
li a5,255
auipc ra,0x8
addi ra,ra,-168
lh a4,0(ra)
li t2,255
bne a4,t2,<fail>
li gp,3
li a5,-256
auipc ra,0x8
addi ra,ra,-196
lh a4,2(ra)
li t2,-256
bne a4,t2,<fail>
li gp,4
lui a5,0x1
addi a5,a5,-16
auipc ra,0x8
addi ra,ra,-228
lh a4,4(ra)
lui t2,0x1
addi t2,t2,-16
bne a4,t2,<fail>
li gp,5
lui a5,0xfffff
addi a5,a5,15
auipc ra,0x8
addi ra,ra,-264
lh a4,6(ra)
lui t2,0xfffff
addi t2,t2,15
bne a4,t2,<fail>
li gp,6
li a5,255
auipc ra,0x8
addi ra,ra,-290
lh a4,-6(ra)
li t2,255
bne a4,t2,<fail>
li gp,7
li a5,-256
auipc ra,0x8
addi ra,ra,-318
lh a4,-4(ra)
li t2,-256
bne a4,t2,<fail>
li gp,8
lui a5,0x1
addi a5,a5,-16
auipc ra,0x8
addi ra,ra,-350
lh a4,-2(ra)
lui t2,0x1
addi t2,t2,-16
bne a4,t2,<fail>
li gp,9
lui a5,0xfffff
addi a5,a5,15
auipc ra,0x8
addi ra,ra,-386
lh a4,0(ra)
lui t2,0xfffff
addi t2,t2,15
bne a4,t2,<fail>
li gp,10
auipc ra,0x8
addi ra,ra,-420
addi ra,ra,-32
lh t0,32(ra)
li t2,255
bne t0,t2,<fail>
li gp,11
auipc ra,0x8
addi ra,ra,-448
addi ra,ra,-5
lh t0,7(ra)
li t2,-256
bne t0,t2,<fail>
li gp,12
li tp,0
auipc ra,0x8
addi ra,ra,-478
lh a4,2(ra)
mv t1,a4
lui t2,0x1
addi t2,t2,-16
bne t1,t2,<fail>
addi tp,tp,1
li t0,2
bne tp,t0,<test_12+0x8>
li gp,13
li tp,0
auipc ra,0x8
addi ra,ra,-524
lh a4,2(ra)
nop 
mv t1,a4
lui t2,0xfffff
addi t2,t2,15
bne t1,t2,<fail>
addi tp,tp,1
li t0,2
bne tp,t0,<test_13+0x8>
li gp,14
li tp,0
auipc ra,0x8
addi ra,ra,-580
lh a4,2(ra)
nop 
nop 
mv t1,a4
li t2,-256
bne t1,t2,<fail>
addi tp,tp,1
li t0,2
bne tp,t0,<test_14+0x8>
li gp,15
li tp,0
auipc ra,0x8
addi ra,ra,-630
lh a4,2(ra)
lui t2,0x1
addi t2,t2,-16
bne a4,t2,<fail>
addi tp,tp,1
li t0,2
bne tp,t0,<test_15+0x8>
li gp,16
li tp,0
auipc ra,0x8
addi ra,ra,-672
nop 
lh a4,2(ra)
lui t2,0xfffff
addi t2,t2,15
bne a4,t2,<fail>
addi tp,tp,1
li t0,2
bne tp,t0,<test_16+0x8>
li gp,17
li tp,0
auipc ra,0x8
addi ra,ra,-724
nop 
nop 
lh a4,2(ra)
li t2,-256
bne a4,t2,<fail>
addi tp,tp,1
li t0,2
bne tp,t0,<test_17+0x8>
li gp,18
auipc t0,0x8
addi t0,t0,-768
lh sp,0(t0)
li sp,2
li t2,2
bne sp,t2,<fail>
li gp,19
auipc t0,0x8
addi t0,t0,-796
lh sp,0(t0)
nop 
li sp,2
li t2,2
bne sp,t2,<fail>
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
