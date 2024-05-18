lui x02, 0x00000002
addi x02, x02, 0x00000710
lui x11, 0x00000000
addi x11, x11, 0x00000000
addi a1, a1, 1
addi t1, x0, 0
cpop t2, t1
bne t2, x0, exit
addi a1, a1, 1
addi t1, x0, 0
clz t2, t1
addi t3, x0, 32
bne t3, t2, exit
addi a1, a1, 1
addi t1, x0, 0
ctz t2, t1
bne t3, t2, exit
addi a1, a1, 1
addi t1, x0, 0xa5
addi t2, x0, 0xf0
andn t3, t1, t2
addi t4, x0, 5
bne t3, t4, exit
addi a1, a1, 1
addi t1, x0, 10
addi t2, x0, -5
orn t3, t1, t2
addi t4, x0, 14
bne t3, t4, exit
addi a1, a1, 1
addi t1, x0, 10
addi t2, x0, -5
xnor t3, t1, t2
addi t4, x0, 14
bne t3, t4, exit
addi a1, a1, 1
addi t1, x0, -1
min t2, t1, x0
bne t1, t2, exit
addi a1, a1, 1
addi t1, x0, -1
max t2, t1, x0
bne t2, x0, exit
addi a1, a1, 1
addi t1, x0, -1
minu t2, t1, x0
bne x0, t2, exit
addi a1, a1, 1
addi t1, x0, -1
maxu t2, t1, x0
bne t1, t2, exit
addi a1, a1, 1
addi t1, x0, 0xfe
sext_b t0, t1
addi t2, x0, -2
bne t0, t2, exit
addi a1, a1, 1
addi t1, x0, 0xfe
sext_h t0, t1
bne t0, t1, exit
addi a1, a1, 1
addi t1, x0, 5
addi t2, x0, 2
bset t0, t1, t2
addi t3, x0, 5
bne t0, t3, exit
addi a1, a1, 1
addi t1, x0, 5
addi t2, x0, 2
bclr t0, t1, t2
addi t3, x0, 1
bne t0, t3, exit
addi a1, a1, 1
addi t1, x0, 5
addi t2, x0, 2
binv t0, t1, t2
addi t3, x0, 1
bne t0, t3, exit
addi a1, a1, 1
nop zero, zero, 0
nop zero, zero, 0
nop zero, zero, 0
nop zero, zero, 0
nop zero, zero, 0
hcf
nop
nop
nop
nop
nop
hcf
