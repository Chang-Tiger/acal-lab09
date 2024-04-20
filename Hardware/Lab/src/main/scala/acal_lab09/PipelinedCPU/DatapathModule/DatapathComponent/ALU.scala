package acal_lab09.PiplinedCPU.DatapathModule.DatapathComponent

import chisel3._
import chisel3.util._

import acal_lab09.PiplinedCPU.opcode_map._
import acal_lab09.PiplinedCPU.alu_op_map._

class ALUIO extends Bundle{
  val src1    = Input(UInt(32.W))
  val src2    = Input(UInt(32.W))
  val ALUSel  = Input(UInt(15.W))
  val out  = Output(UInt(32.W))
}

class ALU extends Module{
  val io = IO(new ALUIO)

  io.out := 0.U
  switch(io.ALUSel){
    is(ADD ){io.out := io.src1+io.src2}
    is(SLL ){io.out := io.src1 << io.src2(4,0)}
    is(SLT ){io.out := Mux(io.src1.asSInt < io.src2.asSInt,1.U,0.U)}
    is(SLTU){io.out := Mux(io.src1 < io.src2              ,1.U,0.U)}
    is(XOR ){io.out := io.src1^io.src2}
    is(SRL ){io.out := io.src1 >> io.src2(4,0)}
    is(OR  ){io.out := io.src1|io.src2}
    is(AND ){io.out := io.src1&io.src2}
    is(SUB ){io.out := io.src1-io.src2}
    is(SRA ){io.out := (io.src1.asSInt >> io.src2(4,0)).asUInt}
    // added by funfish
    is(CTZ ){io.out := Mux(io.src1(0),0.U,Mux(io.src1(1),1.U,Mux(io.src1(2),2.U,Mux(io.src1(3),3.U,Mux(io.src1(4),4.U,Mux(io.src1(5),5.U,Mux(io.src1(6),6.U,Mux(io.src1(7),7.U,Mux(io.src1(8),8.U,Mux(io.src1(9),9.U,Mux(io.src1(10),10.U,Mux(io.src1(11),11.U,Mux(io.src1(12),12.U,Mux(io.src1(13),13.U,Mux(io.src1(14),14.U,Mux(io.src1(15),15.U,Mux(io.src1(16),16.U,Mux(io.src1(17),17.U,Mux(io.src1(18),18.U,Mux(io.src1(19),19.U,Mux(io.src1(20),20.U,Mux(io.src1(21),21.U,Mux(io.src1(22),22.U,Mux(io.src1(23),23.U,Mux(io.src1(24),24.U,Mux(io.src1(25),25.U,Mux(io.src1(26),26.U,Mux(io.src1(27),27.U,Mux(io.src1(28),28.U,Mux(io.src1(29),29.U,Mux(io.src1(30),30.U,Mux(io.src1(31),31.U, 32.U))))))))))))))))))))))))))))))))}
    is(CLZ ){io.out := Mux(io.src1(31),0.U,Mux(io.src1(30),1.U,Mux(io.src1(29),2.U,Mux(io.src1(28),3.U,Mux(io.src1(27),4.U,Mux(io.src1(26),5.U,Mux(io.src1(25),6.U,Mux(io.src1(24),7.U,Mux(io.src1(23),8.U,Mux(io.src1(22),9.U,Mux(io.src1(21),10.U,Mux(io.src1(20),11.U,Mux(io.src1(19),12.U,Mux(io.src1(18),13.U,Mux(io.src1(17),14.U,Mux(io.src1(16),15.U,Mux(io.src1(15),16.U,Mux(io.src1(14),17.U,Mux(io.src1(13),18.U,Mux(io.src1(12),19.U,Mux(io.src1(11),20.U,Mux(io.src1(10),21.U,Mux(io.src1(9),22.U,Mux(io.src1(8),23.U,Mux(io.src1(7),24.U,Mux(io.src1(6),25.U,Mux(io.src1(5),26.U,Mux(io.src1(4),27.U,Mux(io.src1(3),28.U,Mux(io.src1(2),29.U,Mux(io.src1(1),30.U,Mux(io.src1(0),31.U, 32.U))))))))))))))))))))))))))))))))}
    is(CPOP){io.out := (io.src1(0).asUInt) + (io.src1(1).asUInt) + (io.src1(2).asUInt) + (io.src1(3).asUInt) + (io.src1(4).asUInt) + (io.src1(5).asUInt) + (io.src1(6).asUInt) + (io.src1(7).asUInt) + (io.src1(8).asUInt) + (io.src1(9).asUInt) + (io.src1(10).asUInt) + (io.src1(11).asUInt) + (io.src1(12).asUInt) + (io.src1(13).asUInt) + (io.src1(14).asUInt) + (io.src1(15).asUInt) + (io.src1(16).asUInt) + (io.src1(17).asUInt) + (io.src1(18).asUInt) + (io.src1(19).asUInt) + (io.src1(20).asUInt) + (io.src1(21).asUInt) + (io.src1(22).asUInt) + (io.src1(23).asUInt) + (io.src1(24).asUInt) + (io.src1(25).asUInt) + (io.src1(26).asUInt) + (io.src1(27).asUInt) + (io.src1(28).asUInt) + (io.src1(29).asUInt) + (io.src1(30).asUInt) + (io.src1(31).asUInt)}
    is(SEXTB){io.out := Mux(io.src1(7), Cat("hffffff".U, io.src1(7,0)), io.src1(7,0))}
    is(SEXTH){io.out := Mux(io.src1(15), Cat("hfff".U, io.src1(15, 0)), io.src1(15,0))}
    is(ANDN){io.out := (io.src1 & ~(io.src2))}
    is(ORN ){io.out := (io.src1 | ~(io.src2))}  
    is(XNOR){io.out := (~io.src1 ^ io.src2)} 
    is(MIN ){io.out := Mux(io.src1.asSInt > io.src2.asSInt, io.src2, io.src1)}
    is(MAX ){io.out := Mux(io.src1.asSInt > io.src2.asSInt, io.src1, io.src2)}
    is(MINU){io.out := Mux(io.src1.asUInt > io.src2.asUInt, io.src2, io.src1)}
    is(MAXU){io.out := Mux(io.src1.asUInt > io.src2.asUInt, io.src1, io.src2)}
    is(BSET){io.out := io.src1 | (1.U << (io.src2 & 31.U)(5,0))}
    is(BCLR){io.out := io.src1 & ~(1.U << (io.src2 & 31.U)(5,0))}
    is(BINV){io.out := io.src1 ^ (1.U << (io.src2 & 31.U)(5,0))}
  }
}

