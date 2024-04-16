lui x08, 0x00000008
addi x08, x08, 0x00000000
lui x09, 0x00000008
addi x09, x09, 0x00000004
addi a0, s0
lui x11, 0x00000000
addi x11, x11, 0x00000000
lw a2, 0(s1)
addi a2, a2, -1
addi sp, sp, -4
sw ra, 0(sp)
jal ra, mergesort
lw ra, 0(sp)
addi sp, sp, 4
lw t0, 0(a0)
lw t1, 4(a0)
lw t2, 100(a0)
lw t3, 104(a0)
lui x17, 0x00000000
addi x17, x17, 0x0000005d
lui x10, 0x00000000
addi x10, x10, 0x00000000
hcf
bge a1, a2, mergesort_ret
addi sp, sp, -12
sw s0, 8(sp)
sw s1, 4(sp)
sw s2, 0(sp)
addi s1, a1
addi s2, a2
add s0, a1, a2
srai s0, s0, 1
addi sp, sp, -4
sw ra, 0(sp)
addi a1, s1
addi a2, s0
jal ra, mergesort
addi a1, s0, 1
addi a2, s2
jal ra, mergesort
addi a1, s1
addi a2, s0
addi a3, s2
jal ra, merge
lw ra, 0(sp)
addi sp, sp, 4
lw s0, 8(sp)
lw s1, 4(sp)
lw s2, 0(sp)
addi sp, sp, 12
jalr x0, x1, x0
sub t0, a3, a1
addi t0, t0, 1
slli t1, t0, 2
sub sp, sp, t1
addi t1, sp
lui x07, 0x00000000
addi x07, x07, 0x00000000
bge t2, t0, for_loop_1_end
add t3, t2, a1
slli t3, t3, 2
add t3, t3, a0
lw t4, 0(t3)
slli t3, t2, 2
add t3, t3, t1
sw t4, 0(t3)
addi t2, t2, 1
blt t2, t0, for_loop_1
addi sp, sp, -20
sw s0, 0(sp)
sw s1, 4(sp)
sw s2, 8(sp)
sw s3, 12(sp)
sw s4, 16(sp)
lui x08, 0x00000000
addi x08, x08, 0x00000000
sub s1, a2, a1
addi s2, s1, 1
sub s3, a3, a1
addi s4, a1
blt s1, s0, while_loop_1_end
blt s3, s2, while_loop_1_end
slli t2, s0, 2
add t2, t2, t1
lw t3, 0(t2)
slli t2, s2, 2
add t2, t2, t1
lw t4, 0(t2)
slli t2, s4, 2
add t2, t2, a0
blt t4, t3, else_1
sw t3, 0(t2)
addi s4, s4, 1
addi s0, s0, 1
j x0, if_1_end
sw t4, 0(t2)
addi s4, s4, 1
addi s2, s2, 1
j x0, while_loop_1
blt s1, s0, while_loop_2_end
slli t2, s0, 2
add t2, t2, t1
lw t3, 0(t2)
slli t2, s4, 2
add t2, t2, a0
sw t3, 0(t2)
addi s4, s4, 1
addi s0, s0, 1
bge s1, s0, while_loop_2
blt s3, s2, while_loop_3_end
slli t2, s2, 2
add t2, t2, t1
lw t3, 0(t2)
slli t2, s4, 2
add t2, t2, a0
sw t3, 0(t2)
addi s4, s4, 1
addi s2, s2, 1
bge s3, s2, while_loop_3
lw s0, 0(sp)
lw s1, 4(sp)
lw s2, 8(sp)
lw s3, 12(sp)
lw s4, 16(sp)
addi sp, sp, 20
slli t1, t0, 2
add sp, sp, t1
jalr x0, x1, x0
nop zero, zero, 0
nop zero, zero, 0
nop zero, zero, 0
nop zero, zero, 0
nop zero, zero, 0
nop
nop
nop
nop
nop
hcf
