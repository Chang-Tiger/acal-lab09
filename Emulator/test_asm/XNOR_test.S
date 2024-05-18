.text
.macro init
main:
addi    t1, x0,10
addi    t2, x0,-5
xnor     t0,t1,t2
nop
nop
nop
nop
nop
hcf