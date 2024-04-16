lui x02, 0x00000002
addi x02, x02, 0x00000710
lui x05, 0x000ff00f
addi x05, x05, 0x00000ff0
lui x06, 0x00000000
addi x06, x06, 0x00000005
bext t2, t0, t1
bseti t2, t0, 16
bclri t2, t0, 5
binvi t2, t0, 6
bexti t2, t0, 5
ror t2, t0, t1
rol t2, t0, t1
rori t2, t0, 5
sh1add t2, t0, t1
sh2add t2, t0, t1
sh3add t2, t0, t1
rev8 t2, t0
zexth t2, t0
orc.b t2, t0
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
