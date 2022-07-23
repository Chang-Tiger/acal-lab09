package aias_lab10.PiplinedCPU.StageRegister

import chisel3._
import chisel3.util._

class Reg_ID(addrWidth:Int) extends Module {
    val io = IO(new Bundle{
        val Flush = Input(Bool())
        val Stall = Input(Bool())

        val pc_in = Input(UInt(addrWidth.W))
        val inst_in = Input(UInt(32.W))
        val BP_taken_in = Input(Bool())

        val pc = Output(UInt(addrWidth.W))
        val inst = Output(UInt(32.W))
        val BP_taken = Output(Bool())
    })
    
    // stage Registers
    val InstReg = RegInit(0.U(32.W))
    val pcReg =  RegInit(0.U(15.W))
    val BP_taken_Reg =  RegInit(false.B)

    /*** stage Registers Action ***/
    when(io.Flush){
        InstReg := 0.U(32.W)
        pcReg := 0.U(addrWidth.W)
        BP_taken_Reg := false.B
    }.elsewhen(io.Stall){
        InstReg := InstReg
        pcReg := pcReg
        BP_taken_Reg := BP_taken_Reg
    }.otherwise{
        InstReg := io.inst_in
        pcReg := io.pc_in
        BP_taken_Reg := io.BP_taken_in
    }
 
    io.inst := InstReg
    io.pc := pcReg
    io.BP_taken := BP_taken_Reg
}
