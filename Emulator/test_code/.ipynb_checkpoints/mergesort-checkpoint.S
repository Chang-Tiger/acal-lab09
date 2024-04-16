# See LICENSE for license details.

#*****************************************************************************
# mergesort.S
#-----------------------------------------------------------------------------
#
# Test mergesort.
#

#include "riscv_test.h"
#include "test_macros.h"

main:


    # Load data
    la    s0, test1
    la    s1, TEST1_SIZE
    
    # set parameters
    mv    a0, s0
    li    a1, 0
    lw    a2, 0(s1)
    addi  a2, a2, -1
    
    # Caller save
    addi  sp, sp, -4
    sw    ra, 0(sp)   
    
    # Call function
    jal   ra, mergesort
    
    # Caller save pop
    lw    ra, 0(sp)   
    addi  sp, sp, 4

    # Load data
    lw    t0, 0(a0)
    lw    t1, 4(a0)
    lw    t2, 100(a0)
    lw    t3, 104(a0)
    

    li a7, 93
    li a0, 0
    hcf
#------------------------#
#   Function mergesort   #
#------------------------#

mergesort:
    ####
    # a0: array address
    # a1: start offset
    # a2: end offset
    ####
    
    bge a1, a2, mergesort_ret    
    
    ### if(start < end)
    
    # Collee save
    addi  sp, sp, -12
    sw    s0, 8(sp)   
    sw    s1, 4(sp)    
    sw    s2, 0(sp)   

    # set data
    mv    s1, a1
    mv    s2, a2
    # s0: mid = (end + start)/2
    add   s0, a1, a2
    srai  s0, s0, 1    
    
    # Caller save
    addi  sp, sp, -4
    sw    ra, 0(sp)  
    
    ### code: mergesort(arr, start, mid)
    # set parameters
    # a0: address (fixed)
    mv    a1, s1      
    mv    a2, s0      
    # Call mergesort
    jal   ra, mergesort
    
    ### code: mergesort(arr, mid+1, end)
    # set parameters
    # a0: address (fixed)
    addi  a1, s0, 1     
    mv    a2, s2       
    # Call mergesort
    jal   ra, mergesort
    
    ### code: merge(arr, start, mid, end)
    # set parameters
    # a0: address (fixed)
    mv    a1, s1       
    mv    a2, s0       
    mv    a3, s2       
    # Call merge
    jal   ra, merge
    
    # Caller save pop
    lw    ra, 0(sp) 
    addi  sp, sp, 4
    
    # Collee save pop
    lw    s0, 8(sp)  
    lw    s1, 4(sp)    
    lw    s2, 0(sp)    
    addi  sp, sp, 12
    
mergesort_ret:
    ret

#--------------------#
#   Function merge   #
#--------------------#
merge:
    ####
    # a0: array address
    # a1: start offset
    # a2: mid offset
    # a3: end offset
    ####
    
    # t0: temp_size = end - start + 1
    sub   t0, a3, a1
    addi  t0, t0, 1
    
    # t1: temp[temp_size] address in stack (@temp[])
    slli  t1, t0, 2
    sub   sp, sp, t1   
    mv    t1, sp
    
    ### for(int i = 0; i< temp_size;i++)
    li    t2, 0    
    bge   t2, t0, for_loop_1_end
for_loop_1:
    add   t3, t2, a1   
    slli  t3, t3, 2
    add   t3, t3, a0    
    lw    t4, 0(t3)    
    slli  t3, t2, 2
    add   t3, t3, t1   
    sw    t4, 0(t3)     
    
    addi  t2, t2, 1     
    blt   t2, t0, for_loop_1
for_loop_1_end:    
    
    ### set index
    # Collee save
    addi  sp, sp, -20
    sw    s0, 0(sp)
    sw    s1, 4(sp)
    sw    s2, 8(sp)
    sw    s3, 12(sp)
    sw    s4, 16(sp)
    
    # inde initial
    li    s0, 0         
    sub   s1, a2, a1    
    addi  s2, s1, 1     
    sub   s3, a3, a1    
    mv    s4, a1        
    
    ### while(left_index <= left_max && right_index <= right_max)
while_loop_1:
    blt   s1, s0, while_loop_1_end    
    blt   s3, s2, while_loop_1_end    
    
    ### if(temp[left_index] <= temp[right_index])
    slli  t2, s0, 2         
    add   t2, t2, t1      
    lw    t3, 0(t2)
    slli  t2, s2, 2         
    add   t2, t2, t1      
    lw    t4, 0(t2)
    slli  t2, s4, 2         
    add   t2, t2, a0        
    blt   t4, t3, else_1
if_1:      
    sw    t3, 0(t2)        
    addi  s4, s4, 1       
    addi  s0, s0, 1       
    j     if_1_end    
else_1:      
    sw    t4, 0(t2)       
    addi  s4, s4, 1        
    addi  s2, s2, 1        
if_1_end:    
    j    while_loop_1
while_loop_1_end:

### while(left_index <= left_max)
    blt   s1, s0, while_loop_2_end    
while_loop_2:
    slli  t2, s0, 2         
    add   t2, t2, t1        
    lw    t3, 0(t2)
    slli  t2, s4, 2         
    add   t2, t2, a0       
    sw    t3, 0(t2)        
    addi  s4, s4, 1         
    addi  s0, s0, 1        
    bge   s1, s0, while_loop_2    
while_loop_2_end:
    blt   s3, s2, while_loop_3_end    
while_loop_3:
    slli  t2, s2, 2        
    add   t2, t2, t1        
    lw    t3, 0(t2)
    slli  t2, s4, 2         
    add   t2, t2, a0        
    sw    t3, 0(t2)         
    addi  s4, s4, 1         
    addi  s2, s2, 1         
    bge   s3, s2, while_loop_3    
while_loop_3_end:
    
    # Collee save pop
    lw    s0, 0(sp)
    lw    s1, 4(sp)
    lw    s2, 8(sp)
    lw    s3, 12(sp)
    lw    s4, 16(sp)
    addi  sp, sp, 20
    
    # release temp array in stack
    slli  t1, t0, 2
    add   sp ,sp, t1    
    
    ret

    nop
    nop
    nop
    nop
    nop

  .data

test1: .word 412,-474,443,171,-23,247,221,7,40,221,-90,61,-9,49,-80,-80,221,-379,-161,-397,-173,276,-197,221,-12,-145,101
TEST1_SIZE: .word 27

