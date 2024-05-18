.text
.macro init
main:
addi    t1, x0,5
addi    t2, x0,2
bset    t0,t1,t2
bclr    t0,t1,t2
binv    t0,t1,t2
nop
nop
nop
nop
nop
hcf